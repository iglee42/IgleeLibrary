package fr.iglee42.techresourcesbase.api.utils;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.Block;

public class InventoryUtil {
    public static boolean hasPlayerStackInInventory(Player player, Item item) {
        for(int i = 0; i < player.getInventory().getContainerSize(); i++) {
            ItemStack currentStack = player.getInventory().getItem(i);
            if (!currentStack.isEmpty() && currentStack.sameItem(new ItemStack(item))) {
                return true;
            }
        }

        return false;
    }

    public static int getFirstInventoryIndex(Player player, Item item) {
        for(int i = 0; i < player.getInventory().getContainerSize(); i++) {
            ItemStack currentStack = player.getInventory().getItem(i);
            if (!currentStack.isEmpty() && currentStack.sameItem(new ItemStack(item))) {
                return i;
            }
        }

        return -1;
    }

    public static Item getItem(String name){
        String[] it = ModsUtils.split(name,":");
        return Registry.ITEM.get(new ResourceLocation(it[0],it[1]));
    }

    public static Block getBlock(String name){
        String[] it = ModsUtils.split(name,":");
        return Registry.BLOCK.get(new ResourceLocation(it[0],it[1]));
    }
    public static Enchantment getEnchantment(String name){
        String[] it = ModsUtils.split(name,":");
        return Registry.ENCHANTMENT.get(new ResourceLocation(it[0],it[1]));
    }
    public static EntityType<?> getEntity(String name){
        String[] it = ModsUtils.split(name,":");
        return Registry.ENTITY_TYPE.get(new ResourceLocation(it[0],it[1]));
    }
}