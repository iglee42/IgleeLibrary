package fr.iglee42.techresourcesbase.items;

import fr.iglee42.techresourcesbase.init.ModItem;
import fr.iglee42.techresourcesbase.utils.CustomGroup;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class UltimeriteArmor extends ArmorItem {
    public UltimeriteArmor(IArmorMaterial p_i48534_1_, EquipmentSlotType p_i48534_2_) {
        super(p_i48534_1_, p_i48534_2_, new Item.Properties().tab(CustomGroup.BASE_GROUP));
    }

    @Override
    public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
        if (stack.getItem() == ModItem.ULTIMERITE_CHESTPLATE.get()){

        }
        super.onArmorTick(stack, world, player);
    }
}
