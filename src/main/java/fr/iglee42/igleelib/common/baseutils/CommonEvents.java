package fr.iglee42.igleelib.common.baseutils;

import fr.iglee42.igleelib.IgleeLibrary;
import fr.iglee42.igleelib.common.init.ModItem;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = IgleeLibrary.MODID)
public class CommonEvents {

    @SubscribeEvent
    public static void entityInteract(final PlayerInteractEvent.EntityInteractSpecific event){
        if (event.getTarget().getType() != EntityType.BLAZE) return;
        if (event.getEntity().getMainHandItem().getItem() == Items.AIR) return;
        if (event.getEntity().getMainHandItem().getItem() != Items.NETHERITE_SCRAP) return;
        event.getEntity().getMainHandItem().setCount(event.getEntity().getMainHandItem().getCount()-1);
        Block.popResource(event.getTarget().level(),event.getTarget().getOnPos().offset(0,1,0), new ItemStack(ModItem.BLAZE_SHARD.get()));
        event.getTarget().remove(Entity.RemovalReason.KILLED);
    }

}
