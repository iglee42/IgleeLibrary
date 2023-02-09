package fr.iglee42.igleelib.api.blockentities;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public abstract class SecondBlockEntity extends BlockEntity {

    private int tick;

    public SecondBlockEntity(BlockEntityType<?> p_155228_, BlockPos p_155229_, BlockState p_155230_) {
        super(p_155228_, p_155229_, p_155230_);
    }

    public static void tick(Level level, BlockPos pos, BlockState state,SecondBlockEntity entity){
        entity.tick++;
        if (entity.tick == 20) {
            entity.tick = 0;
            entity.second(level, pos, state, entity);
        }
    }

    protected abstract void second(Level level, BlockPos pos, BlockState state,SecondBlockEntity entity);

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putInt("tick",tick);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        tick = tag.getInt("tick");
    }
}
