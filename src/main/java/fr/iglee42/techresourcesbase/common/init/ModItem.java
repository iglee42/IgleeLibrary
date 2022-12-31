package fr.iglee42.techresourcesbase.common.init;

import fr.iglee42.techresourcesbase.TechResourcesBase;
import fr.iglee42.techresourcesbase.common.baseutils.CustomGroup;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.List;

public class ModItem {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, TechResourcesBase.MODID);

    public static final RegistryObject<Item> ULTIMERITE_INGOT = ITEMS.register("ultimerite_ingot",() -> new Item(new Item.Properties().tab(CustomGroup.BASE_GROUP)));
    public static final RegistryObject<Item> ULTIMATE_GESSENCE = ITEMS.register("ultimate_gessence",() -> new Item(new Item.Properties().tab(CustomGroup.BASE_GROUP)));

    public static final RegistryObject<Item> LAVA_SHARD = ITEMS.register("lava_shard",()-> new Item(new Item.Properties().tab(CustomGroup.BASE_GROUP)){
        @Override
        public void appendHoverText(ItemStack p_77624_1_, @Nullable World p_77624_2_, List<ITextComponent> p_77624_3_, ITooltipFlag p_77624_4_) {
            p_77624_3_.add(new StringTextComponent("Obtain when right click on a blaze with netherite scrap").withStyle(TextFormatting.GOLD));
            super.appendHoverText(p_77624_1_, p_77624_2_, p_77624_3_, p_77624_4_);
        }
    });

    public static final RegistryObject<Item> MODIUM_INGOT = ITEMS.register("modium_ingot",() -> new Item(new Item.Properties().tab(CustomGroup.BASE_GROUP)));
    public static final RegistryObject<Item> DERIUM_INGOT = ITEMS.register("derium_ingot",() -> new Item(new Item.Properties().tab(CustomGroup.BASE_GROUP)));
    public static final RegistryObject<Item> BLAZUM_INGOT = ITEMS.register("blazum_ingot",() -> new Item(new Item.Properties().tab(CustomGroup.BASE_GROUP)));
    public static final RegistryObject<Item> LAVIUM_INGOT = ITEMS.register("lavium_ingot",() -> new Item(new Item.Properties().tab(CustomGroup.BASE_GROUP)));


}
