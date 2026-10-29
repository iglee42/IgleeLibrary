package fr.iglee42.igleelib.blockentities.storage.impl;

import fr.iglee42.igleelib.blockentities.storage.SidedStorageModule;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.neoforged.neoforge.capabilities.BlockCapability;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.EnergyStorage;
import net.neoforged.neoforge.energy.IEnergyStorage;
import org.jetbrains.annotations.Nullable;

public class EnergyStorageModule extends SidedStorageModule<IEnergyStorage> {

    private final Storage storage;

    public EnergyStorageModule(int capacity) {
        this(capacity, capacity, capacity);
    }

    public EnergyStorageModule(int capacity, int maxReceive, int maxExtract) {
        this.storage = new Storage(capacity, maxReceive, maxExtract);
    }

    /** Internal, unrestricted energy storage (limited by the max receive/extract rates). */
    public EnergyStorage storage() {
        return storage;
    }

    public int getEnergy() {
        return storage.getEnergyStored();
    }

    /** Adds energy ignoring the transfer rate; returns the amount actually added. */
    public int produce(int amount) {
        int added = Math.max(0, Math.min(amount, storage.getMaxEnergyStored() - storage.getEnergyStored()));
        storage.setEnergy(storage.getEnergyStored() + added);
        return added;
    }

    /** Removes energy ignoring the transfer rate; returns the amount actually removed. */
    public int consume(int amount) {
        int removed = Math.max(0, Math.min(amount, storage.getEnergyStored()));
        storage.setEnergy(storage.getEnergyStored() - removed);
        return removed;
    }

    @Override
    public BlockCapability<IEnergyStorage, @Nullable Direction> capability() {
        return Capabilities.EnergyStorage.BLOCK;
    }

    @Override
    protected IEnergyStorage internalHandler() {
        return storage;
    }

    @Override
    protected IEnergyStorage createSidedHandler(Direction side) {
        return new Sided(side);
    }

    @Override
    protected void saveContent(CompoundTag tag, HolderLookup.Provider registries) {
        tag.putInt("energy", storage.getEnergyStored());
    }

    @Override
    protected void loadContent(CompoundTag tag, HolderLookup.Provider registries) {
        storage.setEnergy(tag.getInt("energy"));
    }

    private class Storage extends EnergyStorage {
        Storage(int capacity, int maxReceive, int maxExtract) {
            super(capacity, maxReceive, maxExtract);
        }

        void setEnergy(int value) {
            int clamped = Math.max(0, Math.min(capacity, value));
            if (clamped == energy) return;
            energy = clamped;
            notifyChanged();
        }

        @Override
        public int receiveEnergy(int toReceive, boolean simulate) {
            int received = super.receiveEnergy(toReceive, simulate);
            if (received > 0 && !simulate) notifyChanged();
            return received;
        }

        @Override
        public int extractEnergy(int toExtract, boolean simulate) {
            int extracted = super.extractEnergy(toExtract, simulate);
            if (extracted > 0 && !simulate) notifyChanged();
            return extracted;
        }
    }

    private class Sided implements IEnergyStorage {
        private final Direction side;

        Sided(Direction side) {
            this.side = side;
        }

        @Override
        public int receiveEnergy(int toReceive, boolean simulate) {
            return getMode(side).canInput() ? storage.receiveEnergy(toReceive, simulate) : 0;
        }

        @Override
        public int extractEnergy(int toExtract, boolean simulate) {
            return getMode(side).canOutput() ? storage.extractEnergy(toExtract, simulate) : 0;
        }

        @Override
        public int getEnergyStored() {
            return storage.getEnergyStored();
        }

        @Override
        public int getMaxEnergyStored() {
            return storage.getMaxEnergyStored();
        }

        @Override
        public boolean canExtract() {
            return getMode(side).canOutput() && storage.canExtract();
        }

        @Override
        public boolean canReceive() {
            return getMode(side).canInput() && storage.canReceive();
        }
    }
}