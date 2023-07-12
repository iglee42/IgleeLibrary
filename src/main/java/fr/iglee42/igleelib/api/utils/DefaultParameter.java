package fr.iglee42.igleelib.api.utils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface DefaultParameter {

    int intValue() default 1;
    String stringValue() default "";
    boolean booleanValue() default false;
    String itemValue() default "minecraft:air";
    String blockValue() default "minecraft:air";
    String entityTypeValue() default "minecraft:armor_stand";

    String enchantmentValue() default "minecraft:protection";


}
