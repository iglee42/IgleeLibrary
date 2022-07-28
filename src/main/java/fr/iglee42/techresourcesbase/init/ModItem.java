package fr.iglee42.techresourcesbase.init;

import fr.iglee42.techresourcesbase.TechResourcesBase;
import fr.iglee42.techresourcesbase.items.UltimeriteArmor;
import fr.iglee42.techresourcesbase.utils.CustomArmorMaterial;
import fr.iglee42.techresourcesbase.utils.CustomGroup;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItem {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, TechResourcesBase.MODID);

    public static final RegistryObject<Item> ULTIMERITE_INGOT = ITEMS.register("ultimerite_ingot",() -> new Item(new Item.Properties().tab(CustomGroup.BASE_GROUP)));
    public static final RegistryObject<Item> ULTIMATE_GESSENCE = ITEMS.register("ultimate_gessence",() -> new Item(new Item.Properties().tab(CustomGroup.BASE_GROUP)));

    public static final RegistryObject<Item> MODIUM_INGOT = ITEMS.register("modium_ingot",() -> new Item(new Item.Properties().tab(CustomGroup.BASE_GROUP)));
    public static final RegistryObject<Item> DERIUM_INGOT = ITEMS.register("derium_ingot",() -> new Item(new Item.Properties().tab(CustomGroup.BASE_GROUP)));
    public static final RegistryObject<Item> BLAZUM_INGOT = ITEMS.register("blazum_ingot",() -> new Item(new Item.Properties().tab(CustomGroup.BASE_GROUP)));
    public static final RegistryObject<Item> LAVIUM_INGOT = ITEMS.register("lavium_ingot",() -> new Item(new Item.Properties().tab(CustomGroup.BASE_GROUP)));

    public static final RegistryObject<Item> ULTIMERITE_HELMET = ITEMS.register("ultimerite_helmet",() -> new UltimeriteArmor(CustomArmorMaterial.ULTIMERITE, EquipmentSlot.HEAD));
    public static final RegistryObject<Item> ULTIMERITE_CHESTPLATE = ITEMS.register("ultimerite_chestplate",() -> new UltimeriteArmor(CustomArmorMaterial.ULTIMERITE, EquipmentSlot.CHEST));
    public static final RegistryObject<Item> ULTIMERITE_LEGGINGS = ITEMS.register("ultimerite_leggings",() -> new UltimeriteArmor(CustomArmorMaterial.ULTIMERITE, EquipmentSlot.LEGS));
    public static final RegistryObject<Item> ULTIMERITE_BOOTS = ITEMS.register("ultimerite_boots",() -> new UltimeriteArmor(CustomArmorMaterial.ULTIMERITE, EquipmentSlot.FEET));


}
