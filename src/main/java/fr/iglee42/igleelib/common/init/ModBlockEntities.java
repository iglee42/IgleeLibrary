package fr.iglee42.igleelib.common.init;

import fr.iglee42.igleelib.IgleeLibrary;
import fr.iglee42.igleelib.common.baseutils.CustomGroup;
import fr.iglee42.igleelib.common.blocks.GhostBlock;
import fr.iglee42.igleelib.common.blocks.entity.GhostBlockEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, IgleeLibrary.MODID);

    public static final RegistryObject<BlockEntityType<GhostBlockEntity>> GHOST_BLOCK = BLOCK_ENTITIES.register("ghost_block_entity",()->BlockEntityType.Builder.of(GhostBlockEntity::new,ModBlock.GHOST_BLOCK.get()).build(null));

}