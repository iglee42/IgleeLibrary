package fr.iglee42.igleelib.api.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.core.registries.BuiltInRegistries;
import fr.iglee42.igleelib.IgleeLibrary;
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
import java.util.function.Function;

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

    public static <O extends Record,T,J extends JsonElement> O createRecordFromJson(Class<O> recordClass , JsonObject json){
        try {
            List<Object> args = new ArrayList<>();
            List<Class<?>> classes = new ArrayList<>();
            for (RecordComponent component : recordClass.getRecordComponents()) {
                if (Integer.class.equals(component.getType()) || int.class.equals(component.getType())) {
                    if (component.isAnnotationPresent(DefaultParameter.class)) {
                        args.add(getIntOrDefault(json, component.getName(),component.getAnnotation(DefaultParameter.class).intValue()));
                    } else {
                        args.add(getInt(json,component.getName()));
                    }
                    classes.add(component.getType());
                } else if (String.class.equals(component.getType())) {
                    if (component.isAnnotationPresent(DefaultParameter.class)) {
                        args.add(getStringOrDefault(json, component.getName(),component.getAnnotation(DefaultParameter.class).stringValue()));
                    } else {
                        args.add(getString(json,component.getName()));
                    }
                    classes.add(component.getType());
                } else if (Boolean.class.equals(component.getType()) ||boolean.class.equals(component.getType())) {
                    if (component.isAnnotationPresent(DefaultParameter.class)) {
                        args.add(getBooleanOrDefault(json, component.getName(),component.getAnnotation(DefaultParameter.class).booleanValue()));
                    } else {
                        args.add(getBoolean(json,component.getName()));
                    }
                    classes.add(component.getType());
                } else if (Item.class.equals(component.getType())) {
                    if (component.isAnnotationPresent(DefaultParameter.class)) {
                        args.add(getItemOrDefault(json, component.getName(), BuiltInRegistries.ITEM.get(ResourceLocation.parse(component.getAnnotation(DefaultParameter.class).itemValue()))));
                    } else {
                        args.add(getItem(json,component.getName()));
                    }
                    classes.add(component.getType());
                } else if (Block.class.equals(component.getType())) {
                    if (component.isAnnotationPresent(DefaultParameter.class)) {
                        args.add(getBlockOrDefault(json, component.getName(),BuiltInRegistries.BLOCK.get(ResourceLocation.parse(component.getAnnotation(DefaultParameter.class).blockValue()))));
                    } else {
                        args.add(getBlock(json,component.getName()));
                    }
                    classes.add(component.getType());
                } else if (EntityType.class.equals(component.getType())) {
                    if (component.isAnnotationPresent(DefaultParameter.class)) {
                        args.add(getEntityTypeOrDefault(json, component.getName(),BuiltInRegistries.ENTITY_TYPE.get(ResourceLocation.parse(component.getAnnotation(DefaultParameter.class).entityTypeValue()))));
                    } else {
                        args.add(getEntityType(json,component.getName()));
                    }
                    classes.add(component.getType());
                } else if (ResourceLocation.class.equals(component.getType())) {
                    if (component.isAnnotationPresent(DefaultParameter.class)) {
                        args.add(ResourceLocation.tryParse(getStringOrDefault(json,component.getName(),component.getAnnotation(DefaultParameter.class).stringValue())));
                    } else {
                        args.add(ResourceLocation.tryParse(getString(json,component.getName())));
                    }
                    classes.add(component.getType());
                } else {
                    Function<J,T> parser = (Function<J,T>) IgleeLibrary.getClassParser(component.getType());
                    args.add(parser.apply((J) json.get(component.getName())));
                    classes.add(component.getType());
                }
            }
            return recordClass.getConstructor(classes.toArray(new Class[]{})).newInstance(args.toArray(new Object[]{}));
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e){
            e.printStackTrace();
        }
        return null;
    }

}
