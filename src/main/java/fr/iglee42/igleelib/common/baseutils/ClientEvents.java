package fr.iglee42.igleelib.common.baseutils;

import fr.iglee42.igleelib.IgleeLibrary;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;

import java.util.ArrayList;
import java.util.List;

@EventBusSubscriber(modid = IgleeLibrary.MODID,bus = EventBusSubscriber.Bus.GAME,value = Dist.CLIENT)
public class ClientEvents {

    public static void clientStuff(final FMLClientSetupEvent event) {
    }


    @SubscribeEvent
    public static void clientTick(final ClientTickEvent.Post event){
        List<GhostBlock> toRemove = new ArrayList<>();
        IgleeLibrary.ghostBlocks.forEach(g->{
            if (g.tick()) toRemove.add(g);
        });
        IgleeLibrary.ghostBlocks.removeAll(toRemove);
    }

    @SubscribeEvent
    public static void clientRender(final RenderLevelStageEvent event){
        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_TRIPWIRE_BLOCKS)  return;
        IgleeLibrary.ghostBlocks.stream().filter(g->g.getPos().distToCenterSqr(Minecraft.getInstance().cameraEntity.position()) <= 100 * 100).forEach(g->g.render(event.getPoseStack()));
    }

}
