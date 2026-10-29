package fr.iglee42.igleelib.blockentities.data;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Save {

	boolean synchronize() default false;

	boolean itemSaved() default false;

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
