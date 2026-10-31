package fr.iglee42.igleelib.blockentities.storage;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;

import java.util.EnumMap;
import java.util.Map;

/**
 * Per-side IO configuration. Input and output are configured independently for every side,
 * so a module can accept input on one side, output on another, or both/none.
 * Auto input/output flags are not implemented with the current system, but can be used by mods to implement automatic transfer.
 */
public class IOConfig {

    private final Map<Direction, IOMode> modes = new EnumMap<>(Direction.class);
    private IOMode defaultMode = IOMode.BOTH;
    private boolean autoInput = false;
    private boolean autoOutput = false;

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

    public IOConfig setAutoInput(boolean enabled) {
        autoInput = enabled;
        return this;
    }

    public IOConfig setAutoOutput(boolean enabled) {
        autoOutput = enabled;
        return this;
    }

    public IOConfig toggleAutoInput() {
        autoInput = !autoInput;
        return this;
    }

    public IOConfig toggleAutoOutput() {
        autoOutput = !autoOutput;
        return this;
    }

    public boolean isAutoInput() {
        return autoInput;
    }

    public boolean isAutoOutput() {
        return autoOutput;
    }

    public void save(CompoundTag tag) {
        tag.putString("default", defaultMode.name());
        for (var entry : modes.entrySet()) tag.putString(entry.getKey().getSerializedName(), entry.getValue().name());
        tag.putBoolean("auto_input", autoInput);
        tag.putBoolean("auto_output", autoOutput);
    }

    public void load(CompoundTag tag) {
        modes.clear();
        if (tag.contains("default")) defaultMode = parse(tag.getString("default"), IOMode.BOTH);
        for (Direction dir : Direction.values()) {
            if (tag.contains(dir.getSerializedName()))
                modes.put(dir, parse(tag.getString(dir.getSerializedName()), defaultMode));
        }
        if (tag.contains("auto_input")) autoInput = tag.getBoolean("auto_input");
        if (tag.contains("auto_output")) autoOutput = tag.getBoolean("auto_output");
    }

    private static IOMode parse(String name, IOMode fallback) {
        try {
            return IOMode.valueOf(name);
        } catch (IllegalArgumentException e) {
            return fallback;
        }
    }
}
