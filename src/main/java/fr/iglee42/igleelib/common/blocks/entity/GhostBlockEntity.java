package fr.iglee42.igleelib.common.blocks.entity;

import fr.iglee42.igleelib.api.utils.ModsUtils;
import fr.iglee42.igleelib.common.blocks.GhostBlock;
import fr.iglee42.igleelib.common.init.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.data.ModelData;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class GhostBlockEntity extends BlockEntity {
    private BlockState stockedBlock = Blocks.BEDROCK.defaultBlockState();
    private BlockState previousStockedBlock = Blocks.BEDROCK.defaultBlockState();
    private int dispearTime = -1;

    public GhostBlockEntity(BlockPos p_155229_, BlockState p_155230_) {
        super(ModBlockEntities.GHOST_BLOCK.get(), p_155229_, p_155230_);
    }


    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.put("stockedBlock", NbtUtils.writeBlockState(stockedBlock));
        tag.putInt("dispearTime",dispearTime);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        if (level != null)
            this.stockedBlock = NbtUtils.readBlockState(this.level.holderLookup(Registries.BLOCK),tag.getCompound("stockedBlock"));
        this.dispearTime = tag.getInt("dispearTime");
    }
    @Override
    public CompoundTag getUpdateTag() {
        return this.saveWithoutMetadata();
    }


    @Override
    public void handleUpdateTag(CompoundTag tag) {
        this.load(tag);
    }

    @Nullable
    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        if (level != null)
            this.stockedBlock = NbtUtils.readBlockState(this.level.holderLookup(Registries.BLOCK),pkt.getTag().getCompound("stockedBlock"));
       this.dispearTime = pkt.getTag().getInt("dispearTime");
    }
    @NotNull
    @Override
    public ModelData getModelData() {
        if (this.remove) super.getModelData();
        ModelData.Builder builder = ModelData.builder()
                .with(GhostBlock.PS_BLOCKSTATE, stockedBlock)
                .with(GhostBlock.PS_FLUIDSTATE, stockedBlock.getFluidState());
        return builder.build();
    }



    public void tick(Level level, BlockPos pos, BlockState state){
        if (!level.isClientSide) {
            if (previousStockedBlock != stockedBlock) {
                level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
                ModsUtils.placeGhostBlock((ServerLevel) level, pos, stockedBlock, dispearTime);
            }
        }
        if (dispearTime > 0){
            dispearTime--;
        }
        if (dispearTime == 0){
            level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
        }
    }

    public BlockState getStockedBlock() {
        return stockedBlock;
    }

    public void setStockedBlock(BlockState stockedBlock) {
        this.stockedBlock = stockedBlock;
    }


    public void setDispearTime(int time) {
        dispearTime = time;
    }
}