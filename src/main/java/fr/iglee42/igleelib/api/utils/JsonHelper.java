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

    public static <O extends Record> O createRecordFromJson(Class<O> recordClass ,JsonObject json){
        try {
            List<Object> args = new ArrayList<>();
            for (RecordComponent components : recordClass.getRecordComponents()) {
                if (Integer.class.equals(components.getType())) {
                    args.add(getInt(json, components.getName()));
                } else if (String.class.equals(components.getType())) {
                    args.add(getString(json, components.getName()));
                } else if (Boolean.class.equals(components.getType())) {
                    args.add(getBoolean(json, components.getName()));
                } else if (Item.class.equals(components.getType())) {
                    args.add(getItem(json, components.getName()));
                } else if (Block.class.equals(components.getType())) {
                    args.add(getBlock(json, components.getName()));
                } else if (EntityType.class.equals(components.getType())) {
                    args.add(getEntityType(json, components.getName()));
                } else if (Enchantment.class.equals(components.getType())) {
                    args.add(getEnchantment(json, components.getName()));
                } else if (ResourceLocation.class.equals(components.getType())) {
                    args.add(ResourceLocation.tryParse(getString(json, components.getName())));
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
