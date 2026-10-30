package fr.iglee42.igleelib.blockentities.storage.impl;

import fr.iglee42.igleelib.blockentities.storage.SidedStorageModule;
import fr.iglee42.igleelib.blockentities.storage.slot.BasicInventorySlot;
import fr.iglee42.igleelib.blockentities.storage.slot.InventorySlot;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.capabilities.BlockCapability;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.IItemHandlerModifiable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.BiPredicate;

/** Item storage made of independent {@link InventorySlot}s. */
public class ItemStorageModule extends SidedStorageModule<IItemHandler> {

    private final List<InventorySlot> slots;
    private final Inventory inventory = new Inventory();

    public ItemStorageModule(List<? extends InventorySlot> slots) {
        this.slots = List.copyOf(slots);
        this.slots.forEach(s -> s.setChangeListener(this::notifyChanged));
    }

    public static Builder builder() {
        return new Builder();
    }

    /** Internal, unrestricted view of the slots. */
    public IItemHandlerModifiable inventory() {
        return inventory;
    }

    public List<InventorySlot> slots() {
        return Collections.unmodifiableList(slots);
    }

    public InventorySlot slot(int index) {
        return slots.get(index);
    }

    @Override
    public BlockCapability<IItemHandler, @Nullable Direction> capability() {
        return Capabilities.ItemHandler.BLOCK;
    }

    @Override
    protected IItemHandler internalHandler() {
        return inventory;
    }

    @Override
    protected IItemHandler createSidedHandler(Direction side) {
        return new Sided(side);
    }

    @Override
    protected void saveContent(CompoundTag tag, HolderLookup.Provider registries) {
        ListTag list = new ListTag();
        for (InventorySlot slot : slots) list.add(slot.serializeNBT(registries));
        tag.put("items", list);
    }

    @Override
    protected void loadContent(CompoundTag tag, HolderLookup.Provider registries) {
        if (!tag.contains("items", Tag.TAG_LIST)) return;
        ListTag list = tag.getList("items", Tag.TAG_COMPOUND);
        for (int i = 0; i < slots.size() && i < list.size(); i++)
            slots.get(i).deserializeNBT(registries, list.getCompound(i));
    }

    @Override
    public void onDestroyed(Level level, BlockPos pos) {
        for (InventorySlot slot : slots) {
            ItemStack stack = slot.getStack();
            if (!stack.isEmpty()) Block.popResource(level, pos, stack);
            slot.setStack(ItemStack.EMPTY);
        }
    }

    private class Inventory implements IItemHandlerModifiable {
        @Override
        public void setStackInSlot(int slot, @NotNull ItemStack stack) {
            slots.get(slot).setStack(stack);
        }

        @Override
        public int getSlots() {
            return slots.size();
        }

        @Override
        public @NotNull ItemStack getStackInSlot(int slot) {
            return slots.get(slot).getStack();
        }

        @Override
        public @NotNull ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
            return slots.get(slot).insert(stack, simulate);
        }

        @Override
        public @NotNull ItemStack extractItem(int slot, int amount, boolean simulate) {
            return slots.get(slot).extract(amount, simulate);
        }

        @Override
        public int getSlotLimit(int slot) {
            return slots.get(slot).getLimit(ItemStack.EMPTY);
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return slots.get(slot).isValid(stack);
        }
    }

    /** Side-restricted view: the side IO mode and each slot's own insert/extract rules both apply. */
    private class Sided implements IItemHandler {
        private final Direction side;

        Sided(Direction side) {
            this.side = side;
        }

        @Override
        public int getSlots() {
            return slots.size();
        }

        @Override
        public @NotNull ItemStack getStackInSlot(int slot) {
            return slots.get(slot).getStack();
        }

        @Override
        public @NotNull ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
            InventorySlot s = slots.get(slot);
            return getMode(side).canInput() && s.canInsert() ? s.insert(stack, simulate) : stack;
        }

        @Override
        public @NotNull ItemStack extractItem(int slot, int amount, boolean simulate) {
            InventorySlot s = slots.get(slot);
            return getMode(side).canOutput() && s.canExtract() ? s.extract(amount, simulate) : ItemStack.EMPTY;
        }

        @Override
        public int getSlotLimit(int slot) {
            return slots.get(slot).getLimit(ItemStack.EMPTY);
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            InventorySlot s = slots.get(slot);
            return getMode(side).canInput() && s.canInsert() && s.isValid(stack);
        }
    }

    public static class Builder {
        private final List<InventorySlot> slots = new ArrayList<>();

        public Builder addSlot(InventorySlot slot) {
            slots.add(slot);
            return this;
        }

        public Builder addBasicSlot(int x, int y) {
            slots.add(new BasicInventorySlot(x, y));
            return this;
        }

        public Builder addBasicSlot(int x, int y, int limit) {
            int index = slots.size();
            slots.add(new BasicInventorySlot(x, y).limit(limit));
            return this;
        }

        public Builder addBasicSlot(int x, int y, BiPredicate<Integer, ItemStack> validator) {
            int index = slots.size();
            slots.add(new BasicInventorySlot(x, y).validator(stack -> validator.test(index, stack)));
            return this;
        }

        public Builder addBasicSlot(int x, int y, int limit, BiPredicate<Integer, ItemStack> validator) {
            int index = slots.size();
            slots.add(new BasicInventorySlot(x, y).limit(limit).validator(stack -> validator.test(index, stack)));
            return this;
        }

        public ItemStorageModule build() {
            return new ItemStorageModule(slots);
        }
    }
}
