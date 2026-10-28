package fr.iglee42.igleelib.registries.impl;

import fr.iglee42.igleelib.registries.ExtendedDeferredHolder;
import fr.iglee42.igleelib.registries.ExtendedDeferredRegister;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;

import java.util.function.UnaryOperator;

public class DataComponentDeferredRegister extends ExtendedDeferredRegister<DataComponentType<?>> {
	public DataComponentDeferredRegister(String modid) {
		super(Registries.DATA_COMPONENT_TYPE, modid);
	}

	public <T> ExtendedDeferredHolder<DataComponentType<?>, DataComponentType<T>> registerComponent(String name, UnaryOperator<DataComponentType.Builder<T>> builder) {
		return register(name, () -> builder.apply(DataComponentType.builder()).build());
	}
}
