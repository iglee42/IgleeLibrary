package fr.iglee42.igleelib.common.blocks.entity;

import fr.iglee42.igleelib.common.blocks.GhostBlock;
import fr.iglee42.igleelib.common.init.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.data.ModelData;
import org.jetbrains.annotations.NotNull;

public class GhostBlockEntity extends BlockEntity {
    private BlockState stockedBlock = Blocks.BEDROCK.defaultBlockState();
    private BlockState previousStockedBlock = Blocks.BEDROCK.defaultBlockState();
    private int dispearTime = -1;

    public GhostBlockEntity(BlockPos p_155229_, BlockState p_155230_) {
        super(ModBlockEntities.GHOST_BLOCK.get(), p_155229_, p_155230_);
    }



    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider p_323635_) {
        super.saveAdditional(tag, p_323635_);
        tag.put("stockedBlock", NbtUtils.writeBlockState(stockedBlock));
        tag.putInt("dispearTime",dispearTime);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider p_338445_) {
        super.loadAdditional(tag, p_338445_);
        if (level != null)
            this.stockedBlock = NbtUtils.readBlockState(this.level.holderLookup(Registries.BLOCK),tag.getCompound("stockedBlock"));
        this.dispearTime = tag.getInt("dispearTime");
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
                level.sendBlockUpdated(pos,state,state,Block.UPDATE_CLIENTS);
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