package fr.iglee42.igleelib.common.blocks.entity;

import fr.iglee42.igleelib.common.blocks.GhostBlock;
import fr.iglee42.igleelib.common.init.ModBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.client.model.data.ModelDataMap;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class GhostBlockEntity extends TileEntity implements ITickableTileEntity {
    private BlockState stockedBlock = Blocks.BEDROCK.defaultBlockState();
    private int dispearTime = -1;

    public GhostBlockEntity() {
        super(ModBlockEntities.GHOST_BLOCK.get());
    }




    @Override
    public CompoundNBT save(CompoundNBT tag) {
        tag.put("stockedBlock", NBTUtil.writeBlockState(stockedBlock));
        tag.putInt("dispearTime",dispearTime);
        return super.save(tag);
    }


    @Override
    public void load(BlockState state, CompoundNBT tag) {
        this.stockedBlock = NBTUtil.readBlockState(tag.getCompound("stockedBlock"));
        this.dispearTime = tag.getInt("dispearTime");
        super.load(state, tag);
    }

    @Override
    public CompoundNBT getUpdateTag() {
        return this.save(new CompoundNBT());
    }


    @Override
    public void handleUpdateTag(BlockState state,CompoundNBT tag) {
        this.load(state,tag);
    }

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(this.worldPosition,2,getUpdateTag());
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        this.stockedBlock = NBTUtil.readBlockState(pkt.getTag().getCompound("stockedBlock"));
        this.dispearTime = pkt.getTag().getInt("dispearTime");
    }

    @Nonnull
    @Override
    public IModelData getModelData() {
        if (this.remove) super.getModelData();
        ModelDataMap.Builder builder = new ModelDataMap.Builder()
                .withInitial(GhostBlock.PS_BLOCKSTATE, stockedBlock)
                .withInitial(GhostBlock.PS_FLUIDSTATE, stockedBlock.getFluidState());
        return builder.build();
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

    @Override
    public void tick() {
        if (dispearTime > 0){
            dispearTime--;
        }
        if (dispearTime == 0){
            level.setBlockAndUpdate(this.worldPosition, Blocks.AIR.defaultBlockState());
        }
    }
}