package fr.iglee42.igleelib.common.network;

import fr.iglee42.igleelib.IgleeLibrary;
import fr.iglee42.igleelib.common.network.data.ClearGhostBlockPayload;
import fr.iglee42.igleelib.common.network.data.CreateGhostBlockPayload;
import fr.iglee42.igleelib.common.network.handler.ClearGhostBlockPacketHandler;
import fr.iglee42.igleelib.common.network.handler.CreateGhostBlockPacketHandler;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber(modid = IgleeLibrary.MODID,bus = EventBusSubscriber.Bus.MOD)
public class ModMessages {
    @SubscribeEvent
    public static void registerNetworking(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar(IgleeLibrary.MODID);

        registrar.playToClient(CreateGhostBlockPayload.TYPE, CreateGhostBlockPayload.STREAM_CODEC, CreateGhostBlockPacketHandler.instance()::handle);
        registrar.playToClient(ClearGhostBlockPayload.TYPE, ClearGhostBlockPayload.STREAM_CODEC, ClearGhostBlockPacketHandler.instance()::handle);
    }

}
