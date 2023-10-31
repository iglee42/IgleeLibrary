package fr.iglee42.igleelib;

import fr.iglee42.igleelib.common.config.IgleeLibCommonConfig;
import fr.iglee42.igleelib.common.init.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;


@Mod(IgleeLibrary.MODID)
public class IgleeLibrary {

    public static final String MODID = "igleelib";

    public IgleeLibrary() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        ModBlock.BLOCKS.register(bus);
        ModBlockEntities.BLOCK_ENTITIES.register(bus);
        ModItem.ITEMS.register(bus);
        ModCreativeTab.TABS.register(bus);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, IgleeLibCommonConfig.SPEC,"igleelib-common.toml");

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::addCreative);


    }

    private void setup(FMLCommonSetupEvent e){

        ModMessages.register();
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {
        if (event.getTabKey() == ModCreativeTab.TAB.getKey()){
            ForgeRegistries.ITEMS.getKeys().stream().filter(rs-> rs.getNamespace().equals(MODID)).forEach(rs->
                    event.accept(ForgeRegistries.ITEMS.getValue(rs)));
        }
    }

}
