package fr.iglee42.igleelib.blockentities.storage;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Per-side IO configuration. Input and output are configured independently for every side,
 * so a module can accept input on one side, output on another, or both/none.
 * Auto input/output flags are not implemented with the current system, but can be used by mods to implement automatic transfer.
 */
public class IOConfig {

    private final Map<RelativeSide, IOMode> modes = new EnumMap<>(RelativeSide.class);
    private Supplier<Direction> facingSupplier = () -> Direction.NORTH;
    private boolean autoInput = false;
    private boolean autoOutput = false;

    public IOConfig setFacingSupplier(Supplier<Direction> facingSupplier) {
        this.facingSupplier = facingSupplier != null ? facingSupplier : () -> Direction.NORTH;
        return this;
    }

    public IOMode get(RelativeSide side) {
        return modes.getOrDefault(side, IOMode.BOTH);
    }

    public IOMode get(Direction side) {
        return get(toRelative(side));
    }

    public boolean canInput(RelativeSide side) {
        return get(side).canInput();
    }

    public boolean canInput(Direction side) {
        return canInput(toRelative(side));
    }

    public boolean canOutput(RelativeSide side) {
        return get(side).canOutput();
    }

    public boolean canOutput(Direction side) {
        return canOutput(toRelative(side));
    }

    public IOConfig set(RelativeSide side, IOMode mode) {
        modes.put(side, mode);
        return this;
    }

    public IOConfig set(Direction side, IOMode mode) {
        return set(toRelative(side), mode);
    }

    public IOConfig setInput(RelativeSide side, boolean enabled) {
        return set(side, IOMode.of(enabled, canOutput(side)));
    }

    public IOConfig setInput(Direction side, boolean enabled) {
        return setInput(toRelative(side), enabled);
    }

    public IOConfig setOutput(RelativeSide side, boolean enabled) {
        return set(side, IOMode.of(canInput(side), enabled));
    }

    public IOConfig setOutput(Direction side, boolean enabled) {
        return setOutput(toRelative(side), enabled);
    }

    /** Sets the same mode on every side. */
    public IOConfig setAll(IOMode mode) {
        modes.clear();
        for (RelativeSide side : RelativeSide.values()) {
            modes.put(side, mode);
        }
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
        byte[] sides = new byte[RelativeSide.values().length];
        for (int i = 0; i < sides.length; i++) {
            RelativeSide side = RelativeSide.values()[i];
            sides[i] = (byte) get(side).ordinal();
        }
        tag.putByteArray("modes", sides);
        tag.putBoolean("auto_input", autoInput);
        tag.putBoolean("auto_output", autoOutput);
    }

    public void load(CompoundTag tag) {
        modes.clear();
        if (tag.contains("modes")) {
            byte[] sides = tag.getByteArray("modes");
            int max = Math.min(sides.length, RelativeSide.values().length);
            for (int i = 0; i < max; i++) {
                RelativeSide side = RelativeSide.values()[i];
                IOMode mode = parse(sides[i]);
                modes.put(side, mode);
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

    private RelativeSide toRelative(Direction side) {
        Direction facing;
        try {
            facing = facingSupplier.get();
        } catch (Exception e) {
            facing = Direction.NORTH;
        }
        if (facing == null) facing = Direction.NORTH;
        return RelativeSide.fromDirections(facing, side);
    }
}
