package fr.iglee42.igleelib.registries;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import net.neoforged.neoforge.registries.DeferredRegister;

import org.jetbrains.annotations.NotNull;

import java.util.function.Function;
import java.util.function.Supplier;

@SuppressWarnings("unchecked")
public class ExtendedDeferredRegister<T> extends DeferredRegister<T> {

	private final Function<ResourceKey<T>, ? extends ExtendedDeferredHolder<T, ?>> holderConstructor;

	public static <T> ExtendedDeferredRegister<T> create(ResourceKey<? extends Registry<T>> registryKey, String namespace) {
		return new ExtendedDeferredRegister<>(registryKey, namespace);
	}

	public ExtendedDeferredRegister(ResourceKey<? extends Registry<T>> registryKey, String namespace) {
		this(registryKey, namespace, ExtendedDeferredHolder::new);
	}

	public ExtendedDeferredRegister(ResourceKey<? extends Registry<T>> registryKey, String namespace, Function<ResourceKey<T>, ? extends ExtendedDeferredHolder<T, ?>> holderConstructor) {
		super(registryKey, namespace);
		this.holderConstructor = holderConstructor;
	}

	@Override
	public <I extends T> @NotNull ExtendedDeferredHolder<T, I> register(@NotNull String name, @NotNull Supplier<? extends I> sup) {
		return (ExtendedDeferredHolder<T, I>) super.register(name, sup);
	}

	@Override
	public <I extends T> @NotNull ExtendedDeferredHolder<T, I> register(@NotNull String name, @NotNull Function<ResourceLocation, ? extends I> func) {
		return (ExtendedDeferredHolder<T, I>) super.register(name, func);
	}

	@Override
	protected <I extends T> @NotNull ExtendedDeferredHolder<T, I> createHolder(@NotNull ResourceKey<? extends Registry<T>> registryKey, @NotNull ResourceLocation key) {
		return (ExtendedDeferredHolder<T, I>) holderConstructor.apply(ResourceKey.create(registryKey, key));
	}
}
