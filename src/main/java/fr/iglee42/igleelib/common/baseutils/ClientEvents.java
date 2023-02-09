package fr.iglee42.igleelib.baseutils;

import fr.iglee42.igleelib.IgleeLib;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = IgleeLib.MODID,bus = Mod.EventBusSubscriber.Bus.MOD,value = Dist.CLIENT)
public class ClientEvents {

    @SubscribeEvent
    public static void clientStuff(FMLClientSetupEvent event){
        //ClientRegistry.registerKeyBinding(KeyBinding.NO_CLIP);
    }
    @Mod.EventBusSubscriber(modid = IgleeLib.MODID,bus = Mod.EventBusSubscriber.Bus.FORGE,value = Dist.CLIENT)
    public class Forge {

    }

}
