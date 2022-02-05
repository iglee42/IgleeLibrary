package fr.iglee42.techresourcesbase.blocks;

import fr.iglee42.techresourcesbase.init.ModDimension;
import fr.iglee42.techresourcesbase.world.MTeleporter;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.pathfinding.PathType;
import net.minecraft.server.MinecraftServer;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

public class MineralPortalBlock extends Block {

    public static final DirectionProperty FACING = HorizontalBlock.FACING;
    public static final BooleanProperty OPEN = BooleanProperty.create("open");

    public MineralPortalBlock() {
        super(Properties.of(Material.HEAVY_METAL).strength(5.0f,10.0f).noOcclusion());
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(OPEN, Boolean.FALSE));
    }

    public BlockState getStateForPlacement(BlockItemUseContext p_196258_1_) {
        return this.defaultBlockState().setValue(FACING, p_196258_1_.getHorizontalDirection().getClockWise());
    }

    public BlockState rotate(BlockState p_185499_1_, Rotation p_185499_2_) {
        return p_185499_1_.setValue(FACING, p_185499_2_.rotate(p_185499_1_.getValue(FACING)));
    }

    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> p_206840_1_) {
        p_206840_1_.add(FACING,OPEN);
    }

    public boolean isPathfindable(BlockState p_196266_1_, IBlockReader p_196266_2_, BlockPos p_196266_3_, PathType p_196266_4_) {
        return false;
    }

    @OnlyIn(Dist.CLIENT)
    public int getDustColor(BlockState p_189876_1_, IBlockReader p_189876_2_, BlockPos p_189876_3_) {
        return p_189876_1_.getMapColor(p_189876_2_, p_189876_3_).col;
    }

    @Override
    public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand p_225533_5_, BlockRayTraceResult p_225533_6_) {
        if (player.isCrouching()){
            boolean b = state.getValue(OPEN);
            worldIn.setBlock(pos,state.setValue(OPEN,!b),0);
        } else {
            if (state.getValue(OPEN)){
                MinecraftServer server = worldIn.getServer();

                if (server != null) {
                    if (worldIn.dimension() == ModDimension.MINERAL_DIMENSION_WORLD) {
                        ServerWorld overWorld = server.getLevel(World.OVERWORLD);
                        if (overWorld != null) {
                            player.changeDimension(overWorld, new MTeleporter(pos, true));
                            return ActionResultType.SUCCESS;
                        }
                    } else {
                        ServerWorld mdim = server.getLevel(ModDimension.MINERAL_DIMENSION_WORLD);
                        if (mdim != null) {
                            player.changeDimension(mdim, new MTeleporter(pos, false));
                            return ActionResultType.SUCCESS;
                        }
                    }
                }
            }
        }
        return ActionResultType.PASS;
    }

    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState p_180655_1_, World p_180655_2_, BlockPos p_180655_3_, Random p_180655_4_) {
        if (p_180655_4_.nextInt(100) == 0) {
            p_180655_2_.playLocalSound((double) p_180655_3_.getX() + 0.5D, (double) p_180655_3_.getY() + 0.5D, (double) p_180655_3_.getZ() + 0.5D, SoundEvents.PORTAL_AMBIENT, SoundCategory.BLOCKS, 0.5F, p_180655_4_.nextFloat() * 0.4F + 0.8F, false);
        }

        for (int i = 0; i < 4; ++i) {
            double d0 = (double) p_180655_3_.getX() + p_180655_4_.nextDouble();
            double d1 = (double) p_180655_3_.getY() + p_180655_4_.nextDouble();
            double d2 = (double) p_180655_3_.getZ() + p_180655_4_.nextDouble();
            double d3 = ((double) p_180655_4_.nextFloat() - 0.5D) * 0.5D;
            double d4 = ((double) p_180655_4_.nextFloat() - 0.5D) * 0.5D;
            double d5 = ((double) p_180655_4_.nextFloat() - 0.5D) * 0.5D;
            int j = p_180655_4_.nextInt(2) * 2 - 1;
            if (!p_180655_2_.getBlockState(p_180655_3_.west()).is(this) && !p_180655_2_.getBlockState(p_180655_3_.east()).is(this)) {
                d0 = (double) p_180655_3_.getX() + 0.5D + 0.25D * (double) j;
                d3 = (double) (p_180655_4_.nextFloat() * 2.0F * (float) j);
            } else {
                d2 = (double) p_180655_3_.getZ() + 0.5D + 0.25D * (double) j;
                d5 = (double) (p_180655_4_.nextFloat() * 2.0F * (float) j);
            }

            p_180655_2_.addParticle(ParticleTypes.TOTEM_OF_UNDYING, d0, d1, d2, d3, d4, d5);
        }
    }
}
