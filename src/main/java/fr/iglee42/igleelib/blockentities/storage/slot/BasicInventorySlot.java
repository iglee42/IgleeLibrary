package fr.iglee42.igleelib.blockentities.storage.slot;

import fr.iglee42.igleelib.blockentities.storage.AutomationType;
import fr.iglee42.igleelib.menus.slot.InventoryContainerSlot;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.function.Predicate;

/** Default {@link InventorySlot}: configurable limit, validator and insert/extract permissions. */
public class BasicInventorySlot implements InventorySlot {

    private final int x,y;
    private ItemStack stack = ItemStack.EMPTY;
    private Runnable listener = () -> {};
    private int limit = Item.DEFAULT_MAX_STACK_SIZE;
    private Predicate<ItemStack> validator = s -> true;
    private Predicate<AutomationType> canInsert = t -> true;
    private Predicate<AutomationType> canExtract = t -> true;
    private SlotType type = SlotType.NORMAL;

    private BasicInventorySlot(BasicInventorySlot oldSlot){
        this.x = oldSlot.x;
        this.y = oldSlot.y;
        this.stack = oldSlot.stack.copy();
        this.limit = oldSlot.limit;
        this.validator = oldSlot.validator;
        this.canInsert = oldSlot.canInsert;
        this.canExtract = oldSlot.canExtract;
        this.type = oldSlot.type;
    }

    private BasicInventorySlot(Builder builder) {
        this.x = builder.x;
        this.y = builder.y;
        this.limit = builder.limit;
        this.validator = builder.validator;
        this.canInsert = builder.canInsert;
        this.canExtract = builder.canExtract;
        this.type = builder.type;
    }

    public static Builder builder(int x, int y) {
        return new Builder(x, y);
    }

	/** A preconfigured builder for input slots. */
	public static Builder input(int x, int y){
		return builder(x,y).type(SlotType.INPUT).canInsert(true).canExtract(false);
	}

	/** A preconfigured builder for output slots. */
	public static Builder output(int x, int y){
		return builder(x,y).type(SlotType.OUTPUT).canInsert(false).canExtract(true);
	}

	@Override
    public ItemStack getStack() {
        return stack;
    }

    @Override
    public void setStack(ItemStack stack) {
        this.stack = stack;
        listener.run();
    }

    @Override
    public Slot createContainerSlot() {
        return new InventoryContainerSlot(this, x, y);
    }

    @Override
    public int getLimit(ItemStack stack) {
        return limit;
    }

    @Override
    public boolean isValid(ItemStack stack) {
        return validator.test(stack);
    }

    @Override
    public boolean canInsert(AutomationType type) {
        return canInsert.test(type);
    }

    @Override
    public boolean canExtract(AutomationType type) {
        return canExtract.test(type);
    }

    @Override
    public void setChangeListener(Runnable listener) {
        this.listener = listener;
    }

    @Override
    public CompoundTag serializeNBT(HolderLookup.Provider registries) {
        return stack.isEmpty() ? new CompoundTag() : (CompoundTag) stack.save(registries);
    }

    @Override
    public void deserializeNBT(HolderLookup.Provider registries, CompoundTag tag) {
        stack = ItemStack.parseOptional(registries, tag);
    }

    @Override
    public InventorySlot copy() {
        return new BasicInventorySlot(this);
    }

    @Override
    public SlotType getType() {
        return type;
    }

    public static class Builder {
        private final int x, y;
        private int limit = Item.DEFAULT_MAX_STACK_SIZE;
        private Predicate<ItemStack> validator = s -> true;
        private Predicate<AutomationType> canInsert = t -> true;
        private Predicate<AutomationType> canExtract = t -> true;
        private SlotType type = SlotType.NORMAL;

        private Builder(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public Builder limit(int limit) {
            this.limit = limit;
            return this;
        }

        public Builder validator(Predicate<ItemStack> validator) {
            this.validator = validator;
            return this;
        }

        public Builder canInsert(Predicate<AutomationType> canInsert) {
            this.canInsert = canInsert;
            return this;
        }

        public Builder canExtract(Predicate<AutomationType> canExtract) {
            this.canExtract = canExtract;
            return this;
        }

		/** Shortcut: when false, only {@link AutomationType#INTERNAL} may insert. */
		public Builder canInsert(boolean canInsert) {
			return canInsert(t -> t == AutomationType.INTERNAL || canInsert);
		}

		/** Shortcut: when false, only {@link AutomationType#EXTERNAL} is denied. */
		public Builder canExtract(boolean canExtract) {
			return canExtract(t -> t != AutomationType.EXTERNAL || canExtract);
		}

        public Builder type(SlotType type) {
            this.type = type;
            return this;
        }

        public BasicInventorySlot build() {
            return new BasicInventorySlot(this);
        }
    }
}
