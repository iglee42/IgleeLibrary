package fr.iglee42.techresourcesbase.common.init;

import fr.iglee42.techresourcesbase.TechResourcesBase;
import fr.iglee42.techresourcesbase.common.baseutils.CustomGroup;
import fr.iglee42.techresourcesbase.common.blocks.RandomOre;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class ModBlock {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, TechResourcesBase.MODID);

    public static final RegistryObject<Block> RANDOM_ORE = createBlock("random_ore",()-> new RandomOre(AbstractBlock.Properties.copy(Blocks.DIAMOND_ORE)));

    public static final RegistryObject<Block> MODIUM_BLOCK = createBlock("modium_block", ()-> new Block(AbstractBlock.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> DERIUM_BLOCK = createBlock("derium_block", ()-> new Block(AbstractBlock.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> BLAZUM_BLOCK = createBlock("blazum_block", ()-> new Block(AbstractBlock.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> LAVIUM_BLOCK = createBlock("lavium_block", ()-> new Block(AbstractBlock.Properties.copy(Blocks.IRON_BLOCK)));


    public static RegistryObject<Block> createBlock(String name, Supplier<? extends Block> supplier)
    {
        RegistryObject<Block> block = BLOCKS.register(name, supplier);
        ModItem.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(CustomGroup.BASE_GROUP)));
        return block;
    }
    public static RegistryObject<Block> createBlockWithoutItem(String name, Supplier<? extends Block> supplier)
    {
        RegistryObject<Block> block = BLOCKS.register(name, supplier);
        return block;
    }
}
