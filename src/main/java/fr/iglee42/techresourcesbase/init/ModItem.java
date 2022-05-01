package fr.iglee42.techresourcesbase.init;

import fr.iglee42.techresourcesbase.TechResourcesBase;
import fr.iglee42.techresourcesbase.items.GeneratorItem;
import fr.iglee42.techresourcesbase.items.Gessence;
import fr.iglee42.techresourcesbase.items.UltimeriteArmor;
import fr.iglee42.techresourcesbase.utils.CustomArmorMaterial;
import fr.iglee42.techresourcesbase.utils.CustomGroup;
import fr.iglee42.techresourcesbase.utils.GessenceType;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItem {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, TechResourcesBase.MODID);


    public static final RegistryObject<Item> ULTIMERITE_HELMET = ITEMS.register("ultimerite_helmet",() -> new UltimeriteArmor(CustomArmorMaterial.ULTIMERITE, EquipmentSlotType.HEAD));
    public static final RegistryObject<Item> ULTIMERITE_CHESTPLATE = ITEMS.register("ultimerite_chestplate",() -> new UltimeriteArmor(CustomArmorMaterial.ULTIMERITE, EquipmentSlotType.CHEST));
    public static final RegistryObject<Item> ULTIMERITE_LEGGINGS = ITEMS.register("ultimerite_leggings",() -> new UltimeriteArmor(CustomArmorMaterial.ULTIMERITE, EquipmentSlotType.LEGS));
    public static final RegistryObject<Item> ULTIMERITE_BOOTS = ITEMS.register("ultimerite_boots",() -> new UltimeriteArmor(CustomArmorMaterial.ULTIMERITE, EquipmentSlotType.FEET));


    public static final RegistryObject<Item> MODIUM_INGOT = ITEMS.register("modium_ingot",() -> new Item(new Item.Properties().tab(CustomGroup.BASE_GROUP)));
    public static final RegistryObject<Item> DERIUM_INGOT = ITEMS.register("derium_ingot",() -> new Item(new Item.Properties().tab(CustomGroup.BASE_GROUP)));
    public static final RegistryObject<Item> BLAZUM_INGOT = ITEMS.register("blazum_ingot",() -> new Item(new Item.Properties().tab(CustomGroup.BASE_GROUP)));
    public static final RegistryObject<Item> LAVIUM_INGOT = ITEMS.register("lavium_ingot",() -> new Item(new Item.Properties().tab(CustomGroup.BASE_GROUP)));
    public static final RegistryObject<Item> ULTIMERITE_INGOT = ITEMS.register("ultimerite_ingot",() -> new Item(new Item.Properties().tab(CustomGroup.BASE_GROUP)));

    public static final RegistryObject<Item> MANUAL_GENERATOR = ITEMS.register("manual_generator",() -> new GeneratorItem(ModBlock.MANUAL_GENERATOR.get()));
    public static final RegistryObject<Item> MODIUM_GENERATOR = ITEMS.register("modium_generator",() -> new GeneratorItem(ModBlock.MODIUM_GENERATOR.get()));
    public static final RegistryObject<Item> DERIUM_GENERATOR = ITEMS.register("derium_generator",() -> new GeneratorItem(ModBlock.DERIUM_GENERATOR.get()));
    public static final RegistryObject<Item> BLAZUM_GENERATOR = ITEMS.register("blazum_generator",() -> new GeneratorItem(ModBlock.BLAZUM_GENERATOR.get()));
    public static final RegistryObject<Item> LAVIUM_GENERATOR = ITEMS.register("lavium_generator",() -> new GeneratorItem(ModBlock.LAVIUM_GENERATOR.get()));

    public static final RegistryObject<Item> BASE_GESSENCE = ITEMS.register("base_gessence", () -> new Item(new Item.Properties().tab(CustomGroup.GENERATOR_GROUP)));
    public static final RegistryObject<Item> WOOD_GESSENCE = ITEMS.register("wood_gessence", () -> new Gessence(GessenceType.WOOD));
    public static final RegistryObject<Item> COBBLESTONE_GESSENCE = ITEMS.register("cobblestone_gessence", () -> new Gessence(GessenceType.COBBLESTONE));
    public static final RegistryObject<Item> COAL_GESSENCE = ITEMS.register("coal_gessence", () -> new Gessence(GessenceType.COAL));
    public static final RegistryObject<Item> IRON_GESSENCE = ITEMS.register("iron_gessence", () -> new Gessence(GessenceType.IRON));
    public static final RegistryObject<Item> GOLD_GESSENCE = ITEMS.register("gold_gessence", () -> new Gessence(GessenceType.GOLD));
    public static final RegistryObject<Item> REDSTONE_GESSENCE = ITEMS.register("redstone_gessence", () -> new Gessence(GessenceType.REDSTONE));
    public static final RegistryObject<Item> LAPIS_GESSENCE = ITEMS.register("lapis_gessence", () -> new Gessence(GessenceType.LAPIS));
    public static final RegistryObject<Item> DIAMOND_GESSENCE = ITEMS.register("diamond_gessence", () -> new Gessence(GessenceType.DIAMOND));
    public static final RegistryObject<Item> EMERALD_GESSENCE = ITEMS.register("emerald_gessence", () -> new Gessence(GessenceType.EMERALD));
    public static final RegistryObject<Item> NETHERITE_GESSENCE = ITEMS.register("netherite_gessence", () -> new Gessence(GessenceType.NETHERITE));
    public static final RegistryObject<Item> QUARTZ_GESSENCE = ITEMS.register("quartz_gessence", () -> new Gessence(GessenceType.QUARTZ));
    public static final RegistryObject<Item> MODIUM_GESSENCE = ITEMS.register("modium_gessence", () -> new Gessence(GessenceType.MODIUM));
    public static final RegistryObject<Item> DERIUM_GESSENCE = ITEMS.register("derium_gessence", () -> new Gessence(GessenceType.DERIUM));
    public static final RegistryObject<Item> BLAZUM_GESSENCE = ITEMS.register("blazum_gessence", () -> new Gessence(GessenceType.BLAZUM));
    public static final RegistryObject<Item> LAVIUM_GESSENCE = ITEMS.register("lavium_gessence", () -> new Gessence(GessenceType.LAVIUM));
    public static final RegistryObject<Item> ULTIMATE_GESSENCE = ITEMS.register("ultimate_gessence", () -> new Item(new Item.Properties().tab(CustomGroup.BASE_GROUP)));


}
