package fr.iglee42.techresourcesbase.utils;

import com.google.gson.JsonObject;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class JsonHelper {

    public static boolean getBoolean(JsonObject json, String name){
        return json.get(name).getAsBoolean();
    }
    public static String getString(JsonObject json, String name){
        return json.get(name).getAsString();
    }
    public static Item getItem(JsonObject json, String name){
        String[] it = ModsUtils.split(name,":");
        return Registry.ITEM.get(new ResourceLocation(it[0],it[1]));
    }

}
