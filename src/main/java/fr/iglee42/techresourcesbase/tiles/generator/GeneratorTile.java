package fr.iglee42.techresourcesbase.tiles.generator;

import fr.iglee42.techresourcesbase.utils.GessenceType;
import net.minecraft.block.BlockState;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.HopperTileEntity;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;

import java.util.Objects;

public class GeneratorTile extends TileEntity implements ITickableTileEntity {

    private GessenceType type;
    private ItemStack output_stack;
    private boolean enabled;
    private int tick = 0;
    private final int itemPerSecond;
    public GeneratorTile(TileEntityType<?> p_i48289_1_,int itemPerSecond) {
        super(p_i48289_1_);
        this.itemPerSecond = itemPerSecond;
    }



    @Override
    public void tick() {
        enabled = (output_stack == null || output_stack.getCount() < 64) && hasGessence();
        if (enabled){
            tick++;
            if (tick == 20){ second(); tick = 0;}
        }
    }

    private void second() {
        if (type == null) return;
        if (output_stack == null){
            output_stack = new ItemStack(type.getItem(),itemPerSecond);
        }
        BlockPos pos = this.getBlockPos().below();
        assert this.level != null;
        IInventory iInventory = HopperTileEntity.getContainerAt(this.level,pos);

        if (iInventory == null){
            InventoryHelper.dropItemStack(Objects.requireNonNull(this.getLevel()),pos.getX(),pos.getY(),pos.getZ(),output_stack);
        } else {
            HopperTileEntity.addItem(null, iInventory, output_stack, Direction.DOWN);
        }
        output_stack = null;
    }




    public boolean hasGessence(){
        return type != null;
    }

    public GessenceType getGessence() {
        return type;
    }

    public void setGessence(GessenceType type){
        this.type = type;
    }

    public int getTick() {
        return tick;
    }

    public void setTick(int tick) {
        this.tick = tick;
    }
}
