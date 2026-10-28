package fr.iglee42.igleelib.registries;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import net.neoforged.neoforge.registries.DeferredHolder;

public class ExtendedDeferredHolder<R, T extends R> extends DeferredHolder<R, T> {

	public ExtendedDeferredHolder(ResourceKey<? extends Registry<R>> registry, ResourceLocation id) {
		super(ResourceKey.create(registry, id));
	}

	public ExtendedDeferredHolder(ResourceKey<R> key) {
		super(key);
	}

	public boolean is(R other) {
		return get() == other;
	}

	public boolean keyMatches(Holder<R> holder) {
		return holder.is(getKey());
	}

	public String getPath() {
		return getId().getPath();
	}
}
