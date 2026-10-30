package fr.iglee42.igleelib.blockentities.storage;

import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import org.jetbrains.annotations.Nullable;

/**
 * Base class handling per-side IO configuration and change notification.
 *
 * @param <H> the handler type exposed through the capability
 */
public abstract class SidedStorageModule<H> implements StorageModule<H> {

    private final IOConfig io = new IOConfig();
    private final Object[] sideHandlers = new Object[Direction.values().length];
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
        return io.get(side);
    }

    /** The module-wide IO configuration. Prefer the setters below so listeners get notified. */
    public IOConfig io() {
        return io;
    }

    public SidedStorageModule<H> setMode(Direction side, IOMode mode) {
        io.set(side, mode);
        notifyChanged();
        return this;
    }

    public SidedStorageModule<H> setInput(Direction side, boolean enabled) {
        io.setInput(side, enabled);
        notifyChanged();
        return this;
    }

    public SidedStorageModule<H> setOutput(Direction side, boolean enabled) {
        io.setOutput(side, enabled);
        notifyChanged();
        return this;
    }

    public SidedStorageModule<H> setAllModes(IOMode mode) {
        io.setAll(mode);
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
        CompoundTag ioTag = new CompoundTag();
        io.save(ioTag);
        tag.put("io", ioTag);
        return tag;
    }

    @Override
    public void deserializeNBT(HolderLookup.Provider registries, CompoundTag tag) {
        loadContent(tag, registries);
        if (tag.contains("io")) io.load(tag.getCompound("io"));
    }
}
