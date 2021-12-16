package fr.iglee42.techresourcesbase.data.recipe;

import fr.iglee42.techresourcesbase.TechResourcesBase;
import fr.iglee42.techresourcesbase.init.ModBlock;
import fr.iglee42.techresourcesbase.init.ModItem;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.data.*;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;

import java.util.function.Consumer;

public class RecipeGenerator extends RecipeProvider {

    public RecipeGenerator(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    protected void buildShapelessRecipes(Consumer<IFinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(ModBlock.PILLAR.get())
                .pattern("BBB")
                .pattern(" M ")
                .pattern("BBB")
                .define('B',Items.POLISHED_BLACKSTONE)
                .define('M',Items.NETHERITE_INGOT)
                .unlockedBy("unlock",InventoryChangeTrigger.Instance.hasItems(Items.POLISHED_BLACKSTONE,Items.NETHERITE_INGOT))
                .save(consumer);
    }
}
