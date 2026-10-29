package fr.iglee42.igleelib.blockentities.storage;

import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import org.jetbrains.annotations.Nullable;

import java.util.EnumMap;
import java.util.Map;

/**
 * Base class handling per-side IO configuration and change notification.
 *
 * @param <H> the handler type exposed through the capability
 */
public abstract class SidedStorageModule<H> implements StorageModule<H> {

    private final Map<Direction, IOMode> modes = new EnumMap<>(Direction.class);
    private final Object[] sideHandlers = new Object[Direction.values().length];
    private IOMode defaultMode = IOMode.BOTH;
    private Runnable listener = () -> {};

    /** Internal, unrestricted handler. */
    protected abstract H internalHandler();

    /** Creates a handler that checks {@link #getMode(Direction)} for the given side at call time. */
    protected abstract H createSidedHandler(Direction side);

    protected abstract void saveContent(CompoundTag tag, HolderLookup.Provider registries);

    protected abstract void loadContent(CompoundTag tag, HolderLookup.Provider registries);

    @SuppressWarnings("unchecked")
    @Override
    public H getHandler(@Nullable Direction side) {
        if (side == null) return internalHandler();
        Object handler = sideHandlers[side.ordinal()];
        if (handler == null) {
            handler = createSidedHandler(side);
            sideHandlers[side.ordinal()] = handler;
        }
        return (H) handler;
    }

    @Override
    public boolean isAccessible(@Nullable Direction side) {
        return side == null || getMode(side) != IOMode.NONE;
    }

    public IOMode getMode(Direction side) {
        return modes.getOrDefault(side, defaultMode);
    }

    public SidedStorageModule<H> setMode(Direction side, IOMode mode) {
        modes.put(side, mode);
        notifyChanged();
        return this;
    }

    public SidedStorageModule<H> setAllModes(IOMode mode) {
        defaultMode = mode;
        modes.clear();
        notifyChanged();
        return this;
    }

    @Override
    public void setChangeListener(Runnable listener) {
        this.listener = listener;
    }

    protected void notifyChanged() {
        listener.run();
    }

    @Override
    public CompoundTag serializeNBT(HolderLookup.Provider registries) {
        CompoundTag tag = new CompoundTag();
        saveContent(tag, registries);
        CompoundTag io = new CompoundTag();
        io.putString("default", defaultMode.name());
        for (var entry : modes.entrySet()) io.putString(entry.getKey().getSerializedName(), entry.getValue().name());
        tag.put("io", io);
        return tag;
    }

    @Override
    public void deserializeNBT(HolderLookup.Provider registries, CompoundTag tag) {
        loadContent(tag, registries);
        modes.clear();
        if (tag.contains("io")) {
            CompoundTag io = tag.getCompound("io");
            if (io.contains("default")) defaultMode = parse(io.getString("default"), IOMode.BOTH);
            for (Direction dir : Direction.values()) {
                if (io.contains(dir.getSerializedName()))
                    modes.put(dir, parse(io.getString(dir.getSerializedName()), defaultMode));
            }
        }
    }

    private static IOMode parse(String name, IOMode fallback) {
        try {
            return IOMode.valueOf(name);
        } catch (IllegalArgumentException e) {
            return fallback;
        }
    }
}