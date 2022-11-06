package fr.iglee42.techresourcesbase;

import fr.iglee42.techresourcesbase.config.TechResourcesBaseCommonConfig;
import fr.iglee42.techresourcesbase.init.ModBlock;
import fr.iglee42.techresourcesbase.init.ModItem;
import fr.iglee42.techresourcesbase.init.ModMessages;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;


@Mod(TechResourcesBase.MODID)
public class TechResourcesBase {

    public static final String MODID = "techresourcesbase";

    public TechResourcesBase() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        ModBlock.BLOCKS.register(bus);
        ModItem.ITEMS.register(bus);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, TechResourcesBaseCommonConfig.SPEC,"techresourcesbase-common.toml");

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
