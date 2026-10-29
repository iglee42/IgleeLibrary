package fr.iglee42.igleelib.blockentities.data;

import com.mojang.serialization.Codec;

import fr.iglee42.igleelib.function.QuadConsumer;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.UUIDUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.world.item.ItemStack;

import net.neoforged.neoforge.fluids.FluidStack;

import org.apache.commons.lang3.function.TriFunction;
import org.apache.logging.log4j.util.TriConsumer;

import java.util.Arrays;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import static net.minecraft.nbt.CompoundTag.*;

public enum FieldType implements FieldSerializer {

	INT(
		Codec.INT,
		(out, name, val) -> out.putInt(name, (Integer) val),
		(in, name) -> in.contains(name, TAG_INT) ? in.getInt(name) : 0,
		int.class, Integer.class
	),

	LONG(
		Codec.LONG,
		(out, name, val) -> out.putLong(name, (Long) val),
		(in, name) -> in.contains(name, TAG_LONG) ? in.getLong(name) : 0L,
		long.class, Long.class
	),

	BOOLEAN(
		Codec.BOOL,
		(out, name, val) -> out.putBoolean(name, (Boolean) val),
		(in, name) -> in.contains(name, TAG_BYTE) && in.getBoolean(name),
		boolean.class, Boolean.class
	),

	FLOAT(
		Codec.FLOAT,
		(out, name, val) -> out.putFloat(name, (Float) val),
		(in, name) -> in.contains(name, TAG_FLOAT) ? in.getFloat(name) : 0F,
		float.class, Float.class
	),

	DOUBLE(
		Codec.DOUBLE,
		(out, name, val) -> out.putDouble(name, (Double) val),
		(in, name) -> in.contains(name, TAG_DOUBLE) ? in.getDouble(name) : 0D,
		double.class, Double.class
	),

	STRING(
		Codec.STRING,
		(out, name, val) -> out.putString(name, (String) val),
		(in, name) -> in.contains(name, TAG_STRING) ? in.getString(name) : "",
		String.class
	),

	ITEM_STACK(
		ItemStack.OPTIONAL_CODEC,
		(registries, out, name, it) -> out.put(name, ((ItemStack)it).saveOptional(registries)),
		(registries, in, name) -> in.contains(name, TAG_COMPOUND) ? ItemStack.parseOptional(registries, in.getCompound(name)) : ItemStack.EMPTY,
		ItemStack.class
	),

	FLUID_STACK(
		FluidStack.OPTIONAL_CODEC,
		(registries, out, name, it) -> out.put(name, ((FluidStack)it).saveOptional(registries)),
		(registries, in, name) -> in.contains(name, TAG_COMPOUND) ? FluidStack.parseOptional(registries, in.getCompound(name)) : FluidStack.EMPTY,
		FluidStack.class
	),
	UUID(
		UUIDUtil.CODEC,
		(out, name, val) -> out.putUUID(name, (UUID) val),
		(in, name) -> in.contains(name, TAG_INT_ARRAY) ? in.getUUID(name) : Util.NIL_UUID,
		UUID.class
	),
	BLOCK_POS(
		BlockPos.CODEC,
		(out, name, val) -> out.put(name, NbtUtils.writeBlockPos((BlockPos) val)),
		(in, name) -> in.contains(name, TAG_INT_ARRAY) ? NbtUtils.readBlockPos(in, name) : BlockPos.ZERO,
		BlockPos.class
	);

	private final Codec<?> rawCodec;
	private final Class<?>[] validClasses;
	private final QuadConsumer<HolderLookup.Provider, CompoundTag, String, Object> encoder;
	private final TriFunction<HolderLookup.Provider, CompoundTag, String, Object> decoder;

	FieldType(
			Codec<?> codec,
			QuadConsumer<HolderLookup.Provider, CompoundTag, String, Object> encoder,
			TriFunction<HolderLookup.Provider, CompoundTag, String, Object> decoder,
			Class<?>... classes
	) {
		this.rawCodec = codec;
		this.validClasses = classes;
		this.encoder = encoder;
		this.decoder = decoder;
	}


	FieldType(
		Codec<?> codec,
		TriConsumer<CompoundTag, String, Object> encoder,
		BiFunction<CompoundTag, String, Object> decoder,
		Class<?>... classes
	) {
		this(codec, (prov, out, name, val)->encoder.accept(out, name, val), (prov, in, name)->decoder.apply(in, name), classes);
	}

	@Override
	public void encode(HolderLookup.Provider registries, CompoundTag out, String name, Object value) {
		encoder.accept(registries, out, name, value);
	}

	@Override
	public Object decode(HolderLookup.Provider registries, CompoundTag in, String name) {
		return decoder.apply(registries, in, name);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Codec<Object> codec() {
		return (Codec<Object>) rawCodec;
	}

	public boolean matches(Class<?> clazz) {
		for (Class<?> valid : validClasses) {
			if (valid.isAssignableFrom(clazz)) return true;
		}
		return false;
	}

	public static FieldType from(Class<?> clazz) throws IllegalArgumentException {
		for (FieldType type : values()) {
			if (type.matches(clazz)) return type;
		}
		String supported = Arrays.stream(values())
			.flatMap(t -> Arrays.stream(t.validClasses))
			.map(Class::getSimpleName)
			.distinct()
			.collect(Collectors.joining(", "));
		throw new IllegalArgumentException(
			"Unsupported type '" + clazz.getName() + "'. Supported scalar types: " + supported
		);
	}
}
