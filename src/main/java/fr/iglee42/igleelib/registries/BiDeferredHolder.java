package fr.iglee42.igleelib.registries;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;

public class BiDeferredHolder<PRIMARY_TYPE, PRIMARY extends PRIMARY_TYPE, SECONDARY_TYPE, SECONDARY extends SECONDARY_TYPE> extends ExtendedDeferredHolder<PRIMARY_TYPE, PRIMARY> {

	protected final ExtendedDeferredHolder<SECONDARY_TYPE, SECONDARY> secondaryDH;

	public BiDeferredHolder(ExtendedDeferredHolder<PRIMARY_TYPE, PRIMARY> primaryDH, ExtendedDeferredHolder<SECONDARY_TYPE, SECONDARY> secondaryDH) {
		this(primaryDH.getKey(), secondaryDH);
	}

	public BiDeferredHolder(ResourceKey<PRIMARY_TYPE> key, ExtendedDeferredHolder<SECONDARY_TYPE, SECONDARY> secondaryDH) {
		super(key);
		this.secondaryDH = secondaryDH;
	}

	public SECONDARY getSecondary() {
		return secondaryDH.get();
	}

	public ExtendedDeferredHolder<SECONDARY_TYPE, SECONDARY> getSecondaryHolder() {
		return secondaryDH;
	}

	public boolean secondaryKeyMatches(Holder<SECONDARY_TYPE> holder) {
		return holder.is(secondaryDH.getKey());
	}

	public boolean isSecondary(SECONDARY_TYPE other) {
		return getSecondary() == other;
	}
}
