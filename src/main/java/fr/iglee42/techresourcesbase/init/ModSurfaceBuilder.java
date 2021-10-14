package fr.iglee42.techresourcesbase.init;

import fr.iglee42.techresourcesbase.TechResourcesBase;
import fr.iglee42.techresourcesbase.world.surface.MineralSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModSurfaceBuilder {

    public static final DeferredRegister<SurfaceBuilder<?>> SURFACE_BUILDERS = DeferredRegister
            .create(ForgeRegistries.SURFACE_BUILDERS, TechResourcesBase.MODID);

    public static final RegistryObject<MineralSurfaceBuilder> LIME_GRASS_SURFACE_BUILDER = SURFACE_BUILDERS.register("lime_grass",
            () -> new MineralSurfaceBuilder(SurfaceBuilderConfig.CODEC ));
}
