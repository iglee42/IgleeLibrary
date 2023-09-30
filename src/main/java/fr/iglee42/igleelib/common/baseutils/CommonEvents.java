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
        Block.popResource(event.getTarget().level(),event.getTarget().getOnPos().offset(0,1,0), new ItemStack(ModItem.LAVA_SHARD.get()));
        event.getTarget().remove(Entity.RemovalReason.KILLED);
    }

   /* @SubscribeEvent
    public static void damage(LivingDamageEvent event){
        if (event.getEntity().getType() != EntityType.PLAYER) return;
        Player p = (Player) event.getEntity();
        ItemStack helm = p.getInventory().getArmor(3);
        ItemStack chest = p.getInventory().getArmor(2);
        ItemStack legs = p.getInventory().getArmor(1);
        ItemStack boots = p.getInventory().getArmor(0);

        if (!helm.is(ModItem.ULTIMERITE_HELMET.get())) return;
        if (!chest.is(ModItem.ULTIMERITE_CHESTPLATE.get())) return;
        if (!legs.is(ModItem.ULTIMERITE_LEGGINGS.get())) return;
        if (!boots.is(ModItem.ULTIMERITE_BOOTS.get())) return;

        event.setCanceled(true);
    }

    @SubscribeEvent
    public static void equipment(LivingEquipmentChangeEvent event){
        if (event.getEntity().getType() != EntityType.PLAYER) return;
        switch (event.getSlot()){
            case HEAD :
                if (event.getTo().getItem() == Items.AIR && event.getFrom().getItem() == ModItem.ULTIMERITE_HELMET.get()){
                    ((UltimeriteArmor)event.getFrom().getItem()).stopFlying((Player) event.getEntity());
                } else if (event.getTo().getItem() == ModItem.ULTIMERITE_HELMET.get() && event.getFrom().getItem() == Items.AIR){
                    if (((UltimeriteArmor)event.getTo().getItem()).isFull()){
                        ((UltimeriteArmor)event.getTo().getItem()).startFlying((Player) event.getEntity());
                    }
                }
                break;
            case CHEST :
                if (event.getTo().getItem() == Items.AIR && event.getFrom().getItem() == ModItem.ULTIMERITE_CHESTPLATE.get()){
                    ((UltimeriteArmor)event.getFrom().getItem()).stopFlying((Player) event.getEntity());
                } else if (event.getTo().getItem() == ModItem.ULTIMERITE_CHESTPLATE.get() && event.getFrom().getItem() == Items.AIR){
                    if (((UltimeriteArmor)event.getTo().getItem()).isFull()){
                        ((UltimeriteArmor)event.getTo().getItem()).startFlying((Player) event.getEntity());
                    }
                }
                break;
            case LEGS :
                if (event.getTo().getItem() == Items.AIR && event.getFrom().getItem() == ModItem.ULTIMERITE_LEGGINGS.get()){
                    ((UltimeriteArmor)event.getFrom().getItem()).stopFlying((Player) event.getEntity());
                } else if (event.getTo().getItem() == ModItem.ULTIMERITE_LEGGINGS.get() && event.getFrom().getItem() == Items.AIR){
                    if (((UltimeriteArmor)event.getTo().getItem()).isFull()){
                        ((UltimeriteArmor)event.getTo().getItem()).startFlying((Player) event.getEntity());
                    }
                }
                break;
            case FEET :
                if (event.getTo().getItem() == Items.AIR && event.getFrom().getItem() == ModItem.ULTIMERITE_BOOTS.get()){
                    ((UltimeriteArmor)event.getFrom().getItem()).stopFlying((Player) event.getEntity());
                } else if (event.getTo().getItem() == ModItem.ULTIMERITE_BOOTS.get() && event.getFrom().getItem() == Items.AIR){
                    if (((UltimeriteArmor)event.getTo().getItem()).isFull()){
                        ((UltimeriteArmor)event.getTo().getItem()).startFlying((Player) event.getEntity());
                    }
                }
                break;
        }
    }*/
}
