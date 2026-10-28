package fr.iglee42.igleelib.registries.impl;

import fr.iglee42.igleelib.registries.ExtendedDeferredHolder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

import org.jetbrains.annotations.NotNull;

public class ItemDeferredHolder<T extends Item> extends ExtendedDeferredHolder<Item, T> implements ItemLike {
	public ItemDeferredHolder(ResourceKey<Item> key) {
		super(key);
	}

	@Override
	public @NotNull T asItem() {
		return value();
	}

	public ItemStack asStack() {
		return asStack(1);
	}

	public ItemStack asStack(int count) {
		return new ItemStack(value(), count);
	}
}
