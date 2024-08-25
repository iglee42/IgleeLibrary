package fr.iglee42.igleelib.api.utils;

import com.google.gson.JsonObject;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.Block;

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
        return BuiltInRegistries.ITEM.get(ResourceLocation.fromNamespaceAndPath(it[0],it[1]));
    }

    public static Block getBlock(JsonObject json, String name){
        String[] it = ModsUtils.split(getString(json, name),":");
        return BuiltInRegistries.BLOCK.get(ResourceLocation.fromNamespaceAndPath(it[0],it[1]));
    }

    public static EntityType<?> getEntityType(JsonObject json, String name){
        String[] it = ModsUtils.split(getString(json,name),":");
        return BuiltInRegistries.ENTITY_TYPE.get(ResourceLocation.fromNamespaceAndPath(it[0],it[1]));
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
        return BuiltInRegistries.ITEM.get(ResourceLocation.fromNamespaceAndPath(it[0],it[1]));
    }

    public static Block getBlockOrDefault(JsonObject json, String name,Block def){
        if (!json.has(name)) return def;
        String[] it = ModsUtils.split(getString(json, name),":");
        return BuiltInRegistries.BLOCK.get(ResourceLocation.fromNamespaceAndPath(it[0],it[1]));
    }

    public static EntityType<?> getEntityTypeOrDefault(JsonObject json, String name,EntityType<?> def){
        if (!json.has(name)) return def;
        String[] it = ModsUtils.split(getString(json,name),":");
        return BuiltInRegistries.ENTITY_TYPE.get(ResourceLocation.fromNamespaceAndPath(it[0],it[1]));
    }

    public static <O extends Record> O createRecordFromJson(Class<O> recordClass ,JsonObject json){
        try {
            List<Object> args = new ArrayList<>();
            List<Class<?>> classes = new ArrayList<>();
            for (RecordComponent components : recordClass.getRecordComponents()) {
                if (Integer.class.equals(components.getType()) || int.class.equals(components.getType())) {
                    if (components.isAnnotationPresent(DefaultParameter.class)) {
                        args.add(getIntOrDefault(json, components.getName(),components.getAnnotation(DefaultParameter.class).intValue()));
                    } else {
                        args.add(getInt(json,components.getName()));
                    }
                    classes.add(components.getType());
                } else if (String.class.equals(components.getType())) {
                    if (components.isAnnotationPresent(DefaultParameter.class)) {
                        args.add(getStringOrDefault(json, components.getName(),components.getAnnotation(DefaultParameter.class).stringValue()));
                    } else {
                        args.add(getString(json,components.getName()));
                    }
                    classes.add(components.getType());
                } else if (Boolean.class.equals(components.getType()) ||boolean.class.equals(components.getType())) {
                    if (components.isAnnotationPresent(DefaultParameter.class)) {
                        args.add(getBooleanOrDefault(json, components.getName(),components.getAnnotation(DefaultParameter.class).booleanValue()));
                    } else {
                        args.add(getBoolean(json,components.getName()));
                    }
                    classes.add(components.getType());
                } else if (Item.class.equals(components.getType())) {
                    if (components.isAnnotationPresent(DefaultParameter.class)) {
                            args.add(getItemOrDefault(json, components.getName(), BuiltInRegistries.ITEM.get(ResourceLocation.parse(components.getAnnotation(DefaultParameter.class).itemValue()))));
                    } else {
                        args.add(getItem(json,components.getName()));
                    }
                    classes.add(components.getType());
                } else if (Block.class.equals(components.getType())) {
                    if (components.isAnnotationPresent(DefaultParameter.class)) {
                        args.add(getBlockOrDefault(json, components.getName(),BuiltInRegistries.BLOCK.get(ResourceLocation.parse(components.getAnnotation(DefaultParameter.class).blockValue()))));
                    } else {
                        args.add(getBlock(json,components.getName()));
                    }
                    classes.add(components.getType());
                } else if (EntityType.class.equals(components.getType())) {
                    if (components.isAnnotationPresent(DefaultParameter.class)) {
                        args.add(getEntityTypeOrDefault(json, components.getName(),BuiltInRegistries.ENTITY_TYPE.get(ResourceLocation.parse(components.getAnnotation(DefaultParameter.class).entityTypeValue()))));
                    } else {
                        args.add(getEntityType(json,components.getName()));
                    }
                    classes.add(components.getType());
                } else if (ResourceLocation.class.equals(components.getType())) {
                    if (components.isAnnotationPresent(DefaultParameter.class)) {
                        args.add(ResourceLocation.tryParse(getStringOrDefault(json,components.getName(),components.getAnnotation(DefaultParameter.class).stringValue())));
                    } else {
                        args.add(ResourceLocation.tryParse(getString(json,components.getName())));
                    }
                    classes.add(components.getType());
                } else {
                    throw new IllegalArgumentException("The parameter type is not supported !");
                }
            }
            return recordClass.getConstructor(classes.toArray(new Class[]{})).newInstance(args.toArray(new Object[]{}));
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e){
            e.printStackTrace();
        }
        return null;
    }

}
