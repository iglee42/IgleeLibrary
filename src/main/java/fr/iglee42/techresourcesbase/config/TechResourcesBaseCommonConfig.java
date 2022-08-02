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

    static {
        BUILDER.push("Configs for TechResources Base");

        BLACKLISTED_RANDOM_ORE_DROPS = BUILDER.comment("This list is used when a player break a random ore. Example : \"minecraft:ancient_debris\"")
                        .define("blacklistedRandomOreDrops",new ArrayList<>());

        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
