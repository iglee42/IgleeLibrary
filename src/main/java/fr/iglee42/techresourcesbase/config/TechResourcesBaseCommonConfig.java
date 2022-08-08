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
    public static final ForgeConfigSpec.ConfigValue<Integer> MAX_RANDOM_ORE_DROPS_COUNT;

    static {
        BUILDER.push("Configs for TechResources Base");

        BLACKLISTED_RANDOM_ORE_DROPS = BUILDER.comment("This list is used when a player break a random ore. Example : \"minecraft:ancient_debris\"")
                        .define("blacklistedRandomOreDrops",new ArrayList<>());
        MAX_RANDOM_ORE_DROPS_COUNT = BUILDER.comment("This int is used when a player break a random ore. Min Value: 1 ,Max Value: 64")
                        .defineInRange("maxRandomOreDropCount",4,1,64);

        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
