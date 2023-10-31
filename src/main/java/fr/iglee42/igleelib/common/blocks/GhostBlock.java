package fr.iglee42.igleelib.common.blocks;

import fr.iglee42.igleelib.common.blocks.entity.GhostBlockEntity;
import fr.iglee42.igleelib.common.init.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.client.model.data.ModelProperty;
import org.jetbrains.annotations.Nullable;

/*public class GhostBlock extends BaseEntityBlock {

    public static final ModelProperty<BlockState> PS_BLOCKSTATE = new ModelProperty<>();
    public static final ModelProperty<FluidState> PS_FLUIDSTATE = new ModelProperty<>();
    public GhostBlock() {
        super(Properties.copy(Blocks.GLASS).strength(-1,36000).noOcclusion().noCollission());
    }

    public boolean propagatesSkylightDown(BlockState p_49100_, BlockGetter p_49101_, BlockPos p_49102_) {
        return true;
    }

    public float getShadeBrightness(BlockState p_49094_, BlockGetter p_49095_, BlockPos p_49096_) {
        return 1.0F;
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand p_60507_, BlockHitResult p_60508_) {
        if (level.isClientSide()) return InteractionResult.sidedSuccess(level.isClientSide());
        if (level.getBlockEntity(pos) instanceof GhostBlockEntity be){
            if (be.getStockedBlock().is(be.getStockedBlock().getFluidState().createLegacyBlock().getBlock()) && be.getStockedBlock().getFluidState().getType() != Fluids.EMPTY){
                if (player.getMainHandItem().is(be.getStockedBlock().getFluidState().getType().getBucket())){
                    level.setBlockAndUpdate(pos,be.getStockedBlock().getFluidState().createLegacyBlock());
                    if (!player.isCreative()) player.setItemInHand(InteractionHand.MAIN_HAND,new ItemStack(Items.BUCKET));
                }
            }

        }
        return super.use(state, level, pos, player, p_60507_, p_60508_);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new GhostBlockEntity(pos,state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level lvl, BlockState state, BlockEntityType<T> type) {
        return (level,blockPos,blockState,be) -> {if (type == ModBlockEntities.GHOST_BLOCK.get()) ((GhostBlockEntity)be).tick(level,blockPos,blockState);};
    }

    @Override
    public RenderShape getRenderShape(BlockState p_49232_) {
        return RenderShape.MODEL;
    }

    @Override
    public boolean canBeReplaced(BlockState state, BlockPlaceContext placeContext) {
        return true;
    }

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter level, BlockPos pos, Player player) {
        GhostBlockEntity be = ((GhostBlockEntity)level.getBlockEntity(pos));
        return !state.getShape(level,pos,CollisionContext.of(player)).isEmpty() ? be.getStockedBlock().getCloneItemStack(target,level,pos,player) : null;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
        GhostBlockEntity be = (GhostBlockEntity) getter.getBlockEntity(pos);
        return be.getStockedBlock().is(Blocks.AIR) ? Shapes.empty() : be.getStockedBlock().getShape(getter,pos,context);
    }


}
*/