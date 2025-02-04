package fr.iglee42.igleelib.common.network.data;

import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;

import static fr.iglee42.igleelib.IgleeLibrary.MODID;

public record ClearGhostBlockPayload() implements CustomPacketPayload {

    public static final ClearGhostBlockPayload INSTANCE = new ClearGhostBlockPayload();

    public static final Type<ClearGhostBlockPayload> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(MODID, "clear_ghost_block"));
    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }


    public static final StreamCodec<RegistryFriendlyByteBuf, ClearGhostBlockPayload> STREAM_CODEC = StreamCodec.unit(INSTANCE);
}