package fr.iglee42.techresourcesbase.utils;

import fr.iglee42.techresourcesbase.TechResourcesBase;
import fr.iglee42.techresourcesbase.init.ModItem;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = TechResourcesBase.MODID)
public class ModEvents {

    @SubscribeEvent
    public static void entityInteract(final PlayerInteractEvent.EntityInteractSpecific event){
        if (event.getTarget().getType() != EntityType.BLAZE) return;
        if (event.getPlayer().getMainHandItem().getItem() == Items.AIR) return;
        if (event.getPlayer().getMainHandItem().getItem() != Items.NETHERITE_SCRAP) return;
        event.getPlayer().getMainHandItem().setCount(event.getPlayer().getMainHandItem().getCount()-1);
        Block.popResource(event.getTarget().getLevel(),event.getTarget().getOnPos().offset(0,1,0), new ItemStack(ModItem.LAVA_SHARD.get()));
        event.getTarget().remove(Entity.RemovalReason.KILLED);
    }
}
