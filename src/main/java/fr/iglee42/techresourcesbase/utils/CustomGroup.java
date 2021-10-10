package fr.iglee42.techresourcesbase.utils;

import fr.iglee42.techresourcesbase.TechResourcesBase;
import fr.iglee42.techresourcesbase.init.ModItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class CustomGroup {

    public static final ItemGroup BASE_GROUP = new ItemGroup(TechResourcesBase.MODID + ".base") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItem.MODIUM_INGOT.get());
        }
    };
    public static final ItemGroup GENERATOR_GROUP = new ItemGroup(TechResourcesBase.MODID + ".generator") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItem.MODIUM_GENERATOR.get());
        }
    };
}
