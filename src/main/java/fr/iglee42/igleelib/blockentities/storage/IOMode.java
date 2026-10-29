package fr.iglee42.igleelib.blockentities.storage;

/** Describes what an external block can do with a storage module on a given side. */
public enum IOMode {
    NONE(false, false),
    INPUT(true, false),
    OUTPUT(false, true),
    BOTH(true, true);

    private final boolean input;
    private final boolean output;

    IOMode(boolean input, boolean output) {
        this.input = input;
        this.output = output;
    }

    public boolean canInput() {
        return input;
    }

    public boolean canOutput() {
        return output;
    }
}