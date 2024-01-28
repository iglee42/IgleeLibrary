package fr.iglee42.igleelib.api.utils;

import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface ITickableRecipe<B extends TileEntity> {

    default void start(World level, BlockPos pos, BlockState state, B be){}

    default void tick(World level, BlockPos pos, BlockState state,int progress, B be){}

    default void second(World level, BlockPos pos, BlockState state, int progress, B be){}

    default void finish(World level, BlockPos pos, BlockState state, B be){}

    boolean canContinue(World level, BlockPos pos, BlockState state, int progress, B be);

}
