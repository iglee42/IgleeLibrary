package fr.iglee42.igleelib.registries.impl;

import fr.iglee42.igleelib.registries.BiDeferredRegister;
import fr.iglee42.igleelib.registries.ExtendedDeferredHolder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.UnaryOperator;

public class BlockDeferredRegister extends BiDeferredRegister<Block, Item> {
	public BlockDeferredRegister(String modid) {
		super(modid, Registries.BLOCK, new ItemDeferredRegister(modid));
	}

	public <BLOCK extends Block> BlockDeferredHolder<BLOCK, BlockItem> register(String name, Function<Properties, ? extends BLOCK> blockFactory) {
		return register(name, blockFactory, UnaryOperator.identity(), BlockItem::new);
	}

	public <BLOCK extends Block> BlockDeferredHolder<BLOCK, BlockItem> register(String name, Function<Properties, ? extends BLOCK> blockFactory, UnaryOperator<Properties> propertiesModifier) {
		return register(name, blockFactory, propertiesModifier, BlockItem::new);
	}

	public <BLOCK extends Block, ITEM extends BlockItem> BlockDeferredHolder<BLOCK, ITEM> register(String name, Function<Properties, ? extends BLOCK> blockFactory, BiFunction<BLOCK, Item.Properties, ITEM> itemCreator) {
		return register(name, blockFactory, UnaryOperator.identity(), itemCreator);
	}

	public BlockDeferredHolder<Block, BlockItem> registerSimple(String name, UnaryOperator<Properties> propertiesModifier) {
		return register(name, Block::new, propertiesModifier, BlockItem::new);
	}

	public BlockDeferredHolder<Block, BlockItem> registerSimple(String name, Properties properties) {
		return registerSimple(name, p -> properties);
	}

	public <BLOCK extends Block, ITEM extends BlockItem> BlockDeferredHolder<BLOCK, ITEM> register(String name, Function<Properties, ? extends BLOCK> blockFactory,
	                                                                                               UnaryOperator<Properties> propertiesModifier,
	                                                                                               BiFunction<BLOCK, Item.Properties, ITEM> itemCreator) {
		return registerAdvanced(name,
				id -> blockFactory.apply(propertiesModifier.apply(Properties.of())),
				(id, block) -> itemCreator.apply(block.get(), new Item.Properties()),
				BlockDeferredHolder::new);
	}

	/** Registers a block without any item. */
	public <BLOCK extends Block> ExtendedDeferredHolder<Block, BLOCK> registerNoItem(String name, Function<Properties, ? extends BLOCK> blockFactory, UnaryOperator<Properties> propertiesModifier) {
		return getPrimaryRegister().register(name, () -> blockFactory.apply(propertiesModifier.apply(Properties.of())));
	}
}
