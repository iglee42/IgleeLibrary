package fr.iglee42.techresourcesbase.utils;

import fr.iglee42.techresourcesbase.init.ModItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraftforge.fml.RegistryObject;

import java.util.Objects;

public enum GessenceType {
    WOOD(Items.OAK_LOG,"wood", ModItem.WOOD_GESSENCE),
    COBBLESTONE(Items.COBBLESTONE,"cobblestone", ModItem.COBBLESTONE_GESSENCE),
    COAL(Items.COAL,"coal", ModItem.COAL_GESSENCE),
    IRON(Items.IRON_INGOT,"iron", ModItem.IRON_GESSENCE),
    GOLD(Items.GOLD_INGOT,"gold", ModItem.GOLD_GESSENCE),
    REDSTONE(Items.REDSTONE,"redstone", ModItem.REDSTONE_GESSENCE),
    LAPIS(Items.LAPIS_LAZULI,"lapis", ModItem.LAPIS_GESSENCE),
    DIAMOND(Items.DIAMOND,"diamond", ModItem.DIAMOND_GESSENCE),
    EMERALD(Items.EMERALD,"emerald", ModItem.EMERALD_GESSENCE),
    NETHERITE(Items.NETHERITE_SCRAP,"netherite", ModItem.NETHERITE_GESSENCE),
    QUARTZ(Items.QUARTZ,"quartz", ModItem.QUARTZ_GESSENCE),
    MODIUM(ModItem.MODIUM_INGOT.get(),"modium",ModItem.MODIUM_GESSENCE),
    DERIUM(ModItem.DERIUM_INGOT.get(), "derium",ModItem.DERIUM_GESSENCE),
    BLAZUM(ModItem.BLAZUM_INGOT.get(), "blazum",ModItem.BLAZUM_GESSENCE),
    LAVIUM(ModItem.LAVIUM_INGOT.get(), "lavium",ModItem.LAVIUM_GESSENCE)
    ;

    private Item item;
    private RegistryObject<Item> gessence;
    private String resource_name;

    GessenceType(Item item, String resource_name, RegistryObject<Item> gessence) {
        this.item = item;
        this.resource_name = resource_name;
        this.gessence = gessence;
    }

    public static GessenceType getByResourceName(String name){
        for (GessenceType type : values()){
            if (Objects.equals(type.getRessourceName(), name)) return type;
        }
        return null;
    }

    public Item getItem() {
        return item;
    }

    public String getRessourceName() {
        return resource_name;
    }

    public RegistryObject<Item> getGessence() {
        return gessence;
    }
}
