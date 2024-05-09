package fr.iglee42.igleelib.common.init;

import fr.iglee42.igleelib.IgleeLibrary;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SmithingTemplateItem;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;

import static fr.iglee42.igleelib.IgleeLibrary.MODID;

public class ModItem {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);

    public static final DeferredItem<Item> BLAZE_SHARD = ITEMS.register("blaze_shard",()-> new Item(new Item.Properties()){
        @Override
        public void appendHoverText(ItemStack p_41421_, TooltipContext p_339594_, List<Component> components, TooltipFlag p_41424_) {
            components.add(Component.literal("Obtain when right click on a blaze with netherite scrap").withStyle(ChatFormatting.YELLOW));
            super.appendHoverText(p_41421_, p_339594_, components, p_41424_);
        }
    });

    public static final DeferredItem<Item> MODIUM_INGOT = ITEMS.register("modium_ingot",() -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> DERIUM_INGOT = ITEMS.register("derium_ingot",() -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> BLAZUM_INGOT = ITEMS.register("blazum_ingot",() -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> LAVIUM_INGOT = ITEMS.register("lavium_ingot",() -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> NETHERITE_UPGRADE_SMITHING_TEMPLATE = ITEMS.register("iglium_upgrade_smithing_template", ()-> new SmithingTemplateItem(
            Component.translatable(Util.makeDescriptionId("item", new ResourceLocation(MODID,"smithing_template.iglium_upgrade.applies_to"))).withStyle(ChatFormatting.BLUE),
            Component.translatable(Util.makeDescriptionId("item", new ResourceLocation(MODID,"smithing_template.iglium_upgrade.ingredients"))).withStyle(ChatFormatting.BLUE),
            Component.translatable("item.igleelib.iglium_upgrade_smithing_template"),
            Component.translatable(Util.makeDescriptionId("item", new ResourceLocation(MODID,"smithing_template.iglium_upgrade.base_slot_description"))),
            Component.translatable(Util.makeDescriptionId("item", new ResourceLocation(MODID,"smithing_template.iglium_upgrade.additions_slot_description"))),
            List.of(new ResourceLocation("item/empty_slot_ingot")),
            List.of(new ResourceLocation("item/empty_slot_diamond"))));


}
