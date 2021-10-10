package fr.iglee42.techresourcesbase.items;

import fr.iglee42.techresourcesbase.init.ModItem;
import fr.iglee42.techresourcesbase.utils.CustomGroup;
import fr.iglee42.techresourcesbase.utils.GessenceType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class Gessence extends Item {

    public static boolean isGessence(ItemStack stack){
        if (stack.getItem() == ModItem.WOOD_GESSENCE.get()) return true;
        if (stack.getItem() == ModItem.COBBLESTONE_GESSENCE.get()) return true;
        if (stack.getItem() == ModItem.COAL_GESSENCE.get()) return true;
        if (stack.getItem() == ModItem.IRON_GESSENCE.get()) return true;
        if (stack.getItem() == ModItem.GOLD_GESSENCE.get()) return true;
        if (stack.getItem() == ModItem.REDSTONE_GESSENCE.get()) return true;
        if (stack.getItem() == ModItem.LAPIS_GESSENCE.get()) return true;
        if (stack.getItem() == ModItem.DIAMOND_GESSENCE.get()) return true;
        if (stack.getItem() == ModItem.EMERALD_GESSENCE.get()) return true;
        if (stack.getItem() == ModItem.NETHERITE_GESSENCE.get()) return true;
        if (stack.getItem() == ModItem.MODIUM_GESSENCE.get()) return true;
        if (stack.getItem() == ModItem.DERIUM_GESSENCE.get()) return true;
        if (stack.getItem() == ModItem.BLAZUM_GESSENCE.get()) return true;
        return false;
    }

    private GessenceType type;
    public Gessence(GessenceType type) {
        super(new Item.Properties().tab(CustomGroup.GENERATOR_GROUP));
        this.type = type;
    }

    public GessenceType getType() {
        return type;
    }
}
