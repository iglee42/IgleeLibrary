package fr.iglee42.igleelib.registries.impl;

import fr.iglee42.igleelib.registries.ExtendedDeferredHolder;
import fr.iglee42.igleelib.registries.ExtendedDeferredRegister;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

public class SoundDeferredRegister extends ExtendedDeferredRegister<SoundEvent> {
	public SoundDeferredRegister(String modid) {
		super(Registries.SOUND_EVENT, modid);
	}

	/** Registers a sound event whose id matches its registry name. */
	public ExtendedDeferredHolder<SoundEvent, SoundEvent> registerSound(String name) {
		return register(name, id -> SoundEvent.createVariableRangeEvent(id));
	}

	public ExtendedDeferredHolder<SoundEvent, SoundEvent> registerFixedRange(String name, float range) {
		return register(name, (ResourceLocation id) -> SoundEvent.createFixedRangeEvent(id, range));
	}
}
