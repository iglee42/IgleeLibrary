package fr.iglee42.igleelib.registries.impl;

import fr.iglee42.igleelib.registries.BiDeferredHolder;
import fr.iglee42.igleelib.registries.ExtendedDeferredHolder;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

public class RecipeDeferredHolder<INPUT extends RecipeInput, RECIPE extends Recipe<INPUT>> extends BiDeferredHolder<RecipeType<?>, RecipeType<RECIPE>, RecipeSerializer<?>, RecipeSerializer<RECIPE>> {
	public RecipeDeferredHolder(ExtendedDeferredHolder<RecipeType<?>, RecipeType<RECIPE>> primaryDH, ExtendedDeferredHolder<RecipeSerializer<?>, RecipeSerializer<RECIPE>> secondaryDH) {
		super(primaryDH, secondaryDH);
	}

	public RecipeType<RECIPE> getType() {
		return get();
	}

	public RecipeSerializer<RECIPE> getSerializer() {
		return getSecondary();
	}
}
