package fr.iglee42.igleelib.common.network.handler;

import fr.iglee42.igleelib.IgleeLibrary;
import fr.iglee42.igleelib.common.baseutils.GhostBlock;
import fr.iglee42.igleelib.common.network.data.ClearGhostBlockPayload;
import fr.iglee42.igleelib.common.network.data.CreateGhostBlockPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class ClearGhostBlockPacketHandler {

    public static final ClearGhostBlockPacketHandler INSTANCE = new ClearGhostBlockPacketHandler();

    public static ClearGhostBlockPacketHandler instance(){
        return INSTANCE;
    }


    public void handle(final ClearGhostBlockPayload payload, final IPayloadContext context) {
        context.enqueueWork(IgleeLibrary.ghostBlocks::clear);
    }

}
