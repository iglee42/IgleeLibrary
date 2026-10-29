package fr.iglee42.igleelib.blockentities.storage;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.capabilities.BlockCapability;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.common.util.INBTSerializable;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

/**
 * Holds the modular storages of a block entity. Each module is registered under a unique id.
 */
public class StorageManager implements INBTSerializable<CompoundTag> {

    private final Map<String, StorageModule<?>> modules = new LinkedHashMap<>();
    private final Runnable changeListener;

    public StorageManager(Runnable changeListener) {
        this.changeListener = changeListener;
    }

    public <M extends StorageModule<?>> M add(String id, M module) {
        if (modules.putIfAbsent(id, module) != null)
            throw new IllegalArgumentException("Storage module '" + id + "' is already registered");
        module.setChangeListener(changeListener);
        return module;
    }

    public Optional<StorageModule<?>> get(String id) {
        return Optional.ofNullable(modules.get(id));
    }

    public <M extends StorageModule<?>> M get(String id, Class<M> type) {
        StorageModule<?> module = modules.get(id);
        if (module == null || !type.isInstance(module))
            throw new IllegalArgumentException("No storage module '" + id + "' of type " + type.getSimpleName());
        return type.cast(module);
    }

    public Map<String, StorageModule<?>> modules() {
        return java.util.Collections.unmodifiableMap(modules);
    }

    /** First module exposing the capability that is accessible from the given side, or null. */
    @SuppressWarnings("unchecked")
    public <H> @Nullable H getHandler(BlockCapability<H, @Nullable Direction> capability, @Nullable Direction side) {
        for (StorageModule<?> module : modules.values()) {
            if (module.capability().equals(capability) && module.isAccessible(side))
                return ((StorageModule<H>) module).getHandler(side);
        }
        return null;
    }

    public void onDestroyed(Level level, BlockPos pos) {
        modules.values().forEach(m -> m.onDestroyed(level, pos));
    }

    @Override
    public CompoundTag serializeNBT(HolderLookup.Provider registries) {
        CompoundTag tag = new CompoundTag();
        modules.forEach((id, module) -> tag.put(id, module.serializeNBT(registries)));
        return tag;
    }

    @Override
    public void deserializeNBT(HolderLookup.Provider registries, CompoundTag tag) {
        modules.forEach((id, module) -> {
            if (tag.contains(id)) module.deserializeNBT(registries, tag.getCompound(id));
        });
    }

    /**
     * Registers the given capabilities for a block entity type, backed by its {@link StorageManager}.
     * Works for any capability, including ones added by other mods (gas, ...).
     */
    @SafeVarargs
    public static <BE extends BlockEntity> void register(RegisterCapabilitiesEvent event, BlockEntityType<BE> type,
                                                         Function<BE, StorageManager> manager,
                                                         BlockCapability<?, @Nullable Direction>... capabilities) {
        for (BlockCapability<?, @Nullable Direction> capability : capabilities)
            registerOne(event, type, manager, capability);
    }

    /** Registers item, fluid and energy capabilities. */
    public static <BE extends BlockEntity> void registerDefaults(RegisterCapabilitiesEvent event, BlockEntityType<BE> type,
                                                                 Function<BE, StorageManager> manager) {
        register(event, type, manager, Capabilities.ItemHandler.BLOCK, Capabilities.FluidHandler.BLOCK, Capabilities.EnergyStorage.BLOCK);
    }

    private static <BE extends BlockEntity, H> void registerOne(RegisterCapabilitiesEvent event, BlockEntityType<BE> type,
                                                                Function<BE, StorageManager> manager,
                                                                BlockCapability<H, @Nullable Direction> capability) {
        event.registerBlockEntity(capability, type, (be, side) -> manager.apply(be).getHandler(capability, side));
    }
}