package fr.iglee42.igleelib.blockentities.storage.slot;

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
    private boolean canInsert = true;
    private boolean canExtract = true;

    private BasicInventorySlot(BasicInventorySlot oldSlot){
        this.x = oldSlot.x;
        this.y = oldSlot.y;
        this.stack = oldSlot.stack.copy();
        this.limit = oldSlot.limit;
        this.validator = oldSlot.validator;
        this.canInsert = oldSlot.canInsert;
        this.canExtract = oldSlot.canExtract;
    }

    public BasicInventorySlot(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public BasicInventorySlot limit(int limit) {
        this.limit = limit;
        return this;
    }

    public BasicInventorySlot validator(Predicate<ItemStack> validator) {
        this.validator = validator;
        return this;
    }

    public BasicInventorySlot canInsert(boolean canInsert) {
        this.canInsert = canInsert;
        return this;
    }

    public BasicInventorySlot canExtract(boolean canExtract) {
        this.canExtract = canExtract;
        return this;
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
    public boolean canInsert() {
        return canInsert;
    }

    @Override
    public boolean canExtract() {
        return canExtract;
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
}
