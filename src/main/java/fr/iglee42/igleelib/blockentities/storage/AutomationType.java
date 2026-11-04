package fr.iglee42.igleelib.blockentities.storage;

/** Origin of an insertion or extraction on a slot or tank. */
public enum AutomationType {
    /** From outside the block: pipes, hoppers, any capability access. */
    EXTERNAL,
    /** From a menu: player clicking a slot or quick-moving a stack. */
    GUI,
    /** From the block entity's own code. */
    INTERNAL;

    /** Whether this is a restricted origin (not the block entity itself). */
    public boolean isExternal() {
        return this == EXTERNAL;
    }
}
