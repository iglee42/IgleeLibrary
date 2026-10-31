package fr.iglee42.igleelib.blockentities.storage.slot;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.inventory.Slot;
import net.neoforged.neoforge.common.util.INBTSerializable;
import net.minecraft.nbt.CompoundTag;

/**
 * A single item slot of an {@link fr.iglee42.igleelib.blockentities.storage.impl.ItemStorageModule}.
 * Mods can implement it to provide slots with their own rules (output-only, filtered, custom limit...).
 */
public interface InventorySlot extends INBTSerializable<CompoundTag> {

    ItemStack getStack();

    void setStack(ItemStack stack);

    /** Creates a {@link Slot} bound to this inventory slot, ready to be added to a menu with {@code addSlot}. */
    Slot createContainerSlot();

    /** Max count this slot accepts for the given stack. */
    default int getLimit(ItemStack stack) {
        return stack.getMaxStackSize();
    }

    /** Whether the stack is allowed in this slot. */
    default boolean isValid(ItemStack stack) {
        return true;
    }

    /** Whether items can be inserted into this slot (by sided handlers and by players through the container slot). */
    default boolean canInsert() {
        return true;
    }

    /** Whether items can be extracted from this slot by sided handlers. */
    default boolean canExtract() {
        return true;
    }

    /** Called by the owning module so the slot can signal content changes. */
    void setChangeListener(Runnable listener);

    /** Inserts a stack and returns the remainder. */
    default ItemStack insert(ItemStack stack, boolean simulate) {
        if (stack.isEmpty() || !isValid(stack)) return stack;
        ItemStack existing = getStack();
        int limit = Math.min(getLimit(stack), stack.getMaxStackSize());
        if (!existing.isEmpty()) {
            if (!ItemStack.isSameItemSameComponents(stack, existing)) return stack;
            limit -= existing.getCount();
        }
        if (limit <= 0) return stack;
        int moved = Math.min(limit, stack.getCount());
        if (!simulate) setStack(stack.copyWithCount(existing.getCount() + moved));
        return moved >= stack.getCount() ? ItemStack.EMPTY : stack.copyWithCount(stack.getCount() - moved);
    }

    /** Extracts up to {@code amount} items. */
    default ItemStack extract(int amount, boolean simulate) {
        ItemStack existing = getStack();
        if (amount <= 0 || existing.isEmpty()) return ItemStack.EMPTY;
        int toExtract = Math.min(amount, existing.getMaxStackSize());
        ItemStack result = existing.copyWithCount(Math.min(toExtract, existing.getCount()));
        if (!simulate) {
            setStack(existing.getCount() <= toExtract ? ItemStack.EMPTY : existing.copyWithCount(existing.getCount() - toExtract));
        }
        return result;
    }

    InventorySlot copy();
}
