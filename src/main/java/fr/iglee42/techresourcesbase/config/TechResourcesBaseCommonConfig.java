package fr.iglee42.techresourcesbase.config;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.ArrayList;
import java.util.List;

public class TechResourcesBaseCommonConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.ConfigValue<List<String>> BLACKLISTED_RANDOM_ORE_DROPS;
    public static final ForgeConfigSpec.ConfigValue<List<String>> CUSTOM_RANDOM_ORE_DROPS;
    public static final ForgeConfigSpec.ConfigValue<Integer> MAX_RANDOM_ORE_DROPS_COUNT;
    public static final ForgeConfigSpec.ConfigValue<Boolean> RANDOM_ORE_CAN_DROP_COBBLESTONE;
    public static final ForgeConfigSpec.ConfigValue<Boolean> RANDOM_ORE_CAN_DROP_MINERALS;

    static {
        BUILDER.push("Configs for TechResources Base");

        BLACKLISTED_RANDOM_ORE_DROPS = BUILDER.comment("This list can be use to remove drop to random ore. Example : \"minecraft:ancient_debris\"")
                        .define("blacklistedRandomOreDrops",new ArrayList<>());
        CUSTOM_RANDOM_ORE_DROPS = BUILDER.comment("This list can be use to add custom drop to random ore. Example : \"minecraft:oak_log\"")
                        .define("blacklistedRandomOreDrops",new ArrayList<>());
        MAX_RANDOM_ORE_DROPS_COUNT = BUILDER.comment("This int can be use to change the maxiumum count of minerals can be drops. Min Value: 1 ,Max Value: 64")
                        .defineInRange("maxRandomOreDropCount",4,1,64);
        RANDOM_ORE_CAN_DROP_COBBLESTONE = BUILDER.comment("This boolean can be use to disable the cobblestone drop from the random ore")
                        .define("randomOreEnableCobblestone",true);
        RANDOM_ORE_CAN_DROP_MINERALS = BUILDER.comment("This boolean can be use to disable the minerals drop from the random ore (All items with tag forge:ores)")
                        .define("randomOreEnableMinerals",true);

        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
