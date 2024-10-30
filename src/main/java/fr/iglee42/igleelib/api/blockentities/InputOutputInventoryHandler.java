package fr.iglee42.igleelib.api.blockentities;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.items.ItemStackHandler;

import javax.annotation.Nonnull;

public class InputOutputInventoryHandler extends ItemStackHandler {

    private int inputSlots;
    private int outputSlots;
    private final boolean allowOutputInsert;
    private final boolean allowInputExtract;


    public InputOutputInventoryHandler(int inputSlots, int outputSlots) {
        this(inputSlots,outputSlots,false,false);
    }

    public InputOutputInventoryHandler(int inputSlots, int outputSlots, boolean allowOutputInsert) {
        this(inputSlots,outputSlots,allowOutputInsert,false);
    }

    public InputOutputInventoryHandler(int inputSlots, int outputSlots, boolean allowOutputInsert, boolean allowInputExtract) {
        super(inputSlots + outputSlots);
        this.inputSlots = inputSlots;
        this.outputSlots = outputSlots;
        this.allowOutputInsert = allowOutputInsert;
        this.allowInputExtract = allowInputExtract;
    }

    public void setSize(int inputSlots, int outputSlots) {
        setSize(inputSlots + outputSlots);
        this.inputSlots = inputSlots;
        this.outputSlots = outputSlots;
    }

    @Nonnull
    @Override
    public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
        return isOutput(slot) && !allowOutputInsert ? stack : super.insertItem(slot, stack, simulate);
    }

    @Nonnull
    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        return isInput(slot) && !allowInputExtract ? Items.AIR.getDefaultInstance() : super.extractItem(slot,amount,simulate);
    }

    public boolean isOutput(int slot){
        return slot >= inputSlots;
    }
    public boolean isInput(int slot){
        return !isOutput(slot);
    }

    @Override
    public CompoundTag serializeNBT(HolderLookup.Provider provider) {
        ListTag nbtTagList = new ListTag();
        for (int i = 0; i < stacks.size(); i++) {
            if (!stacks.get(i).isEmpty()) {
                CompoundTag itemTag = new CompoundTag();
                itemTag.putInt("Slot", i);
                nbtTagList.add(stacks.get(i).save(provider, itemTag));
            }
        }
        CompoundTag nbt = new CompoundTag();
        nbt.put("Items", nbtTagList);
        nbt.putInt("InputSlots", inputSlots);
        nbt.putInt("OutputSlots", outputSlots);
        return nbt;
    }


    @Override
    public void deserializeNBT(HolderLookup.Provider provider, CompoundTag nbt) {
        setSize((nbt.contains("InputSlots", Tag.TAG_INT) ? nbt.getInt("InputSlots") : inputSlots),(nbt.contains("OutputSlots", Tag.TAG_INT) ? nbt.getInt("OutputSlots") : inputSlots));
        ListTag tagList = nbt.getList("Items", Tag.TAG_COMPOUND);
        for (int i = 0; i < tagList.size(); i++) {
            CompoundTag itemTags = tagList.getCompound(i);
            int slot = itemTags.getInt("Slot");

            if (slot >= 0 && slot < stacks.size()) {
                ItemStack.parse(provider, itemTags).ifPresent(stack -> stacks.set(slot, stack));
            }
        }
        onLoad();
    }
}
