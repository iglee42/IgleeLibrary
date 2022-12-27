package fr.iglee42.techresourcesbase.data.recipe;

import fr.iglee42.techresourcesbase.init.ModBlock;
import fr.iglee42.techresourcesbase.init.ModItem;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;

import java.util.function.Consumer;

public class RecipeGenerator extends RecipeProvider {

    public RecipeGenerator(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(ModBlock.MODIUM_BLOCK.get())
                .pattern("III")
                .pattern("III")
                .pattern("III")
                .define('I',ModItem.MODIUM_INGOT.get())
                .unlockedBy("unlock",InventoryChangeTrigger.TriggerInstance.hasItems(ModItem.MODIUM_INGOT.get()))
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(ModItem.MODIUM_INGOT.get(),9)
                .requires(ModBlock.MODIUM_BLOCK.get())
                .unlockedBy("unlock",InventoryChangeTrigger.TriggerInstance.hasItems(ModBlock.MODIUM_BLOCK.get()))
                .save(consumer);
        ShapedRecipeBuilder.shaped(ModBlock.DERIUM_BLOCK.get())
                .pattern("III")
                .pattern("III")
                .pattern("III")
                .define('I',ModItem.DERIUM_INGOT.get())
                .unlockedBy("unlock",InventoryChangeTrigger.TriggerInstance.hasItems(ModItem.DERIUM_INGOT.get()))
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(ModItem.DERIUM_INGOT.get(),9)
                .requires(ModBlock.DERIUM_BLOCK.get())
                .unlockedBy("unlock",InventoryChangeTrigger.TriggerInstance.hasItems(ModBlock.DERIUM_BLOCK.get()))
                .save(consumer);
        ShapedRecipeBuilder.shaped(ModBlock.BLAZUM_BLOCK.get())
                .pattern("III")
                .pattern("III")
                .pattern("III")
                .define('I',ModItem.BLAZUM_INGOT.get())
                .unlockedBy("unlock",InventoryChangeTrigger.TriggerInstance.hasItems(ModItem.BLAZUM_INGOT.get()))
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(ModItem.BLAZUM_INGOT.get(),9)
                .requires(ModBlock.BLAZUM_BLOCK.get())
                .unlockedBy("unlock", InventoryChangeTrigger.TriggerInstance.hasItems(ModBlock.BLAZUM_BLOCK.get()))
                .save(consumer);
        ShapedRecipeBuilder.shaped(ModBlock.LAVIUM_BLOCK.get())
                .pattern("III")
                .pattern("III")
                .pattern("III")
                .define('I',ModItem.LAVIUM_INGOT.get())
                .unlockedBy("unlock",InventoryChangeTrigger.TriggerInstance.hasItems(ModItem.LAVIUM_INGOT.get()))
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(ModItem.LAVIUM_INGOT.get(),9)
                .requires(ModBlock.LAVIUM_BLOCK.get())
                .unlockedBy("unlock",InventoryChangeTrigger.TriggerInstance.hasItems(ModBlock.LAVIUM_BLOCK.get()))
                .save(consumer);
    }
}
