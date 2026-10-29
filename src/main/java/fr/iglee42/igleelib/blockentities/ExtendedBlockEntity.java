package fr.iglee42.igleelib.blockentities;

import fr.iglee42.igleelib.blockentities.data.Save;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.capabilities.Capabilities;

public abstract class ExtendedBlockEntity extends SavedBlockEntity {

    @Save(synchronize = true)
    private int tickCount = 0;

    public ExtendedBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    // Ticking Management

    public void tick(Level level, BlockPos pos, BlockState state){
        if (level == null || pos == null || state == null) return;
        if (level.isClientSide)
            clientTick(level,pos,state);
        else {
            boolean sendUpdatePacket = serverTick((ServerLevel) level,pos,state);
            tickCount++;
            if (sendUpdatePacket) setChanged();
        }
    }

    @OnlyIn(Dist.CLIENT)
    protected void clientTick(Level level, BlockPos pos, BlockState state) {}
    protected boolean serverTick(ServerLevel level, BlockPos pos, BlockState state) {return false;}

    @Override
    public final void setChanged() {
        super.setChanged();
        changed();
        if (level == null) return;
        if (level.isClientSide) return;
        level.sendBlockUpdated(getBlockPos(),getBlockState(),getBlockState(), Block.UPDATE_CLIENTS);
    }

    protected void changed(){}

    public void setPlacedBy(Player player){};
    public void destroy(){
        if (level != null && !level.isClientSide){
            var itemHandler = level.getCapability(Capabilities.ItemHandler.BLOCK,getBlockPos(),getBlockState(),this,null);
            if (itemHandler != null){
                for (int slot = 0; slot < itemHandler.getSlots(); slot++){
                    var stack = itemHandler.getStackInSlot(slot);
                    if (!stack.isEmpty())
                        Block.popResource(level, getBlockPos(), stack);
                }
            }
        }
    }

}
