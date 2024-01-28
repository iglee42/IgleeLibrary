package fr.iglee42.igleelib.api.utils;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public interface ITickableRecipe<B extends BlockEntity> {

    default void start(Level level, BlockPos pos, BlockState state, B be){}

    default void tick(Level level, BlockPos pos, BlockState state,int progress, B be){}

    default void second(Level level, BlockPos pos, BlockState state,int progress, B be){}

    default void finish(Level level, BlockPos pos, BlockState state, B be){}

    boolean canContinue(Level level, BlockPos pos, BlockState state,int progress, B be);

}
