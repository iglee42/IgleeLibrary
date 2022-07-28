package fr.iglee42.techresourcesbase.items;

import fr.iglee42.techresourcesbase.init.ModItem;
import fr.iglee42.techresourcesbase.utils.CustomGroup;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class UltimeriteArmor extends ArmorItem {
    public UltimeriteArmor(ArmorMaterial p_i48534_1_, EquipmentSlot p_i48534_2_) {
        super(p_i48534_1_, p_i48534_2_, new Item.Properties().tab(CustomGroup.BASE_GROUP));
    }

    @Override
    public void onArmorTick(ItemStack stack, Level world, Player player) {
        if (stack.getItem() == ModItem.ULTIMERITE_CHESTPLATE.get()){

        }
        super.onArmorTick(stack, world, player);
    }
}
