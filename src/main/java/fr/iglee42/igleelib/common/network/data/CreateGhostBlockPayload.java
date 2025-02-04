package fr.iglee42.igleelib.common.network.data;

import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;

import static fr.iglee42.igleelib.IgleeLibrary.MODID;

public record CreateGhostBlockPayload(
        BlockPos pos,
        BlockState state,
        int time
) implements CustomPacketPayload {

    public static final Type<CreateGhostBlockPayload> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(MODID, "create_ghost_block"));
    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }


    public static final StreamCodec<RegistryFriendlyByteBuf, CreateGhostBlockPayload> STREAM_CODEC = StreamCodec.composite(
            BlockPos.STREAM_CODEC, CreateGhostBlockPayload::pos,
            ByteBufCodecs.fromCodec(BlockState.CODEC), CreateGhostBlockPayload::state,
            ByteBufCodecs.INT, CreateGhostBlockPayload::time,
            CreateGhostBlockPayload::new
    );
}