package fr.iglee42.igleelib.api.utils;

import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

public class InventoryUtil {
    public static boolean hasPlayerStackInInventory(PlayerEntity player, Item item) {
        for(int i = 0; i < player.inventory.getContainerSize(); i++) {
            ItemStack currentStack = player.inventory.getItem(i);
            if (!currentStack.isEmpty() && currentStack.sameItem(new ItemStack(item))) {
                return true;
            }
        }

        return false;
    }

    public static int getFirstInventoryIndex(PlayerEntity player, Item item) {
        for(int i = 0; i < player.inventory.getContainerSize(); i++) {
            ItemStack currentStack = player.inventory.getItem(i);
            if (!currentStack.isEmpty() && currentStack.sameItem(new ItemStack(item))) {
                return i;
            }
        }

        return -1;
    }

    public static Item getItem(String name){
        String[] it = ModsUtils.split(name,":");
        return ForgeRegistries.ITEMS.getValue(new ResourceLocation(it[0],it[1]));
    }

    public static Block getBlock(String name){
        String[] it = ModsUtils.split(name,":");
        return ForgeRegistries.BLOCKS.getValue(new ResourceLocation(it[0],it[1]));
    }
    public static Enchantment getEnchantment(String name){
        String[] it = ModsUtils.split(name,":");
        return ForgeRegistries.ENCHANTMENTS.getValue(new ResourceLocation(it[0],it[1]));
    }
    public static EntityType<?> getEntity(String name){
        String[] it = ModsUtils.split(name,":");
        return ForgeRegistries.ENTITIES.getValue(new ResourceLocation(it[0],it[1]));
    }
}