package fr.iglee42.igleelib.blockentities.storage;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.capabilities.BlockCapability;
import net.neoforged.neoforge.common.util.INBTSerializable;
import org.jetbrains.annotations.Nullable;

/**
 * A storage module of a block entity (items, fluids, energy, gas...).
 * Implement this interface (or extend {@link SidedStorageModule}) to add a new resource type from any mod.
 *
 * @param <H> the handler type exposed through the capability (IItemHandler, IFluidHandler, ...)
 */
public interface StorageModule<H> extends INBTSerializable<CompoundTag> {

    /** The capability under which this module is exposed to other blocks. */
    BlockCapability<H, @Nullable Direction> capability();

    /**
     * Handler for the given side. A {@code null} side is the internal/unrestricted access.
     */
    H getHandler(@Nullable Direction side);

    /** Whether the module is reachable from the given side (used to pick the module for a capability query). */
    default boolean isAccessible(@Nullable Direction side) {
        return true;
    }

    /** Called by the {@link StorageManager} so the module can notify the block entity of changes. */
    void setChangeListener(Runnable listener);

    /** Called when the block entity is destroyed (drop items, ...). */
    default void onDestroyed(Level level, BlockPos pos) {}
}