package fr.iglee42.igleelib.common.blocks;

import fr.iglee42.igleelib.common.blocks.entity.GhostBlockEntity;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.client.model.data.ModelProperty;

import javax.annotation.Nullable;

public class GhostBlock extends Block {

    public static final ModelProperty<BlockState> PS_BLOCKSTATE = new ModelProperty<>();
    public static final ModelProperty<FluidState> PS_FLUIDSTATE = new ModelProperty<>();
    public GhostBlock() {
        super(AbstractBlock.Properties.of(Material.GLASS).strength(-1,36000).noOcclusion().noCollission());
    }

    @Override
    public float getShadeBrightness(BlockState p_220080_1_, IBlockReader p_220080_2_, BlockPos p_220080_3_) {
        return 1.0F;
    }

    @Override
    public boolean propagatesSkylightDown(BlockState p_200123_1_, IBlockReader p_200123_2_, BlockPos p_200123_3_) {
        return true;
    }


    @Override
    public ActionResultType use(BlockState p_225533_1_, World level, BlockPos pos, PlayerEntity player, Hand p_225533_5_, BlockRayTraceResult p_225533_6_) {
        if (level.isClientSide()) return ActionResultType.sidedSuccess(level.isClientSide());
        if (level.getBlockEntity(pos) instanceof GhostBlockEntity){
            GhostBlockEntity be = (GhostBlockEntity) level.getBlockEntity(pos);
            if (be.getStockedBlock().is(be.getStockedBlock().getFluidState().createLegacyBlock().getBlock()) && be.getStockedBlock().getFluidState().getType() != Fluids.EMPTY){
                if (player.getMainHandItem().getItem() == be.getStockedBlock().getFluidState().getType().getBucket()){
                    level.setBlockAndUpdate(pos,be.getStockedBlock().getFluidState().createLegacyBlock());
                    if (!player.isCreative()) player.setItemInHand(Hand.MAIN_HAND,new ItemStack(Items.BUCKET));
                }
            }

        }
        return super.use(p_225533_1_, level, pos, player, p_225533_5_, p_225533_6_);
    }


    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new GhostBlockEntity();
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Override
    public BlockRenderType getRenderShape(BlockState p_49232_) {
        return BlockRenderType.MODEL;
    }

    @Override
    public boolean canBeReplaced(BlockState p_196253_1_, BlockItemUseContext p_196253_2_) {
        return true;
    }

    @Override
    public ItemStack getCloneItemStack(IBlockReader level, BlockPos pos, BlockState state) {
        GhostBlockEntity be = ((GhostBlockEntity)level.getBlockEntity(pos));
        return !state.getShape(level,pos).isEmpty() ? be.getStockedBlock().getBlock().getCloneItemStack(level,pos,state) : null;
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader level, BlockPos pos, ISelectionContext context) {
        GhostBlockEntity be = (GhostBlockEntity) level.getBlockEntity(pos);
        return be.getStockedBlock().is(Blocks.AIR) ? VoxelShapes.empty() : be.getStockedBlock().getBlock().getShape(state,level,pos,context);
    }



}