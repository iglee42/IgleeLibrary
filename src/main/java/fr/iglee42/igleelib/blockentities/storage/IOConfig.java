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
    private boolean autoInput = false;
    private boolean autoOutput = false;

    public IOMode get(Direction side) {
        return modes.getOrDefault(side, IOMode.BOTH);
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
        byte[] directions = new byte[Direction.values().length];
		for (int i = 0; i < directions.length; i++){
			Direction dir = Direction.values()[i];
			directions[i] = (byte) get(dir).ordinal();
		}
		tag.putByteArray("modes", directions);
        tag.putBoolean("auto_input", autoInput);
        tag.putBoolean("auto_output", autoOutput);
    }

    public void load(CompoundTag tag) {
        modes.clear();
        if (tag.contains("modes")) {
			byte[] directions = tag.getByteArray("modes");
			for (int i = 0; i < directions.length; i++) {
				Direction dir = Direction.values()[i];
				IOMode mode = parse(directions[i]);
				modes.put(dir, mode);
			}
		}
        if (tag.contains("auto_input")) autoInput = tag.getBoolean("auto_input");
        if (tag.contains("auto_output")) autoOutput = tag.getBoolean("auto_output");
    }

    private static IOMode parse(byte id) {
        try {
            return IOMode.values()[id];
        } catch (Exception e) {
            return IOMode.BOTH;
        }
    }
}
