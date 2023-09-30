package fr.iglee42.igleelib.common.init;

import fr.iglee42.igleelib.IgleeLibrary;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeTab {

    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, IgleeLibrary.MODID);

    public static final RegistryObject<CreativeModeTab> TAB = TABS.register("tab", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.igleelib.base"))
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(()->ModItem.MODIUM_INGOT.get().getDefaultInstance())
            .build());
}
