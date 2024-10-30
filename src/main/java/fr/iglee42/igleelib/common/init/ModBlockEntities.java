package fr.iglee42.igleelib.common.init;

import fr.iglee42.igleelib.IgleeLibrary;
import fr.iglee42.igleelib.common.blocks.entity.GhostBlockEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, IgleeLibrary.MODID);

    public static final DeferredHolder<BlockEntityType<?>,BlockEntityType<GhostBlockEntity>> GHOST_BLOCK = BLOCK_ENTITIES.register("ghost_block_entity",()->new BlockEntityType<>(GhostBlockEntity::new,ModBlock.GHOST_BLOCK.get()));

}