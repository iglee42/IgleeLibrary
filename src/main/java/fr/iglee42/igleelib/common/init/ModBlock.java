package fr.iglee42.igleelib.common.init;

import fr.iglee42.igleelib.IgleeLibrary;
import fr.iglee42.igleelib.common.blocks.GhostBlock;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlock {

    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(IgleeLibrary.MODID);

    //public static final RegistryObject<Block> RANDOM_ORE = createBlock("random_ore",()-> new RandomOre(BlockBehaviour.Properties.copy(Blocks.DIAMOND_ORE)));

    public static final DeferredBlock<Block> MODIUM_BLOCK = createBlock("modium_block", ()-> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK)));
    public static final DeferredBlock<Block> DERIUM_BLOCK = createBlock("derium_block", ()-> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK)));
    public static final DeferredBlock<Block> BLAZUM_BLOCK = createBlock("blazum_block", ()-> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK)));
    public static final DeferredBlock<Block> LAVIUM_BLOCK = createBlock("lavium_block", ()-> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK)));

    public static final DeferredBlock<Block> GHOST_BLOCK = createBlockWithoutItem("ghost_block", GhostBlock::new);
    public static DeferredBlock<Block> createBlock(String name, Supplier<? extends Block> supplier)
    {
        DeferredBlock<Block> block = BLOCKS.register(name, supplier);
        ModItem.ITEMS.registerSimpleBlockItem(block);
        return block;
    }
    public static DeferredBlock<Block> createBlockWithoutItem(String name, Supplier<? extends Block> supplier)
    {
        DeferredBlock<Block> block = BLOCKS.register(name, supplier);
        return block;
    }
}
