package fr.iglee42.igleelib.registries.impl;

import fr.iglee42.igleelib.registries.ExtendedDeferredRegister;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;

import org.jetbrains.annotations.NotNull;

import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class ItemDeferredRegister extends ExtendedDeferredRegister<Item> {
	public ItemDeferredRegister(String namespace) {
		super(Registries.ITEM, namespace, ItemDeferredHolder::new);
	}

	public ItemDeferredHolder<Item> register(String name) {
		return registerItem(name, Item::new, UnaryOperator.identity());
	}

	public ItemDeferredHolder<Item> register(String name, UnaryOperator<Properties> propertiesModifier) {
		return registerItem(name, Item::new, propertiesModifier);
	}

	public <T extends Item> ItemDeferredHolder<T> registerItem(String name, Function<Properties, T> factory) {
		return registerItem(name, factory, UnaryOperator.identity());
	}

	public <T extends Item> ItemDeferredHolder<T> registerItem(String name, Function<Properties, T> factory, UnaryOperator<Properties> propertiesModifier) {
		return register(name, () -> factory.apply(propertiesModifier.apply(new Properties())));
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T extends Item> @NotNull ItemDeferredHolder<T> register(@NotNull String name, @NotNull Supplier<? extends T> sup) {
		return (ItemDeferredHolder<T>) super.register(name, sup);
	}
}
