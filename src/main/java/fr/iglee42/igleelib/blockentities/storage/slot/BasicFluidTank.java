package fr.iglee42.igleelib.blockentities.storage.slot;

import fr.iglee42.igleelib.blockentities.storage.AutomationType;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.neoforged.neoforge.fluids.FluidStack;

import java.util.function.Predicate;

/** Default {@link FluidTankSlot}, created through {@link #builder(int)}. */
public class BasicFluidTank implements FluidTankSlot {

    private final int capacity;
    private final Predicate<FluidStack> validator;
    private final Predicate<AutomationType> canInsert;
    private final Predicate<AutomationType> canExtract;
    private FluidStack fluid = FluidStack.EMPTY;
    private Runnable listener = () -> {};

    private BasicFluidTank(BasicFluidTank oldTank){
        this.capacity = oldTank.capacity;
        this.validator = oldTank.validator;
        this.canInsert = oldTank.canInsert;
        this.canExtract = oldTank.canExtract;
        this.fluid = oldTank.fluid.copy();
    }

    private BasicFluidTank(Builder builder) {
        this.capacity = builder.capacity;
        this.validator = builder.validator;
        this.canInsert = builder.canInsert;
        this.canExtract = builder.canExtract;
    }

    public static Builder builder(int capacity) {
        return new Builder(capacity);
    }

    @Override
    public FluidStack getFluid() {
        return fluid;
    }

    @Override
    public void setFluid(FluidStack fluid) {
        this.fluid = fluid;
        listener.run();
    }

    @Override
    public int getCapacity() {
        return capacity;
    }

    @Override
    public boolean isValid(FluidStack fluid) {
        return validator.test(fluid);
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
        CompoundTag tag = new CompoundTag();
        if (!fluid.isEmpty()) tag.put("fluid", fluid.save(registries));
        return tag;
    }

    @Override
    public void deserializeNBT(HolderLookup.Provider registries, CompoundTag tag) {
        fluid = tag.contains("fluid") ? FluidStack.parseOptional(registries, tag.getCompound("fluid")) : FluidStack.EMPTY;
    }

    public static class Builder {
        private final int capacity;
        private Predicate<FluidStack> validator = f -> true;
        private Predicate<AutomationType> canInsert = t -> true;
        private Predicate<AutomationType> canExtract = t -> true;

        private Builder(int capacity) {
            this.capacity = capacity;
        }

        public Builder validator(Predicate<FluidStack> validator) {
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

        /** Shortcut: when false, only {@link AutomationType#INTERNAL} may fill. */
        public Builder canInsert(boolean canInsert) {
            return canInsert(t -> t == AutomationType.INTERNAL || canInsert);
        }

        /** Shortcut: when false, only {@link AutomationType#INTERNAL} may drain. */
        public Builder canExtract(boolean canExtract) {
            return canExtract(t -> t == AutomationType.INTERNAL || canExtract);
        }

        /** Outside handlers can only fill this tank (e.g. a machine ingredient). */
        public Builder inputOnly() {
            return canInsert(true).canExtract(false);
        }

        /** Outside handlers can only drain this tank (e.g. a machine result). */
        public Builder outputOnly() {
            return canInsert(false).canExtract(true);
        }

        public BasicFluidTank build() {
            return new BasicFluidTank(this);
        }
    }

    @Override
    public FluidTankSlot copy() {
        return new BasicFluidTank(this);
    }
}
