package fr.iglee42.igleelib.blockentities.storage;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;

import java.util.EnumMap;
import java.util.Map;

/**
 * Per-side IO configuration. Input and output are configured independently for every side,
 * so a module can accept input on one side, output on another, or both/none.
 */
public class IOConfig {

    private final Map<Direction, IOMode> modes = new EnumMap<>(Direction.class);
    private IOMode defaultMode = IOMode.BOTH;

    public IOMode get(Direction side) {
        return modes.getOrDefault(side, defaultMode);
    }

    public boolean canInput(Direction side) {
        return get(side).canInput();
    }

    public boolean canOutput(Direction side) {
        return get(side).canOutput();
    }

    public IOConfig set(Direction side, IOMode mode) {
        modes.put(side, mode);
        return this;
    }

    public IOConfig setInput(Direction side, boolean enabled) {
        return set(side, IOMode.of(enabled, canOutput(side)));
    }

    public IOConfig setOutput(Direction side, boolean enabled) {
        return set(side, IOMode.of(canInput(side), enabled));
    }

    /** Sets the same mode on every side. */
    public IOConfig setAll(IOMode mode) {
        defaultMode = mode;
        modes.clear();
        return this;
    }

    public void save(CompoundTag tag) {
        tag.putString("default", defaultMode.name());
        for (var entry : modes.entrySet()) tag.putString(entry.getKey().getSerializedName(), entry.getValue().name());
    }

    public void load(CompoundTag tag) {
        modes.clear();
        if (tag.contains("default")) defaultMode = parse(tag.getString("default"), IOMode.BOTH);
        for (Direction dir : Direction.values()) {
            if (tag.contains(dir.getSerializedName()))
                modes.put(dir, parse(tag.getString(dir.getSerializedName()), defaultMode));
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
