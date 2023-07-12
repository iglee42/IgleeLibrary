package fr.iglee42.igleelib.api.utils;

import com.google.errorprone.annotations.IncompatibleModifiers;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import javax.lang.model.element.Modifier;
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
