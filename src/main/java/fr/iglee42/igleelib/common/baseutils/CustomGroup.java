package fr.iglee42.igleelib.common.baseutils;

import fr.iglee42.igleelib.IgleeLib;
import fr.iglee42.igleelib.common.init.ModItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class CustomGroup {

    public static final CreativeModeTab BASE_GROUP = new CreativeModeTab(IgleeLib.MODID + ".base") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItem.MODIUM_INGOT.get());
        }
    };

}
