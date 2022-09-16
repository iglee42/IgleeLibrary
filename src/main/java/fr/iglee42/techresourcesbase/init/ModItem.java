package fr.iglee42.techresourcesbase.init;

import fr.iglee42.techresourcesbase.TechResourcesBase;
import fr.iglee42.techresourcesbase.baseutils.CustomGroup;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ModItem {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, TechResourcesBase.MODID);

    public static final RegistryObject<Item> ULTIMERITE_INGOT = ITEMS.register("ultimerite_ingot",() -> new Item(new Item.Properties().tab(CustomGroup.BASE_GROUP)));
    public static final RegistryObject<Item> ULTIMATE_GESSENCE = ITEMS.register("ultimate_gessence",() -> new Item(new Item.Properties().tab(CustomGroup.BASE_GROUP)));

    public static final RegistryObject<Item> LAVA_SHARD = ITEMS.register("lava_shard",()-> new Item(new Item.Properties().tab(CustomGroup.BASE_GROUP)){
        @Override
        public void appendHoverText(ItemStack p_41421_, @Nullable Level p_41422_, List<Component> p_41423_, TooltipFlag p_41424_) {
            p_41423_.add(new TextComponent("§eObtain when right click on a blaze with netherite scrap"));
            super.appendHoverText(p_41421_, p_41422_, p_41423_, p_41424_);
        }
    });

    public static final RegistryObject<Item> MODIUM_INGOT = ITEMS.register("modium_ingot",() -> new Item(new Item.Properties().tab(CustomGroup.BASE_GROUP)));
    public static final RegistryObject<Item> DERIUM_INGOT = ITEMS.register("derium_ingot",() -> new Item(new Item.Properties().tab(CustomGroup.BASE_GROUP)));
    public static final RegistryObject<Item> BLAZUM_INGOT = ITEMS.register("blazum_ingot",() -> new Item(new Item.Properties().tab(CustomGroup.BASE_GROUP)));
    public static final RegistryObject<Item> LAVIUM_INGOT = ITEMS.register("lavium_ingot",() -> new Item(new Item.Properties().tab(CustomGroup.BASE_GROUP)));


}
