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
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static fr.iglee42.igleelib.IgleeLibrary.MODID;

public class ModItem {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);

    //public static final RegistryObject<Item> ULTIMERITE_INGOT = ITEMS.register("ultimerite_ingot",() -> new Item(new Item.Properties()));
    //public static final RegistryObject<Item> ULTIMATE_GESSENCE = ITEMS.register("ultimate_gessence",() -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> BLAZE_SHARD = ITEMS.register("blaze_shard",()-> new Item(new Item.Properties()){
        @Override
        public void appendHoverText(ItemStack p_41421_, @Nullable Level p_41422_, List<Component> p_41423_, TooltipFlag p_41424_) {
            p_41423_.add(Component.literal("Obtain when right click on a blaze with netherite scrap").withStyle(ChatFormatting.YELLOW));
            super.appendHoverText(p_41421_, p_41422_, p_41423_, p_41424_);
        }
    });

    public static final RegistryObject<Item> MODIUM_INGOT = ITEMS.register("modium_ingot",() -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> DERIUM_INGOT = ITEMS.register("derium_ingot",() -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> BLAZUM_INGOT = ITEMS.register("blazum_ingot",() -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> LAVIUM_INGOT = ITEMS.register("lavium_ingot",() -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> NETHERITE_UPGRADE_SMITHING_TEMPLATE = ITEMS.register("iglium_upgrade_smithing_template", ()-> new SmithingTemplateItem(
            Component.translatable(Util.makeDescriptionId("item", new ResourceLocation(MODID,"smithing_template.iglium_upgrade.applies_to"))).withStyle(ChatFormatting.BLUE),
            Component.translatable(Util.makeDescriptionId("item", new ResourceLocation(MODID,"smithing_template.iglium_upgrade.ingredients"))).withStyle(ChatFormatting.BLUE),
            Component.translatable("item.igleelib.iglium_upgrade_smithing_template"),
            Component.translatable(Util.makeDescriptionId("item", new ResourceLocation(MODID,"smithing_template.iglium_upgrade.base_slot_description"))),
            Component.translatable(Util.makeDescriptionId("item", new ResourceLocation(MODID,"smithing_template.iglium_upgrade.additions_slot_description"))),
            List.of(new ResourceLocation("item/empty_slot_ingot")),
            List.of(new ResourceLocation("item/empty_slot_diamond"))));

}
