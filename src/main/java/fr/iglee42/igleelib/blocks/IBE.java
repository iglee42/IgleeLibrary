package fr.iglee42.igleelib.blocks;

import fr.iglee42.igleelib.blockentities.ExtendedBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.OptionalInt;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

public interface IBE<T extends BlockEntity> extends EntityBlock {

    @NotNull BlockEntityType<T> type();

    @Override
    default @Nullable T newBlockEntity(BlockPos pos, BlockState state){
        return type().create(pos, state);
    }

    @Override
    @Nullable
    default <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level unusedLvl, BlockState unusedState, BlockEntityType<T> type) {
        return type().equals(type) ? (level,pos,state,be)->{
            if (be instanceof ExtendedBlockEntity auBe) auBe.tick(level,pos,state);
        } : null;
    }

    default T getBlockEntity(BlockGetter level, BlockPos pos){
        return getBEOptional(level,pos).orElse(null);
    }

    default Optional<T> getBEOptional(BlockGetter level, BlockPos pos){
        return level.getBlockEntity(pos,type());
    }

    default void withBlockEntityDo(BlockGetter level, BlockPos pos, Consumer<T> action){
        getBEOptional(level,pos).ifPresent(action);
    }

    default void onPlace(Level level, BlockPos pos, LivingEntity entity){
        if (!level.isClientSide && entity instanceof Player player)
            withBlockEntityDo(level,pos,be->{
                if (be instanceof ExtendedBlockEntity sbe) sbe.setPlacedBy(player);
            });
    }

    static void onRemove(BlockState blockState, Level level, BlockPos pos, BlockState newState) {
        if (!blockState.hasBlockEntity())
            return;
        if (blockState.is(newState.getBlock()) && newState.hasBlockEntity())
            return;
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof ExtendedBlockEntity sbe)
            sbe.destroy();
        level.removeBlockEntity(pos);
    }

    default InteractionResult openMenu(Level level, BlockPos pos, Player player){
        if (level.isClientSide) return InteractionResult.sidedSuccess(true);
        if (player.isCrouching()) return InteractionResult.PASS;
        AtomicReference<OptionalInt> result = new AtomicReference<>(OptionalInt.empty());
        withBlockEntityDo(level,pos,be->{
            if (be instanceof MenuProvider provider)
                result.set(player.openMenu(provider, pos));
        });
        return result.get().isPresent() ? InteractionResult.SUCCESS : InteractionResult.PASS;
    }

}