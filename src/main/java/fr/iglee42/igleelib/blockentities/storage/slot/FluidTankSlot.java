package fr.iglee42.igleelib.blockentities.storage.slot;

import fr.iglee42.igleelib.blockentities.storage.AutomationType;
import net.minecraft.nbt.CompoundTag;
import net.neoforged.neoforge.common.util.INBTSerializable;
import net.neoforged.neoforge.fluids.FluidStack;

/**
 * A single tank of a {@link fr.iglee42.igleelib.blockentities.storage.impl.FluidStorageModule}.
 * Mods can implement it to provide tanks with their own rules.
 */
public interface FluidTankSlot extends INBTSerializable<CompoundTag> {

    FluidStack getFluid();

    void setFluid(FluidStack fluid);

    int getCapacity();

    default boolean isValid(FluidStack fluid) {
        return true;
    }

    /** Whether this tank may be filled from the given origin. */
    default boolean canInsert(AutomationType type) {
        return true;
    }

    /** Whether this tank may be drained from the given origin. */
    default boolean canExtract(AutomationType type) {
        return true;
    }

    /** Called by the owning module so the tank can signal content changes. */
    void setChangeListener(Runnable listener);

    /** Fills the tank and returns the amount accepted. */
    default int fill(FluidStack resource, boolean simulate, AutomationType type) {
        if (resource.isEmpty() || !isValid(resource) || !canInsert(type)) return 0;
        FluidStack current = getFluid();
        if (current.isEmpty()) {
            int filled = Math.min(getCapacity(), resource.getAmount());
            if (!simulate) setFluid(resource.copyWithAmount(filled));
            return filled;
        }
        if (!FluidStack.isSameFluidSameComponents(current, resource)) return 0;
        int filled = Math.min(getCapacity() - current.getAmount(), resource.getAmount());
        if (filled > 0 && !simulate) setFluid(current.copyWithAmount(current.getAmount() + filled));
        return Math.max(filled, 0);
    }

    /** Drains up to {@code amount} and returns what was removed. */
    default FluidStack drain(int amount, boolean simulate, AutomationType type) {
        FluidStack current = getFluid();
        if (current.isEmpty() || amount <= 0 || !canExtract(type)) return FluidStack.EMPTY;
        int drained = Math.min(current.getAmount(), amount);
        FluidStack result = current.copyWithAmount(drained);
        if (!simulate) setFluid(drained >= current.getAmount() ? FluidStack.EMPTY : current.copyWithAmount(current.getAmount() - drained));
        return result;
    }

    /** Slot semantic marker. Mods can use this for auto IO detection or UI hints. */
    default SlotType getType() {
        return SlotType.NORMAL;
    }

    FluidTankSlot copy();
}
