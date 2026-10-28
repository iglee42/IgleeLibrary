package fr.iglee42.igleelib.registries.impl;

import fr.iglee42.igleelib.registries.BiDeferredRegister;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

import java.util.function.Supplier;

public class RecipeDeferredRegister extends BiDeferredRegister<RecipeType<?>, RecipeSerializer<?>> {
	public RecipeDeferredRegister(String modid) {
		super(modid, Registries.RECIPE_TYPE, Registries.RECIPE_SERIALIZER);
	}

	public <INPUT extends RecipeInput, RECIPE extends Recipe<INPUT>, SERIALIZER extends RecipeSerializer<RECIPE>> RecipeDeferredHolder<INPUT, RECIPE> register(
			String name, Supplier<SERIALIZER> serializer) {
		return registerAdvanced(name, id -> new RecipeType<RECIPE>() {
			@Override
			public String toString() {
				return id.toString();
			}
		}, (id, type) -> serializer.get(), RecipeDeferredHolder::new);
	}
}
