package fr.iglee42.igleelib.menus.slot.api;

import net.minecraft.world.item.ItemStack;

import org.jetbrains.annotations.NotNull;

public interface InsertableSlot {

	/** Attempts to insert the given stack into this slot. Returns the remainder.
	 * Works like the {@link fr.iglee42.igleelib.blockentities.storage.slot.InventorySlot#insert(ItemStack, boolean)}
	 */
	@NotNull
	ItemStack insertItem(@NotNull ItemStack stack, boolean simulate);

	/**
	 * Used for determining if this slot can merge with the given stack when the stack is double-clicked.
	 */
	default boolean canMergeWith(@NotNull ItemStack stack) {
		return true;
	}

}
