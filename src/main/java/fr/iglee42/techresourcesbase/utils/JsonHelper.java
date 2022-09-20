package fr.iglee42.techresourcesbase.utils;

import com.google.gson.JsonObject;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.Block;

public class JsonHelper {

    public static boolean getBoolean(JsonObject json, String name){
        return json.get(name).getAsBoolean();
    }
    public static String getString(JsonObject json, String name){
        return json.get(name).getAsString();
    }
    public static Item getItem(JsonObject json, String name){
        String[] it = ModsUtils.split(getString(json,name),":");
        return Registry.ITEM.get(new ResourceLocation(it[0],it[1]));
    }

    public static Block getBlock(JsonObject json, String name){
        String[] it = ModsUtils.split(getString(json, name),":");
        return Registry.BLOCK.get(new ResourceLocation(it[0],it[1]));
    }

    public static EntityType<?> getEntityType(JsonObject json, String name){
        String[] it = ModsUtils.split(getString(json,name),":");
        return Registry.ENTITY_TYPE.get(new ResourceLocation(it[0],it[1]));
    }
    public static Enchantment getEnchantment(JsonObject json, String name){
        String[] it = ModsUtils.split(getString(json,name),":");
        return Registry.ENCHANTMENT.get(new ResourceLocation(it[0],it[1]));
    }


}
