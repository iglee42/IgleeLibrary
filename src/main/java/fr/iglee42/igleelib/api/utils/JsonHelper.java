package fr.iglee42.igleelib.api.utils;

import com.google.gson.JsonObject;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.RecordComponent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JsonHelper {

    public static boolean getBoolean(JsonObject json, String name){
        return json.get(name).getAsBoolean();
    }
    public static String getString(JsonObject json, String name){
        return json.get(name).getAsString();
    }
    public static int getInt(JsonObject json, String name){
        return json.get(name).getAsInt();
    }
    public static Item getItem(JsonObject json, String name){
        String[] it = ModsUtils.split(getString(json,name),":");
        return ForgeRegistries.ITEMS.getValue(new ResourceLocation(it[0],it[1]));
    }

    public static Block getBlock(JsonObject json, String name){
        String[] it = ModsUtils.split(getString(json, name),":");
        return ForgeRegistries.BLOCKS.getValue(new ResourceLocation(it[0],it[1]));
    }

    public static EntityType<?> getEntityType(JsonObject json, String name){
        String[] it = ModsUtils.split(getString(json,name),":");
        return ForgeRegistries.ENTITIES.getValue(new ResourceLocation(it[0],it[1]));
    }
    public static Enchantment getEnchantment(JsonObject json, String name){
        String[] it = ModsUtils.split(getString(json,name),":");
        return ForgeRegistries.ENCHANTMENTS.getValue(new ResourceLocation(it[0],it[1]));
    }
    public static boolean getBooleanOrDefault(JsonObject json, String name,boolean def){
        if (!json.has(name)) return def;
        return json.get(name).getAsBoolean();
    }
    public static String getStringOrDefault(JsonObject json, String name,String def){
        if (!json.has(name)) return def;
        return json.get(name).getAsString();
    }

    public static int getIntOrDefault(JsonObject json,String name,int def){
        if (!json.has(name)) return def;
        return json.get(name).getAsInt();
    }
    public static Item getItemOrDefault(JsonObject json, String name,Item def){
        if (!json.has(name)) return def;
        String[] it = ModsUtils.split(getString(json,name),":");
        return ForgeRegistries.ITEMS.getValue(new ResourceLocation(it[0],it[1]));
    }

    public static Block getBlockOrDefault(JsonObject json, String name,Block def){
        if (!json.has(name)) return def;
        String[] it = ModsUtils.split(getString(json, name),":");
        return ForgeRegistries.BLOCKS.getValue(new ResourceLocation(it[0],it[1]));
    }

    public static EntityType<?> getEntityTypeOrDefault(JsonObject json, String name,EntityType<?> def){
        if (!json.has(name)) return def;
        String[] it = ModsUtils.split(getString(json,name),":");
        return ForgeRegistries.ENTITIES.getValue(new ResourceLocation(it[0],it[1]));
    }
    public static Enchantment getEnchantmentOrDefault(JsonObject json, String name,Enchantment def){
        if (!json.has(name)) return def;
        String[] it = ModsUtils.split(getString(json,name),":");
        return ForgeRegistries.ENCHANTMENTS.getValue(new ResourceLocation(it[0],it[1]));
    }

    public static <O extends Record> O createRecordFromJson(Class<O> recordClass ,JsonObject json,CustomParameter... defaultParameters){
        try {
            List<Object> args = new ArrayList<>();
            for (RecordComponent components : recordClass.getRecordComponents()) {
                if (Arrays.stream(defaultParameters).anyMatch(p->p.name().equals(components.getName()))){
                    args.add(Arrays.stream(defaultParameters).filter(p->p.name().equals(components.getName())).findAny().get().value());
                    continue;
                }
                if (Integer.class.equals(components.getType()) || int.class.equals(components.getType())) {
                    if (components.isAnnotationPresent(DefaultParameter.class)) {
                        if (components.isAnnotationPresent(OptionalParameter.class) && !json.has(components.getName())){
                            args.add(components.getAnnotation(DefaultParameter.class).intValue());
                        } else {
                            args.add(getIntOrDefault(json, components.getName(),components.getAnnotation(DefaultParameter.class).intValue()));
                        }
                    } else {
                        args.add(getInt(json,components.getName()));
                    }
                } else if (String.class.equals(components.getType())) {
                    if (components.isAnnotationPresent(DefaultParameter.class)) {
                        if (components.isAnnotationPresent(OptionalParameter.class) && !json.has(components.getName())){
                            args.add(components.getAnnotation(DefaultParameter.class).stringValue());
                        } else {
                            args.add(getStringOrDefault(json, components.getName(),components.getAnnotation(DefaultParameter.class).stringValue()));
                        }
                    } else {
                        args.add(getString(json,components.getName()));
                    }
                } else if (Boolean.class.equals(components.getType()) ||boolean.class.equals(components.getType())) {
                    if (components.isAnnotationPresent(DefaultParameter.class)) {
                        if (components.isAnnotationPresent(OptionalParameter.class) && !json.has(components.getName())){
                            args.add(components.getAnnotation(DefaultParameter.class).booleanValue());
                        } else {
                            args.add(getBooleanOrDefault(json, components.getName(),components.getAnnotation(DefaultParameter.class).booleanValue()));
                        }
                    } else {
                        args.add(getBoolean(json,components.getName()));
                    }
                } else if (Item.class.equals(components.getType())) {
                    if (components.isAnnotationPresent(DefaultParameter.class)) {
                        if (components.isAnnotationPresent(OptionalParameter.class) && !json.has(components.getName())){
                            args.add(ForgeRegistries.ITEMS.getValue(new ResourceLocation(components.getAnnotation(DefaultParameter.class).itemValue())));
                        } else {
                            args.add(getItemOrDefault(json, components.getName(), ForgeRegistries.ITEMS.getValue(new ResourceLocation(components.getAnnotation(DefaultParameter.class).itemValue()))));
                        }
                    } else {
                        args.add(getItem(json,components.getName()));
                    }
                } else if (Block.class.equals(components.getType())) {
                    if (components.isAnnotationPresent(DefaultParameter.class)) {
                        if (components.isAnnotationPresent(OptionalParameter.class) && !json.has(components.getName())){
                            args.add(ForgeRegistries.BLOCKS.getValue(new ResourceLocation(components.getAnnotation(DefaultParameter.class).blockValue())));
                        } else {
                            args.add(getBlockOrDefault(json, components.getName(),ForgeRegistries.BLOCKS.getValue(new ResourceLocation(components.getAnnotation(DefaultParameter.class).blockValue()))));
                        }
                    } else {
                        args.add(getBlock(json,components.getName()));
                    }
                } else if (EntityType.class.equals(components.getType())) {
                    if (components.isAnnotationPresent(DefaultParameter.class)) {
                        if (components.isAnnotationPresent(OptionalParameter.class) && !json.has(components.getName())){
                            args.add(ForgeRegistries.ENTITIES.getValue(new ResourceLocation(components.getAnnotation(DefaultParameter.class).entityTypeValue())));
                        } else {
                            args.add(getEntityTypeOrDefault(json, components.getName(),ForgeRegistries.ENTITIES.getValue(new ResourceLocation(components.getAnnotation(DefaultParameter.class).entityTypeValue()))));
                        }
                    } else {
                        args.add(getEntityType(json,components.getName()));
                    }
                } else if (Enchantment.class.equals(components.getType())) {
                    if (components.isAnnotationPresent(DefaultParameter.class)) {
                        if (components.isAnnotationPresent(OptionalParameter.class) && !json.has(components.getName())){
                            args.add(ForgeRegistries.ENCHANTMENTS.getValue(new ResourceLocation(components.getAnnotation(DefaultParameter.class).enchantmentValue())));
                        } else {
                            args.add(getEnchantmentOrDefault(json, components.getName(),ForgeRegistries.ENCHANTMENTS.getValue(new ResourceLocation(components.getAnnotation(DefaultParameter.class).enchantmentValue()))));
                        }
                    } else {
                        args.add(getEnchantment(json,components.getName()));
                    }
                } else if (ResourceLocation.class.equals(components.getType())) {
                    if (components.isAnnotationPresent(DefaultParameter.class)) {
                        if (components.isAnnotationPresent(OptionalParameter.class) && !json.has(components.getName())){
                            args.add(ResourceLocation.tryParse(components.getAnnotation(DefaultParameter.class).stringValue()));
                        } else {
                            args.add(ResourceLocation.tryParse(getStringOrDefault(json,components.getName(),components.getAnnotation(DefaultParameter.class).stringValue())));
                        }
                    } else {
                        args.add(ResourceLocation.tryParse(getString(json,components.getName())));
                    }
                } else {
                    throw new IllegalArgumentException("The parameter type is not supported !");
                }
            }
            List<Class<?>> classes = new ArrayList<>();
            args.forEach(o -> classes.add(o.getClass()));
            return recordClass.getConstructor(classes.toArray(new Class[]{})).newInstance(args.toArray(new Object[]{}));
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e){
            e.printStackTrace();
        }
        return null;
    }

}
