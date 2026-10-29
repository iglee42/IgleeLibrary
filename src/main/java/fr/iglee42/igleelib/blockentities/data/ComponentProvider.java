package fr.iglee42.igleelib.blockentities.data;

import net.minecraft.core.component.DataComponentType;

public interface ComponentProvider<T> {

	DataComponentType<T> get();

}
