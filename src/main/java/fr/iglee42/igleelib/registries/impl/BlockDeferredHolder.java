package fr.iglee42.igleelib.registries.impl;

import fr.iglee42.igleelib.registries.BiDeferredHolder;
import fr.iglee42.igleelib.registries.ExtendedDeferredHolder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class BlockDeferredHolder<BLOCK extends Block, ITEM extends Item> extends BiDeferredHolder<Block, BLOCK, Item, ITEM> implements ItemLike {
	public BlockDeferredHolder(ExtendedDeferredHolder<Block, BLOCK> primaryDH, ExtendedDeferredHolder<Item, ITEM> secondaryDH) {
		super(primaryDH, secondaryDH);
	}

	public BlockState defaultState() {
		return value().defaultBlockState();
	}

	@Override
	public @NotNull Item asItem() {
		return getSecondary();
	}

	public BlockDeferredHolder<BLOCK, ITEM> forItemHolder(Consumer<ItemDeferredHolder<ITEM>> consumer) {
		if (secondaryDH instanceof ItemDeferredHolder<ITEM> itemHolder) {
			consumer.accept(itemHolder);
			return this;
		}
		throw new IllegalStateException("Called method requires an ItemDeferredHolder");
	}

	public ExtendedDeferredHolder<Item, ITEM> getItemHolder() {
		return secondaryDH;
	}

	public ItemStack asStack() {
		return asStack(1);
	}

	public ItemStack asStack(int count) {
		return new ItemStack(asItem(), count);
	}
}
