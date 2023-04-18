package fr.iglee42.igleelib.common.init;

import fr.iglee42.igleelib.IgleeLibrary;
import fr.iglee42.igleelib.common.blocks.entity.GhostBlockEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBlockEntities {

    public static final DeferredRegister<TileEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, IgleeLibrary.MODID);

    public static final RegistryObject<TileEntityType<GhostBlockEntity>> GHOST_BLOCK = BLOCK_ENTITIES.register("ghost_block_entity",()->TileEntityType.Builder.of(GhostBlockEntity::new,ModBlock.GHOST_BLOCK.get()).build(null));

}