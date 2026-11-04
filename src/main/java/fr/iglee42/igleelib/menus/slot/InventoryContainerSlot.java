package fr.iglee42.igleelib.menus.slot;

import fr.iglee42.igleelib.blockentities.storage.AutomationType;
import fr.iglee42.igleelib.blockentities.storage.slot.InventorySlot;
import fr.iglee42.igleelib.menus.slot.api.InsertableSlot;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

import org.jetbrains.annotations.NotNull;

/** Menu {@link Slot} that reads and writes directly into an {@link InventorySlot}. */
public class InventoryContainerSlot extends Slot implements InsertableSlot {

    private static final SimpleContainer UNUSED = new SimpleContainer(1);

    private final InventorySlot slot;

    public InventoryContainerSlot(InventorySlot slot, int x, int y) {
        super(UNUSED, 0, x, y);
        this.slot = slot;
    }

    public InventorySlot inventorySlot() {
        return slot;
    }

    @Override
    public ItemStack getItem() {
        return slot.getStack();
    }

    @Override
    public boolean hasItem() {
        return !slot.getStack().isEmpty();
    }

    @Override
    public void set(ItemStack stack) {
        slot.setStack(stack);
        setChanged();
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return slot.canInsert(AutomationType.GUI) && slot.isValid(stack);
    }

    @Override
    public boolean mayPickup(Player player) {
        return slot.canExtract(AutomationType.GUI);
    }

    @Override
    public int getMaxStackSize() {
        return slot.getLimit(slot.getStack());
    }

    @Override
    public int getMaxStackSize(ItemStack stack) {
        return Math.min(slot.getLimit(stack), stack.getMaxStackSize());
    }

    @Override
    public ItemStack remove(int amount) {
        ItemStack existing = slot.getStack();
        if (existing.isEmpty() || amount <= 0) return ItemStack.EMPTY;
        ItemStack result = existing.copyWithCount(Math.min(amount, existing.getCount()));
        slot.setStack(existing.getCount() <= amount ? ItemStack.EMPTY : existing.copyWithCount(existing.getCount() - amount));
        setChanged();
        return result;
    }

	@Override
	public @NotNull ItemStack insertItem(@NotNull ItemStack stack, boolean simulate) {
		ItemStack remainder = slot.insert(stack, simulate, AutomationType.GUI);
		if (!simulate && remainder.getCount() != stack.getCount())
			setChanged();
		return remainder;
	}
}
