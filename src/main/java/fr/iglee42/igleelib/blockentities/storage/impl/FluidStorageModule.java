package fr.iglee42.igleelib.blockentities.storage.impl;

import fr.iglee42.igleelib.blockentities.storage.SidedStorageModule;
import fr.iglee42.igleelib.blockentities.storage.slot.BasicFluidTank;
import fr.iglee42.igleelib.blockentities.storage.slot.FluidTankSlot;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.neoforged.neoforge.capabilities.BlockCapability;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

/**
 * Fluid storage made of independent {@link FluidTankSlot}s, each with its own capacity, filter and
 * insert/extract rules. Use {@link #builder()} to assemble several tanks.
 */
public class FluidStorageModule extends SidedStorageModule<IFluidHandler> {

    private final List<FluidTankSlot> tanks;
    private final Handler internal = new Handler(null);

    /** Single unrestricted tank. */
    public FluidStorageModule(int capacity) {
        this(capacity, fluid -> true);
    }

    public FluidStorageModule(int capacity, Predicate<FluidStack> validator) {
        this(BasicFluidTank.builder(capacity).validator(validator).build());
    }

    public FluidStorageModule(FluidTankSlot... tanks) {
        this(List.of(tanks));
    }

    public FluidStorageModule(List<? extends FluidTankSlot> tanks) {
        this.tanks = List.copyOf(tanks);
        this.tanks.forEach(t -> t.setChangeListener(this::notifyChanged));
    }

    public static Builder builder() {
        return new Builder();
    }

    public List<FluidTankSlot> tanks() {
        return Collections.unmodifiableList(tanks);
    }

    public FluidTankSlot tank(int index) {
        return tanks.get(index);
    }

    @Override
    public BlockCapability<IFluidHandler, @Nullable Direction> capability() {
        return Capabilities.FluidHandler.BLOCK;
    }

    @Override
    protected IFluidHandler internalHandler() {
        return internal;
    }

    @Override
    protected IFluidHandler createSidedHandler(Direction side) {
        return new Handler(side);
    }

    @Override
    protected void saveContent(CompoundTag tag, HolderLookup.Provider registries) {
        ListTag list = new ListTag();
        for (FluidTankSlot tank : tanks) list.add(tank.serializeNBT(registries));
        tag.put("tanks", list);
    }

    @Override
    protected void loadContent(CompoundTag tag, HolderLookup.Provider registries) {
        if (!tag.contains("tanks", Tag.TAG_LIST)) return;
        ListTag list = tag.getList("tanks", Tag.TAG_COMPOUND);
        for (int i = 0; i < tanks.size() && i < list.size(); i++)
            tanks.get(i).deserializeNBT(registries, list.getCompound(i));
    }

    public static class Builder {
        private final List<FluidTankSlot> tanks = new ArrayList<>();

        public Builder tank(FluidTankSlot tank) {
            tanks.add(tank);
            return this;
        }

        public Builder tank(int capacity) {
            return tank(BasicFluidTank.builder(capacity).build());
        }

        public Builder inputTank(int capacity) {
            return tank(BasicFluidTank.builder(capacity).inputOnly().build());
        }

        public Builder outputTank(int capacity) {
            return tank(BasicFluidTank.builder(capacity).outputOnly().build());
        }

        public FluidStorageModule build() {
            return new FluidStorageModule(tanks);
        }
    }

    /** Handler over all tanks. With a side, the side IO mode and each tank's rules both apply; without, nothing is restricted. */
    private class Handler implements IFluidHandler {
        private final @Nullable Direction side;

        Handler(@Nullable Direction side) {
            this.side = side;
        }

        private boolean mayInsert(FluidTankSlot tank) {
            return side == null || (getMode(side).canInput() && tank.canInsert());
        }

        private boolean mayExtract(FluidTankSlot tank) {
            return side == null || (getMode(side).canOutput() && tank.canExtract());
        }

        @Override
        public int getTanks() {
            return tanks.size();
        }

        @Override
        public @NotNull FluidStack getFluidInTank(int index) {
            return tanks.get(index).getFluid();
        }

        @Override
        public int getTankCapacity(int index) {
            return tanks.get(index).getCapacity();
        }

        @Override
        public boolean isFluidValid(int index, @NotNull FluidStack stack) {
            FluidTankSlot tank = tanks.get(index);
            return mayInsert(tank) && tank.isValid(stack);
        }

        @Override
        public int fill(FluidStack resource, FluidAction action) {
            if (resource.isEmpty()) return 0;
            boolean simulate = action.simulate();
            // Prefer tanks already holding this fluid, then fall back to empty ones.
            for (FluidTankSlot tank : tanks) {
                if (mayInsert(tank) && !tank.getFluid().isEmpty() && FluidStack.isSameFluidSameComponents(tank.getFluid(), resource)) {
                    int filled = tank.fill(resource, simulate);
                    if (filled > 0) return filled;
                }
            }
            for (FluidTankSlot tank : tanks) {
                if (mayInsert(tank) && tank.getFluid().isEmpty()) {
                    int filled = tank.fill(resource, simulate);
                    if (filled > 0) return filled;
                }
            }
            return 0;
        }

        @Override
        public @NotNull FluidStack drain(FluidStack resource, FluidAction action) {
            if (resource.isEmpty()) return FluidStack.EMPTY;
            for (FluidTankSlot tank : tanks) {
                if (mayExtract(tank) && FluidStack.isSameFluidSameComponents(tank.getFluid(), resource)) {
                    FluidStack drained = tank.drain(resource.getAmount(), action.simulate());
                    if (!drained.isEmpty()) return drained;
                }
            }
            return FluidStack.EMPTY;
        }

        @Override
        public @NotNull FluidStack drain(int maxDrain, FluidAction action) {
            for (FluidTankSlot tank : tanks) {
                if (mayExtract(tank) && !tank.getFluid().isEmpty()) {
                    FluidStack drained = tank.drain(maxDrain, action.simulate());
                    if (!drained.isEmpty()) return drained;
                }
            }
            return FluidStack.EMPTY;
        }
    }
}
