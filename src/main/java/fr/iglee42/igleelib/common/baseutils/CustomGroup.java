package fr.iglee42.igleelib.common.baseutils;

import fr.iglee42.igleelib.IgleeLibrary;
import fr.iglee42.igleelib.common.init.ModItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class CustomGroup {

    public static final ItemGroup BASE_GROUP = new ItemGroup(IgleeLibrary.MODID + ".base") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItem.MODIUM_INGOT.get());
        }
    };

}
