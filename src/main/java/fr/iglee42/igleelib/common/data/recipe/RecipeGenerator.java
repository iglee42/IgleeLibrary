package fr.iglee42.igleelib.common.data.recipe;

import fr.iglee42.igleelib.common.init.ModBlock;
import fr.iglee42.igleelib.common.init.ModItem;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;

import java.util.function.Consumer;

public class RecipeGenerator extends RecipeProvider {


    public RecipeGenerator(PackOutput p_248933_) {
        super(p_248933_);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS,ModBlock.MODIUM_BLOCK.get())
                .pattern("III")
                .pattern("III")
                .pattern("III")
                .define('I', ModItem.MODIUM_INGOT.get())
                .unlockedBy("unlock",InventoryChangeTrigger.TriggerInstance.hasItems(ModItem.MODIUM_INGOT.get()))
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,ModItem.MODIUM_INGOT.get(),9)
                .requires(ModBlock.MODIUM_BLOCK.get())
                .unlockedBy("unlock",InventoryChangeTrigger.TriggerInstance.hasItems(ModBlock.MODIUM_BLOCK.get()))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS,ModBlock.DERIUM_BLOCK.get())
                .pattern("III")
                .pattern("III")
                .pattern("III")
                .define('I',ModItem.DERIUM_INGOT.get())
                .unlockedBy("unlock",InventoryChangeTrigger.TriggerInstance.hasItems(ModItem.DERIUM_INGOT.get()))
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,ModItem.DERIUM_INGOT.get(),9)
                .requires(ModBlock.DERIUM_BLOCK.get())
                .unlockedBy("unlock",InventoryChangeTrigger.TriggerInstance.hasItems(ModBlock.DERIUM_BLOCK.get()))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS,ModBlock.BLAZUM_BLOCK.get())
                .pattern("III")
                .pattern("III")
                .pattern("III")
                .define('I',ModItem.BLAZUM_INGOT.get())
                .unlockedBy("unlock",InventoryChangeTrigger.TriggerInstance.hasItems(ModItem.BLAZUM_INGOT.get()))
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,ModItem.BLAZUM_INGOT.get(),9)
                .requires(ModBlock.BLAZUM_BLOCK.get())
                .unlockedBy("unlock", InventoryChangeTrigger.TriggerInstance.hasItems(ModBlock.BLAZUM_BLOCK.get()))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS,ModBlock.LAVIUM_BLOCK.get())
                .pattern("III")
                .pattern("III")
                .pattern("III")
                .define('I',ModItem.LAVIUM_INGOT.get())
                .unlockedBy("unlock",InventoryChangeTrigger.TriggerInstance.hasItems(ModItem.LAVIUM_INGOT.get()))
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,ModItem.LAVIUM_INGOT.get(),9)
                .requires(ModBlock.LAVIUM_BLOCK.get())
                .unlockedBy("unlock",InventoryChangeTrigger.TriggerInstance.hasItems(ModBlock.LAVIUM_BLOCK.get()))
                .save(consumer);
    }
}
