package fr.iglee42.techresourcesbase.api.blockentities;

import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class SecondTileEntity extends TileEntity implements ITickableTileEntity {

    private int tick;

    public SecondTileEntity(TileEntityType<?> p_155228_) {
        super(p_155228_);
    }

    @Override
    public void tick() {
        if (tick == 20){
            tick = 0;
            second(this.level,worldPosition,getBlockState(),this);
        }
    }

    protected abstract void second(World level, BlockPos pos, BlockState state, SecondTileEntity entity);

    @Override
    public CompoundNBT save(CompoundNBT tag) {
        tag.putInt("tick",tick);
        return super.save(tag);
    }

    @Override
    public void load(BlockState state,CompoundNBT tag) {
        super.load(state,tag);
        tick = tag.getInt("tick");
    }
}
