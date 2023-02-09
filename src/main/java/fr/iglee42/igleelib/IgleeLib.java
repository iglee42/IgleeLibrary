package fr.iglee42.igleelib;

import fr.iglee42.igleelib.common.config.IgleeLibCommonConfig;
import fr.iglee42.igleelib.common.init.ModBlock;
import fr.iglee42.igleelib.common.init.ModItem;
import fr.iglee42.igleelib.common.init.ModMessages;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;


@Mod(IgleeLib.MODID)
public class IgleeLib {

    public static final String MODID = "igleelib";

    public IgleeLib() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        ModBlock.BLOCKS.register(bus);
        ModItem.ITEMS.register(bus);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, IgleeLibCommonConfig.SPEC,"igleelib-common.toml");

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);


    }

    private void setup(FMLCommonSetupEvent e){

        ModMessages.register();
    }

}
