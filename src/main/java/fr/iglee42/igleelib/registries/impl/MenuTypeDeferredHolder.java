package fr.iglee42.igleelib.registries.impl;

import fr.iglee42.igleelib.registries.ExtendedDeferredHolder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuConstructor;
import net.minecraft.world.inventory.MenuType;

import java.util.function.Consumer;

public class MenuTypeDeferredHolder<T extends AbstractContainerMenu> extends ExtendedDeferredHolder<MenuType<?>, MenuType<T>> {

	public MenuTypeDeferredHolder(ResourceKey<MenuType<?>> key) {
		super(key);
	}

	public MenuProvider getProvider(Component name, MenuConstructor constructor) {
		return new SimpleMenuProvider(constructor, name);
	}

	/**
	 * Opens the menu for the player, writing extra data for the client side menu factory.
	 */
	public void tryOpenGui(ServerPlayer player, Component name, MenuConstructor constructor, Consumer<RegistryFriendlyByteBuf> extraData) {
		player.openMenu(getProvider(name, constructor), extraData);
	}
}
