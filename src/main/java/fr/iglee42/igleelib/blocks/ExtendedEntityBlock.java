package fr.iglee42.igleelib.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class ExtendedEntityBlock<T extends BlockEntity> extends Block implements IBE<T> {

    private final Supplier<BlockEntityType<T>> type;

    public ExtendedEntityBlock(Properties props, Supplier<BlockEntityType<T>> type) {
        super(props);
        this.type = type;
    }

    @Override
    public @NotNull BlockEntityType<T> type() {
        return type.get();
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity entity, ItemStack stack) {
        onPlace(level, pos, entity);
        super.setPlacedBy(level, pos, state, entity, stack);
    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean moved) {
        IBE.onRemove(state, level, pos, newState);
        super.onRemove(state, level, pos, newState, moved);
    }
}
