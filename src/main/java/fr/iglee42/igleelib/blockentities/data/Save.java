package fr.iglee42.igleelib.blockentities.data;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks a field to be persisted by {@link fr.iglee42.igleelib.blockentities.SavedBlockEntity}.
 * <p>
 * Runtime behavior for a {@code @Save} field:
 * <ul>
 *     <li>World NBT save/load: always considered during {@code saveAdditional}/{@code loadAdditional}.</li>
 *     <li>Client sync: included in {@code getUpdateTag}/{@code handleUpdateTag} only when {@link #synchronize()} is true.</li>
 *     <li>Item transfer: copied to/from item components only when {@link #itemSaved()} is true.</li>
 * </ul>
 * Type resolution is delegated to {@link FieldSerializer} and supports built-in scalar types, enums,
 * classes implementing {@code INBTSerializable}, classes exposing a static {@code CODEC},
 * plus {@code List<T>} and {@code Map<K,V>} compositions.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Save {

	/**
	 * Enables client synchronization for this field.
	 * <p>
	 * Cases:
	 * <ul>
	 *     <li>{@code false} (default): field is server-persistent only.</li>
	 *     <li>{@code true}: field is included in update tags sent to clients.</li>
	 * </ul>
	 */
	boolean synchronize() default false;

	/**
	 * Enables transfer between block entity state and item data components.
	 * <p>
	 * Cases:
	 * <ul>
	 *     <li>{@code false} (default): no item component transfer is attempted.</li>
	 *     <li>{@code true}: transfer uses {@link #component()} to locate the destination component type.</li>
	 * </ul>
	 */
	boolean itemSaved() default false;

	/**
	 * Component provider used when {@link #itemSaved()} is enabled.
	 * <p>
	 * Cases:
	 * <ul>
	 *     <li>{@link NoComponentProvider} (default): returns {@code null}, so transfer is skipped.</li>
	 *     <li>Custom provider: must expose a public no-arg constructor and return a component type matching the field value type.</li>
	 * </ul>
	 */
	Class<? extends ComponentProvider<?>> component()
		default NoComponentProvider.class;

	/**
	 * Explicit element type for {@code List} fields.
	 * Only needed when the field is declared as a raw {@code List} without generic type parameters.
	 * Prefer using {@code List<ElementType>} instead.
	 */
	Class<?> elementType() default void.class;

	/**
	 * Explicit key type for {@code Map} fields.
	 * Only needed when the field is declared as a raw {@code Map} without generic type parameters.
	 * Prefer using {@code Map<KeyType, ValueType>} instead.
	 */
	Class<?> keyType() default void.class;

	/**
	 * Explicit value type for {@code Map} fields.
	 * Only needed when the field is declared as a raw {@code Map} without generic type parameters.
	 * Prefer using {@code Map<KeyType, ValueType>} instead.
	 */
	Class<?> valueType() default void.class;

}
