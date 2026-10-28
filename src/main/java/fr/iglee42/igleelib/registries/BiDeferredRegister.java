package fr.iglee42.igleelib.registries;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.Collection;
import java.util.function.BiFunction;
import java.util.function.Function;

public class BiDeferredRegister<PRIMARY, SECONDARY> {

	private final ExtendedDeferredRegister<PRIMARY> primaryRegistry;
	private final ExtendedDeferredRegister<SECONDARY> secondaryRegistry;

	protected BiDeferredRegister(ExtendedDeferredRegister<PRIMARY> primaryRegistry, ExtendedDeferredRegister<SECONDARY> secondaryRegistry) {
		this.primaryRegistry = primaryRegistry;
		this.secondaryRegistry = secondaryRegistry;
	}

	protected BiDeferredRegister(String modid, ResourceKey<? extends Registry<PRIMARY>> primaryRegistryName,
	                             ResourceKey<? extends Registry<SECONDARY>> secondaryRegistryName) {
		this(modid, primaryRegistryName, new ExtendedDeferredRegister<>(secondaryRegistryName, modid));
	}

	protected BiDeferredRegister(String modid, ResourceKey<? extends Registry<PRIMARY>> primaryRegistryName, ExtendedDeferredRegister<SECONDARY> secondaryRegistry) {
		this(new ExtendedDeferredRegister<>(primaryRegistryName, modid), secondaryRegistry);
	}

	public <P extends PRIMARY, S extends SECONDARY, W extends BiDeferredHolder<PRIMARY, P, SECONDARY, S>> W register(String name,
			Function<ResourceLocation, ? extends P> primaryFunction, Function<ResourceLocation, ? extends S> secondaryFunction,
			BiFunction<ExtendedDeferredHolder<PRIMARY, P>, ExtendedDeferredHolder<SECONDARY, S>, W> wrapper) {
		return wrapper.apply(primaryRegistry.register(name, primaryFunction), secondaryRegistry.register(name, secondaryFunction));
	}

	public <P extends PRIMARY, S extends SECONDARY, W extends BiDeferredHolder<PRIMARY, P, SECONDARY, S>> W register(String name,
			Function<ResourceLocation, ? extends P> primaryFunction, BiFunction<ResourceLocation, P, S> secondaryFunction,
			BiFunction<ExtendedDeferredHolder<PRIMARY, P>, ExtendedDeferredHolder<SECONDARY, S>, W> wrapper) {
		return registerAdvanced(name, primaryFunction, (id, rh) -> secondaryFunction.apply(id, rh.get()), wrapper);
	}

	public <P extends PRIMARY, S extends SECONDARY, W extends BiDeferredHolder<PRIMARY, P, SECONDARY, S>> W registerAdvanced(String name,
			Function<ResourceLocation, ? extends P> primaryFunction,
			BiFunction<ResourceLocation, ExtendedDeferredHolder<PRIMARY, P>, S> secondaryFunction,
			BiFunction<ExtendedDeferredHolder<PRIMARY, P>, ExtendedDeferredHolder<SECONDARY, S>, W> wrapper) {
		ExtendedDeferredHolder<PRIMARY, P> primaryDH = primaryRegistry.register(name, primaryFunction);
		return wrapper.apply(primaryDH, secondaryRegistry.register(name, id -> secondaryFunction.apply(id, primaryDH)));
	}

	public void register(IEventBus bus) {
		primaryRegistry.register(bus);
		secondaryRegistry.register(bus);
	}

	protected ExtendedDeferredRegister<PRIMARY> getPrimaryRegister() {
		return primaryRegistry;
	}

	protected ExtendedDeferredRegister<SECONDARY> getSecondaryRegister() {
		return secondaryRegistry;
	}

	public String getNamespace() {
		return primaryRegistry.getNamespace();
	}

	public Collection<DeferredHolder<PRIMARY, ? extends PRIMARY>> getPrimaryEntries() {
		return primaryRegistry.getEntries();
	}

	public Collection<DeferredHolder<SECONDARY, ? extends SECONDARY>> getSecondaryEntries() {
		return secondaryRegistry.getEntries();
	}
}
