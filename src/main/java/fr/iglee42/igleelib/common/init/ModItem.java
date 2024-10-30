package fr.iglee42.igleelib.common.init;

import fr.iglee42.igleelib.IgleeLibrary;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;

import static fr.iglee42.igleelib.IgleeLibrary.MODID;

public class ModItem {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);

    public static final DeferredItem<Item> BLAZE_SHARD = ITEMS.registerItem("blaze_shard",(p)-> new Item(p){
        @Override
        public void appendHoverText(ItemStack p_41421_, TooltipContext p_339594_, List<Component> components, TooltipFlag p_41424_) {
            components.add(Component.literal("Obtain when right click on a blaze with netherite scrap").withStyle(ChatFormatting.YELLOW));
            super.appendHoverText(p_41421_, p_339594_, components, p_41424_);
        }
    },new Item.Properties());

    public static final DeferredItem<Item> MODIUM_INGOT = ITEMS.registerItem("modium_ingot",Item::new);
    public static final DeferredItem<Item> DERIUM_INGOT = ITEMS.registerItem("derium_ingot",Item::new);
    public static final DeferredItem<Item> BLAZUM_INGOT = ITEMS.registerItem("blazum_ingot",Item::new);
    public static final DeferredItem<Item> LAVIUM_INGOT = ITEMS.registerItem("lavium_ingot",Item::new);
    public static final DeferredItem<Item> NETHERITE_UPGRADE_SMITHING_TEMPLATE = ITEMS.registerItem("iglium_upgrade_smithing_template", (p)-> new SmithingTemplateItem(
            Component.translatable(Util.makeDescriptionId("item", ResourceLocation.fromNamespaceAndPath(MODID,"smithing_template.iglium_upgrade.applies_to"))).withStyle(ChatFormatting.BLUE),
            Component.translatable(Util.makeDescriptionId("item", ResourceLocation.fromNamespaceAndPath(MODID,"smithing_template.iglium_upgrade.ingredients"))).withStyle(ChatFormatting.BLUE),
            Component.translatable(Util.makeDescriptionId("item", ResourceLocation.fromNamespaceAndPath(MODID,"smithing_template.iglium_upgrade.base_slot_description"))),
            Component.translatable(Util.makeDescriptionId("item", ResourceLocation.fromNamespaceAndPath(MODID,"smithing_template.iglium_upgrade.additions_slot_description"))),
            List.of(ResourceLocation.withDefaultNamespace("item/empty_slot_ingot")),
            List.of(ResourceLocation.withDefaultNamespace("item/empty_slot_diamond")),
            p),new Item.Properties().rarity(Rarity.UNCOMMON));


}
