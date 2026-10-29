package fr.iglee42.igleelib.blockentities.storage.impl;

import fr.iglee42.igleelib.blockentities.storage.SidedStorageModule;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.capabilities.BlockCapability;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.IItemHandlerModifiable;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.BiPredicate;

public class ItemStorageModule extends SidedStorageModule<IItemHandler> {

    private final Inventory inventory;

    public ItemStorageModule(int slots) {
        this(slots, (slot, stack) -> true);
    }

    public ItemStorageModule(int slots, BiPredicate<Integer, ItemStack> validator) {
        this.inventory = new Inventory(slots, validator);
    }

    /** Internal, unrestricted inventory. */
    public ItemStackHandler inventory() {
        return inventory;
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
        tag.put("items", inventory.serializeNBT(registries));
    }

    @Override
    protected void loadContent(CompoundTag tag, HolderLookup.Provider registries) {
        if (tag.contains("items")) inventory.deserializeNBT(registries, tag.getCompound("items"));
    }

    @Override
    public void onDestroyed(Level level, BlockPos pos) {
        for (int i = 0; i < inventory.getSlots(); i++) {
            ItemStack stack = inventory.getStackInSlot(i);
            if (!stack.isEmpty()) Block.popResource(level, pos, stack);
            inventory.setStackInSlot(i, ItemStack.EMPTY);
        }
    }

    private class Inventory extends ItemStackHandler {
        private final BiPredicate<Integer, ItemStack> validator;

        Inventory(int slots, BiPredicate<Integer, ItemStack> validator) {
            super(slots);
            this.validator = validator;
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return validator.test(slot, stack);
        }

        @Override
        protected void onContentsChanged(int slot) {
            notifyChanged();
        }
    }

    private class Sided implements IItemHandlerModifiable {
        private final Direction side;

        Sided(Direction side) {
            this.side = side;
        }

        @Override
        public void setStackInSlot(int slot, @NotNull ItemStack stack) {
            if (getMode(side).canInput()) inventory.setStackInSlot(slot, stack);
        }

        @Override
        public int getSlots() {
            return inventory.getSlots();
        }

        @Override
        public @NotNull ItemStack getStackInSlot(int slot) {
            return inventory.getStackInSlot(slot);
        }

        @Override
        public @NotNull ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
            return getMode(side).canInput() ? inventory.insertItem(slot, stack, simulate) : stack;
        }

        @Override
        public @NotNull ItemStack extractItem(int slot, int amount, boolean simulate) {
            return getMode(side).canOutput() ? inventory.extractItem(slot, amount, simulate) : ItemStack.EMPTY;
        }

        @Override
        public int getSlotLimit(int slot) {
            return inventory.getSlotLimit(slot);
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return getMode(side).canInput() && inventory.isItemValid(slot, stack);
        }
    }
}