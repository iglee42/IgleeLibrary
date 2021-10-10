package fr.iglee42.techresourcesbase.init;

import fr.iglee42.techresourcesbase.TechResourcesBase;
import fr.iglee42.techresourcesbase.tiles.BlazumGeneratorTile;
import fr.iglee42.techresourcesbase.tiles.DeriumGeneratorTile;
import fr.iglee42.techresourcesbase.tiles.ManualGeneratorTile;
import fr.iglee42.techresourcesbase.tiles.ModiumGeneratorTile;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModTileEntity {
    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, TechResourcesBase.MODID);

    public static final RegistryObject<TileEntityType<?>> MANUAL_GENERATOR_TILE =TILE_ENTITIES.register("manual_generator_tile",() -> TileEntityType.Builder.of(ManualGeneratorTile::new,ModBlock.MANUAL_GENERATOR.get()).build(null));
    public static final RegistryObject<TileEntityType<?>> MODIUM_GENERATOR_TILE =TILE_ENTITIES.register("modium_generator_tile",() -> TileEntityType.Builder.of(ModiumGeneratorTile::new,ModBlock.MODIUM_GENERATOR.get()).build(null));
    public static final RegistryObject<TileEntityType<?>> DERIUM_GENERATOR_TILE =TILE_ENTITIES.register("derium_generator_tile",() -> TileEntityType.Builder.of(DeriumGeneratorTile::new,ModBlock.DERIUM_GENERATOR.get()).build(null));
    public static final RegistryObject<TileEntityType<?>> BLAZUM_GENERATOR_TILE =TILE_ENTITIES.register("blazum_generator_tile",() -> TileEntityType.Builder.of(BlazumGeneratorTile::new,ModBlock.BLAZUM_GENERATOR.get()).build(null));
}
