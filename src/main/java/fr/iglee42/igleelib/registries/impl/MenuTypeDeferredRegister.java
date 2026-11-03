package fr.iglee42.igleelib.registries.impl;

import fr.iglee42.igleelib.registries.ExtendedDeferredRegister;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.MenuType.MenuSupplier;

import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.network.IContainerFactory;

import java.util.function.Supplier;

public class MenuTypeDeferredRegister extends ExtendedDeferredRegister<MenuType<?>> {

	public MenuTypeDeferredRegister(String namespace) {
		super(Registries.MENU, namespace, MenuTypeDeferredHolder::new);
	}

	public <M extends AbstractContainerMenu> MenuTypeDeferredHolder<M> register(String name, MenuSupplier<M> factory) {
		return registerMenu(name, () -> new MenuType<>(factory, FeatureFlags.VANILLA_SET));
	}

	/**
	 * Registers a menu whose client side factory reads extra data from the buffer.
	 */
	public <M extends AbstractContainerMenu> MenuTypeDeferredHolder<M> registerWithData(String name, IContainerFactory<M> factory) {
		return registerMenu(name, () -> IMenuTypeExtension.create(factory));
	}

	@SuppressWarnings("unchecked")
	public <M extends AbstractContainerMenu> MenuTypeDeferredHolder<M> registerMenu(String name, Supplier<? extends MenuType<M>> supplier) {
		return (MenuTypeDeferredHolder<M>) super.register(name, supplier);
	}
}
