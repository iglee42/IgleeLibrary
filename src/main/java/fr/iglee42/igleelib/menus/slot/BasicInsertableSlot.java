package fr.iglee42.igleelib.menus.slot;

import fr.iglee42.igleelib.menus.slot.api.InsertableSlot;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

import org.jetbrains.annotations.NotNull;

public class BasicInsertableSlot extends Slot implements InsertableSlot {
	public BasicInsertableSlot(Container container, int index, int x, int y) {
		super(container, index, x, y);
	}

	@NotNull
	@Override
	public ItemStack insertItem(@NotNull ItemStack stack, boolean simulate) {
		if (stack.isEmpty() || !mayPlace(stack)) {
			//"Fail quick" if the given stack is empty or we are not valid for the slot
			return stack;
		}
		ItemStack current = getItem();
		int needed = getMaxStackSize(stack) - current.getCount();
		if (needed <= 0) {
			//Fail if we are a full slot
			return stack;
		}
		if (current.isEmpty() || ItemStack.isSameItemSameComponents(current, stack)) {
			int toAdd = Math.min(stack.getCount(), needed);
			if (!simulate) {
				//If we want to actually insert the item, then update the current item
				//Set the stack to our new stack (we have no simple way to increment the stack size) so we have to set it instead of being able to just grow it
				set(stack.copyWithCount(current.getCount() + toAdd));
			}
			return stack.copyWithCount(stack.getCount() - toAdd);
		}
		//If we didn't accept this item, then just return the given stack
		return stack;
	}
}
