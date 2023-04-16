package fr.iglee42.igleelib;

import fr.iglee42.igleelib.common.config.IgleeLibCommonConfig;
import fr.iglee42.igleelib.common.init.ModBlock;
import fr.iglee42.igleelib.common.init.ModBlockEntities;
import fr.iglee42.igleelib.common.init.ModItem;
import fr.iglee42.igleelib.common.init.ModMessages;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;


@Mod(IgleeLibrary.MODID)
public class IgleeLibrary {

    public static final String MODID = "igleelib";

    public IgleeLibrary() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        ModBlock.BLOCKS.register(bus);
        ModBlockEntities.BLOCK_ENTITIES.register(bus);
        ModItem.ITEMS.register(bus);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, IgleeLibCommonConfig.SPEC,"igleelib-common.toml");

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);


    }

    private void setup(FMLCommonSetupEvent e){

        ModMessages.register();
    }

    public static void sendClientMessage(String message) {
        LocalPlayer player = Minecraft.getInstance().player;
        player.sendSystemMessage(Component.literal(message));
    }
}
