package fr.iglee42.igleelib.blockentities.storage.impl;

import fr.iglee42.igleelib.blockentities.storage.SidedStorageModule;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.neoforged.neoforge.capabilities.BlockCapability;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

/** A single fluid tank. Add several modules with different ids for several tanks. */
public class FluidStorageModule extends SidedStorageModule<IFluidHandler> {

    private final Tank tank;

    public FluidStorageModule(int capacity) {
        this(capacity, fluid -> true);
    }

    public FluidStorageModule(int capacity, Predicate<FluidStack> validator) {
        this.tank = new Tank(capacity, validator);
    }

    /** Internal, unrestricted tank. */
    public FluidTank tank() {
        return tank;
    }

    @Override
    public BlockCapability<IFluidHandler, @Nullable Direction> capability() {
        return Capabilities.FluidHandler.BLOCK;
    }

    @Override
    protected IFluidHandler internalHandler() {
        return tank;
    }

    @Override
    protected IFluidHandler createSidedHandler(Direction side) {
        return new Sided(side);
    }

    @Override
    protected void saveContent(CompoundTag tag, HolderLookup.Provider registries) {
        tank.writeToNBT(registries, tag);
    }

    @Override
    protected void loadContent(CompoundTag tag, HolderLookup.Provider registries) {
        tank.readFromNBT(registries, tag);
    }

    private class Tank extends FluidTank {
        Tank(int capacity, Predicate<FluidStack> validator) {
            super(capacity, validator);
        }

        @Override
        protected void onContentsChanged() {
            notifyChanged();
        }
    }

    private class Sided implements IFluidHandler {
        private final Direction side;

        Sided(Direction side) {
            this.side = side;
        }

        @Override
        public int getTanks() {
            return tank.getTanks();
        }

        @Override
        public @NotNull FluidStack getFluidInTank(int index) {
            return tank.getFluidInTank(index);
        }

        @Override
        public int getTankCapacity(int index) {
            return tank.getTankCapacity(index);
        }

        @Override
        public boolean isFluidValid(int index, @NotNull FluidStack stack) {
            return getMode(side).canInput() && tank.isFluidValid(index, stack);
        }

        @Override
        public int fill(FluidStack resource, FluidAction action) {
            return getMode(side).canInput() ? tank.fill(resource, action) : 0;
        }

        @Override
        public @NotNull FluidStack drain(FluidStack resource, FluidAction action) {
            return getMode(side).canOutput() ? tank.drain(resource, action) : FluidStack.EMPTY;
        }

        @Override
        public @NotNull FluidStack drain(int maxDrain, FluidAction action) {
            return getMode(side).canOutput() ? tank.drain(maxDrain, action) : FluidStack.EMPTY;
        }
    }
}