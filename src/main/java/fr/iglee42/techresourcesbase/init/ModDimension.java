package fr.iglee42.techresourcesbase.init;

import fr.iglee42.techresourcesbase.TechResourcesBase;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.Dimension;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraftforge.registries.DeferredRegister;


public class ModDimension {

    public static final RegistryKey<World> MINERAL_DIMENSION_WORLD = RegistryKey.create(Registry.DIMENSION_REGISTRY,new ResourceLocation(TechResourcesBase.MODID,"mineral_dimension"));
}
