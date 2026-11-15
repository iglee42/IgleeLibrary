package fr.iglee42.igleelib.blockentities;

import fr.iglee42.igleelib.blockentities.data.FieldSerializer;
import fr.iglee42.igleelib.blockentities.data.Save;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentMap.Builder;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * Reflection-driven base block entity that persists fields annotated with {@link Save}.
 * <p>
 * It handles three independent flows:
 * <ul>
 *     <li>World persistence: {@link #saveAdditional} and {@link #loadAdditional}.</li>
 *     <li>Client synchronization: {@link #getUpdateTag} and {@link #handleUpdateTag}.</li>
 *     <li>Item component bridge: {@link #collectImplicitComponents} and {@link #applyImplicitComponents}.</li>
 * </ul>
 *
 * Per-field cases:
 * <ul>
 *     <li>Field not annotated with {@code @Save}: ignored.</li>
 *     <li>{@code @Save(synchronize = false)}: excluded from client update tags.</li>
 *     <li>{@code @Save(itemSaved = false)}: excluded from item component transfer.</li>
 *     <li>Unsupported type or invalid serializer metadata: fails fast with a runtime exception describing the field.</li>
 * </ul>
 */
public abstract class SavedBlockEntity extends BlockEntity {

	public SavedBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
	}

	// Data Management

	@Override
	protected void loadAdditional(CompoundTag input, HolderLookup.Provider registries) {
		super.loadAdditional(input, registries);
		load(input, registries, false);
	}

	@Override
	protected void saveAdditional(CompoundTag output, HolderLookup.Provider registries) {
		super.saveAdditional(output, registries);
		save(output, registries, false);
	}

	/**
	 * Reads values for all discovered {@link Save} fields.
	 * <p>
	 * Cases:
	 * <ul>
	 *     <li>{@code forClient == false}: all {@code @Save} fields are considered.</li>
	 *     <li>{@code forClient == true}: only {@code @Save(synchronize = true)} fields are considered.</li>
	 *     <li>Missing field payload: serializer-specific fallback applies (often current value or default).</li>
	 * </ul>
	 */
	protected void load(CompoundTag input, HolderLookup.Provider registries, boolean forClient) {
		for (Field field : getSaveFields()) {
			Save annotation = field.getAnnotation(Save.class);

			if (annotation == null)
				continue;

			if (!annotation.synchronize() && forClient) continue;

			field.setAccessible(true);
			try {
				FieldSerializer serializer = FieldSerializer.fromField(field);
				field.set(this, serializer.decode(registries, input, field.getName(), field.get(this)));
			} catch (IllegalAccessException e) {
				throw new RuntimeException(
						"Cannot access @Save field '" + getClass().getName() + "." + field.getName() + "'", e
				);
			} catch (IllegalArgumentException e) {
				throw new RuntimeException(e.getMessage(), e);
			}
		}
	}

	/**
	 * Writes values for all discovered {@link Save} fields.
	 * <p>
	 * Cases mirror {@link #load}: non-synchronized fields are skipped in client sync mode.
	 */
	protected void save(CompoundTag output, HolderLookup.Provider registries, boolean forClient) {
		for (Field field : getSaveFields()) {
			Save annotation = field.getAnnotation(Save.class);

			if (annotation == null)
				continue;

			if (!annotation.synchronize() && forClient) continue;

			field.setAccessible(true);
			try {
				FieldSerializer serializer = FieldSerializer.fromField(field);
				serializer.encode(registries, output, field.getName(), field.get(this));
			} catch (IllegalAccessException e) {
				throw new RuntimeException(
						"Cannot access @Save field '" + getClass().getName() + "." + field.getName() + "'", e
				);
			} catch (IllegalArgumentException e) {
				throw new RuntimeException(e.getMessage(), e);
			}
		}
	}

	/**
	 * Produces the block-entity update tag sent to clients.
	 * <p>
	 * Only fields with {@code @Save(synchronize = true)} are written.
	 */
	@Override
	public @NotNull CompoundTag getUpdateTag(HolderLookup.Provider registries) {
		CompoundTag nbt = super.getUpdateTag(registries);
		save(nbt, registries, true);
		return nbt;
	}

	/**
	 * Applies a client update tag.
	 * <p>
	 * Only fields with {@code @Save(synchronize = true)} are read.
	 */
	@Override
	public void handleUpdateTag(CompoundTag tag, HolderLookup.Provider lookupProvider) {
		super.handleUpdateTag(tag, lookupProvider);
		load(tag, lookupProvider, true);
	}

	@Override
	public final @NotNull Packet<ClientGamePacketListener> getUpdatePacket() {
		return ClientboundBlockEntityDataPacket.create(this);
	}

	/**
	 * Exports {@code @Save(itemSaved = true)} fields into item data components.
	 * <p>
	 * Cases:
	 * <ul>
	 *     <li>Provider returns {@code null}: field is skipped.</li>
	 *     <li>Provider type/value mismatch: throws with a descriptive error.</li>
	 * </ul>
	 */
	@Override
	protected void collectImplicitComponents(Builder components) {
		super.collectImplicitComponents(components);

		for (Field field : getSaveFields()) {
			Save annotation = field.getAnnotation(Save.class);

			if (annotation == null)
				continue;

			if (!annotation.itemSaved()) continue;

			field.setAccessible(true);
			try {
				DataComponentType<?> componentType = annotation.component().getConstructor().newInstance().get();
				if (componentType == null) continue;
				setUnchecked(components, componentType, field.get(this));
			} catch (ClassCastException e) {
				throw new RuntimeException(
						"Component type mismatch for @Save(itemSaved) field '" + getClass().getName() + "." + field.getName() + "'", e
				);
			} catch (IllegalAccessException | InstantiationException | NoSuchMethodException |
			         InvocationTargetException e) {
				throw new RuntimeException(
						"Failed to collect component for @Save(itemSaved) field '" + getClass().getName() + "." + field.getName() + "'", e
				);
			}
		}
	}

	/**
	 * Imports {@code @Save(itemSaved = true)} fields from item data components.
	 * <p>
	 * Cases:
	 * <ul>
	 *     <li>Provider returns {@code null}: field is skipped.</li>
	 *     <li>Component has no value: field is set to {@code null}.</li>
	 *     <li>Component value type mismatch: throws with a descriptive error.</li>
	 * </ul>
	 */
	@Override
	protected void applyImplicitComponents(DataComponentInput components) {
		super.applyImplicitComponents(components);
		for (Field field : getSaveFields()) {
			Save annotation = field.getAnnotation(Save.class);

			if (annotation == null)
				continue;

			if (!annotation.itemSaved()) continue;

			field.setAccessible(true);
			try {
				DataComponentType<?> componentType = annotation.component().getConstructor().newInstance().get();
				if (componentType == null) continue;
				Object value = components.get(componentType);
				if (value != null && !field.getType().isInstance(value)) {
					throw new RuntimeException(
							"Component value type mismatch for @Save(itemSaved) field '" + getClass().getName() + "." + field.getName() +
									"': expected " + field.getType().getSimpleName() + " but got " + value.getClass().getSimpleName()
					);
				}
				field.set(this, value);
			} catch (IllegalAccessException | InstantiationException | NoSuchMethodException |
			         InvocationTargetException e) {
				throw new RuntimeException(
						"Failed to apply component for @Save(itemSaved) field '" + getClass().getName() + "." + field.getName() + "'", e
				);
			}
		}
	}

	/**
	 * Collects every field annotated with {@link Save} from this class and its superclasses up to {@link BlockEntity}.
	 */
	private List<Field> getSaveFields() {
		List<Field> fields = new ArrayList<>();
		Class<?> cursor = getClass();
		while (cursor != null && cursor != BlockEntity.class) {
			for (Field field : cursor.getDeclaredFields()) {
				if (field.isAnnotationPresent(Save.class)) {
					fields.add(field);
				}
			}
			cursor = cursor.getSuperclass();
		}
		return fields;
	}

	@SuppressWarnings("unchecked")
	/** Localized unchecked cast helper used for data component writes. */
	private static <T> void setUnchecked(Builder components, DataComponentType<T> type, Object value) {
		components.set(type, (T) value);
	}
}
