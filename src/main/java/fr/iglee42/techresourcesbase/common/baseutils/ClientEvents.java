package fr.iglee42.techresourcesbase.common.baseutils;

import fr.iglee42.techresourcesbase.TechResourcesBase;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ClientRegistry;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = TechResourcesBase.MODID,bus = Mod.EventBusSubscriber.Bus.MOD,value = Dist.CLIENT)
public class ClientEvents {

    @SubscribeEvent
    public static void clientStuff(FMLClientSetupEvent event){
        //ClientRegistry.registerKeyBinding(KeyBinding.NO_CLIP);
    }
    @Mod.EventBusSubscriber(modid = TechResourcesBase.MODID,bus = Mod.EventBusSubscriber.Bus.FORGE,value = Dist.CLIENT)
    public class Forge {
        @SubscribeEvent
        public static void onKeyInput(InputEvent.KeyInputEvent event) {
            /*if (KeyBinding.NO_CLIP.isDown()) {
                ModMessages.sendToServer(new NoClipC2SPacket());
            }*/
        }
    }

}
