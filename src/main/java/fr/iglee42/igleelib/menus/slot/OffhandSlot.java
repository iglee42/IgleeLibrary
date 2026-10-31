package fr.iglee42.igleelib.menus.slot;

import net.minecraft.world.Container;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * Class <a href="https://github.com/mekanism/Mekanism/blob/bcd7a8bf594cff9614eb12238fe3776f19da24d9/src/main/java/mekanism/common/inventory/container/slot/OffhandSlot.java">OffhandSlot</a> from Mekanism, used to handle offhand slot in a player's inventory.
 * It extends BasicInsertableSlot and provides functionality for equipping and unequipping items in the offhand slot.
 */
public class OffhandSlot extends BasicInsertableSlot {

    private final Player owner;

    public OffhandSlot(Container inventory, int index, int x, int y, Player owner) {
        super(inventory, index, x, y);
        this.owner = owner;
        setBackground(InventoryMenu.BLOCK_ATLAS, InventoryMenu.EMPTY_ARMOR_SLOT_SHIELD);
    }

    @Override
    public void setByPlayer(@NotNull ItemStack newStack, @NotNull ItemStack oldStack) {
        this.owner.onEquipItem(EquipmentSlot.OFFHAND, oldStack, newStack);
        super.setByPlayer(newStack, oldStack);
    }
}
