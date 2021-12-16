package fr.iglee42.techresourcesbase.init;

import fr.iglee42.techresourcesbase.TechResourcesBase;
import fr.iglee42.techresourcesbase.blocks.*;
import fr.iglee42.techresourcesbase.blocks.generator.manual.*;
import fr.iglee42.techresourcesbase.utils.CustomGroup;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class ModBlock {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, TechResourcesBase.MODID);

    public static final RegistryObject<Block> MANUAL_GENERATOR = createBlockWithoutItem("manual_generator", ManualGenerator::new);
    public static final RegistryObject<Block> MODIUM_GENERATOR = createBlockWithoutItem("modium_generator", ModiumGenerator::new);
    public static final RegistryObject<Block> DERIUM_GENERATOR = createBlockWithoutItem("derium_generator", DeriumGenerator::new);
    public static final RegistryObject<Block> BLAZUM_GENERATOR = createBlockWithoutItem("blazum_generator", BlazumGenerator::new);
    public static final RegistryObject<Block> LAVIUM_GENERATOR = createBlockWithoutItem("lavium_generator", LaviumGenerator::new);

    public static final RegistryObject<Block> LIME_GRASS = createBlock("lime_grass",()-> new LimeGrassBlock(AbstractBlock.Properties.of(Material.GRASS)));

    public static final RegistryObject<Block> RANDOM_ORE = createBlock("random_ore",()-> new Block(AbstractBlock.Properties.of(Material.METAL)));

    public static final RegistryObject<Block> PILLAR = createBlock("pillar",()-> new PillarBlock(AbstractBlock.Properties.of(Material.METAL)));

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
