package fr.iglee42.techresourcesbase.blocks;

import fr.iglee42.techresourcesbase.TechResourcesBase;
import fr.iglee42.techresourcesbase.blocks.properties.ConnectedBlockType;
import fr.iglee42.techresourcesbase.init.ModBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.client.Minecraft;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class PillarBlock extends Block implements IWaterLoggable {

    public static final EnumProperty<ConnectedBlockType> TYPE = EnumProperty.create("type", ConnectedBlockType.class);
    public static final EnumProperty<Direction.Axis> AXIS = BlockStateProperties.AXIS;


    public PillarBlock(Properties properties) {
        super(properties.noOcclusion());
        this.registerDefaultState(this.defaultBlockState().setValue(AXIS, Direction.Axis.Y).setValue(TYPE, ConnectedBlockType.SINGLE));
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return super.getStateForPlacement(context).setValue(TYPE, ConnectedBlockType.SINGLE).setValue(AXIS, context.getClickedFace().getAxis());
    }
    

    public ConnectedBlockType tryConnect(BlockState state, IWorld world, BlockPos pos) {
        Direction.Axis axis = state.getValue(AXIS);

        BlockState stateDown = world.getBlockState(pos.offset(Direction.fromAxisAndDirection(axis, Direction.AxisDirection.NEGATIVE).getNormal()));
        BlockState stateUp = world.getBlockState(pos.offset(Direction.fromAxisAndDirection(axis, Direction.AxisDirection.POSITIVE).getNormal()));

        boolean axisUpEqual = stateUp.getBlock() instanceof PillarBlock && stateUp.getValue(AXIS) == axis;
        boolean axisDownEqual = stateDown.getBlock() instanceof PillarBlock && stateDown.getValue(AXIS) == axis;

        return ConnectedBlockType.getTypeForConnections(axisUpEqual, axisDownEqual, axis);
    }

    @Override
    public void neighborChanged(BlockState p_220069_1_, World p_220069_2_, BlockPos p_220069_3_, Block p_220069_4_, BlockPos p_220069_5_, boolean p_220069_6_) {
        p_220069_1_.setValue(TYPE,tryConnect(p_220069_1_,p_220069_2_,p_220069_3_));
        super.neighborChanged(p_220069_1_, p_220069_2_, p_220069_3_, p_220069_4_, p_220069_5_, p_220069_6_);
    }

    @Override
    public void onPlace(BlockState p_220082_1_, World p_220082_2_, BlockPos p_220082_3_, BlockState p_220082_4_, boolean p_220082_5_) {
        p_220082_1_.setValue(TYPE,tryConnect(p_220082_1_,p_220082_2_,p_220082_3_));
        super.onPlace(p_220082_1_, p_220082_2_, p_220082_3_, p_220082_4_, p_220082_5_);
    }



    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {
        switch(rotation) {
            case COUNTERCLOCKWISE_90:
            case CLOCKWISE_90:
                switch(state.getValue(AXIS)) {
                    case X:
                        return state.setValue(AXIS, Direction.Axis.Z);
                    case Z:
                        return state.setValue(AXIS, Direction.Axis.X);
                    default:
                        return state;
                }
            default:
                return state;
        }
    }


    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(TYPE, AXIS);
    }


}
