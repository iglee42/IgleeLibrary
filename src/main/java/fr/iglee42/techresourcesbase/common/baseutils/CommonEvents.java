package fr.iglee42.techresourcesbase.baseutils;

import fr.iglee42.techresourcesbase.TechResourcesBase;
import fr.iglee42.techresourcesbase.common.init.ModItem;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = TechResourcesBase.MODID)
public class CommonEvents {

    @SubscribeEvent
    public static void entityInteract(final PlayerInteractEvent.EntityInteractSpecific event){
        if (event.getTarget().getType() != EntityType.BLAZE) return;
        if (event.getEntityLiving().getMainHandItem().getItem() == Items.AIR) return;
        if (event.getEntityLiving().getMainHandItem().getItem() != Items.NETHERITE_SCRAP) return;
        event.getEntityLiving().getMainHandItem().setCount(event.getEntityLiving().getMainHandItem().getCount()-1);
        Block.popResource(event.getTarget().level,event.getTarget().blockPosition().offset(0,1,0), new ItemStack(ModItem.LAVA_SHARD.get()));
        event.getTarget().remove();
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
