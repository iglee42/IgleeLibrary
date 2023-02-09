package fr.iglee42.igleelib.common.data.recipe;

import fr.iglee42.igleelib.common.init.ModBlock;
import fr.iglee42.igleelib.common.init.ModItem;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.data.*;

import java.util.function.Consumer;

public class RecipeGenerator extends RecipeProvider {

    public RecipeGenerator(DataGenerator generatorIn) {
        super(generatorIn);
    }


    @Override
    protected void buildShapelessRecipes(Consumer<IFinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(ModBlock.MODIUM_BLOCK.get())
                .pattern("III")
                .pattern("III")
                .pattern("III")
                .define('I', ModItem.MODIUM_INGOT.get())
                .unlockedBy("unlock", InventoryChangeTrigger.Instance.hasItems(ModItem.MODIUM_INGOT.get()))
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(ModItem.MODIUM_INGOT.get(),9)
                .requires(ModBlock.MODIUM_BLOCK.get())
                .unlockedBy("unlock",InventoryChangeTrigger.Instance.hasItems(ModBlock.MODIUM_BLOCK.get()))
                .save(consumer);
        ShapedRecipeBuilder.shaped(ModBlock.DERIUM_BLOCK.get())
                .pattern("III")
                .pattern("III")
                .pattern("III")
                .define('I',ModItem.DERIUM_INGOT.get())
                .unlockedBy("unlock",InventoryChangeTrigger.Instance.hasItems(ModItem.DERIUM_INGOT.get()))
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(ModItem.DERIUM_INGOT.get(),9)
                .requires(ModBlock.DERIUM_BLOCK.get())
                .unlockedBy("unlock",InventoryChangeTrigger.Instance.hasItems(ModBlock.DERIUM_BLOCK.get()))
                .save(consumer);
        ShapedRecipeBuilder.shaped(ModBlock.BLAZUM_BLOCK.get())
                .pattern("III")
                .pattern("III")
                .pattern("III")
                .define('I',ModItem.BLAZUM_INGOT.get())
                .unlockedBy("unlock",InventoryChangeTrigger.Instance.hasItems(ModItem.BLAZUM_INGOT.get()))
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(ModItem.BLAZUM_INGOT.get(),9)
                .requires(ModBlock.BLAZUM_BLOCK.get())
                .unlockedBy("unlock", InventoryChangeTrigger.Instance.hasItems(ModBlock.BLAZUM_BLOCK.get()))
                .save(consumer);
        ShapedRecipeBuilder.shaped(ModBlock.LAVIUM_BLOCK.get())
                .pattern("III")
                .pattern("III")
                .pattern("III")
                .define('I',ModItem.LAVIUM_INGOT.get())
                .unlockedBy("unlock",InventoryChangeTrigger.Instance.hasItems(ModItem.LAVIUM_INGOT.get()))
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(ModItem.LAVIUM_INGOT.get(),9)
                .requires(ModBlock.LAVIUM_BLOCK.get())
                .unlockedBy("unlock",InventoryChangeTrigger.Instance.hasItems(ModBlock.LAVIUM_BLOCK.get()))
                .save(consumer);
    }
}
