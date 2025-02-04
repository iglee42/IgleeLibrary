package fr.iglee42.igleelib.common.network.handler;

import fr.iglee42.igleelib.IgleeLibrary;
import fr.iglee42.igleelib.common.baseutils.GhostBlock;
import fr.iglee42.igleelib.common.network.data.CreateGhostBlockPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class CreateGhostBlockPacketHandler {

    public static final CreateGhostBlockPacketHandler INSTANCE = new CreateGhostBlockPacketHandler();

    public static CreateGhostBlockPacketHandler instance(){
        return INSTANCE;
    }


    public void handle(final CreateGhostBlockPayload payload, final IPayloadContext context) {
        context.enqueueWork(() -> {
            IgleeLibrary.ghostBlocks.add(new GhostBlock(payload.pos(),payload.state(),payload.time()));
        });
    }

}
