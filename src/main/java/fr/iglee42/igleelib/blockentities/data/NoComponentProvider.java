package fr.iglee42.igleelib.blockentities.data;

import net.minecraft.core.component.DataComponentType;

public class NoComponentProvider implements ComponentProvider<Void>{
	@Override
	public DataComponentType<Void> get() {
		return null;
	}
}
