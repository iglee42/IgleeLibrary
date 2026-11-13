package fr.iglee42.igleelib.blockentities.storage.slot;

import java.util.Objects;

/**
 * Semantic marker for a slot or tank.
 * Only used as metadata for automation, UI, or auto-in/auto-out logic.
 */
public record SlotType(String name) {
    public static final SlotType NORMAL = new SlotType("normal");
    public static final SlotType INPUT = new SlotType("input");
    public static final SlotType OUTPUT = new SlotType("output");
    public static final SlotType ENERGY = new SlotType("energy");

    public SlotType {
        name = Objects.requireNonNull(name, "name");
    }

    public boolean isNormal() {
        return this == NORMAL;
    }

    public boolean isInput() {
        return this == INPUT;
    }

    public boolean isOutput() {
        return this == OUTPUT;
    }

    public boolean isEnergy() {
        return this == ENERGY;
    }
}
