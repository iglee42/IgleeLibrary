package fr.iglee42.techresourcesbase.common.baseutils;

import fr.iglee42.techresourcesbase.TechResourcesBase;
import fr.iglee42.techresourcesbase.common.init.ModItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class CustomGroup {

    public static final ItemGroup BASE_GROUP = new ItemGroup(TechResourcesBase.MODID + ".base") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItem.MODIUM_INGOT.get());
        }
    };

}
