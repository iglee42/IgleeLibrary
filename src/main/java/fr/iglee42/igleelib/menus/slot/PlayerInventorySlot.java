package fr.iglee42.igleelib.menus.slot;

import net.minecraft.world.Container;

/**
 * Class <a href="https://github.com/mekanism/Mekanism/blob/bcd7a8bf594cff9614eb12238fe3776f19da24d9/src/main/java/mekanism/common/inventory/container/slot/MainInventorySlot.java">MainInventorySlot</a> from Mekanism, used to handle slots in a player's inventory.
 * It extends BasicInsertableSlot and is only used as marker for slots in a player's inventory.
 */
public class PlayerInventorySlot extends BasicInsertableSlot {

    public PlayerInventorySlot(Container inventory, int index, int x, int y) {
        super(inventory, index, x, y);
    }
}
