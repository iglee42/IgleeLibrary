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
import net.minecraftforge.common.Tags;

import java.util.function.Consumer;

public class RecipeGenerator extends RecipeProvider {

    public RecipeGenerator(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    protected void buildShapelessRecipes(Consumer<IFinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(ModItem.QUARTZ_GESSENCE.get())
                .pattern("SNS")
                .pattern("NBN")
                .pattern("SNS")
                .define('S',Items.SOUL_SAND)
                .define('N',Items.NETHERITE_INGOT)
                .define('B', ModItem.BASE_GESSENCE.get())
                .unlockedBy("unlock",InventoryChangeTrigger.Instance.hasItems(Items.SOUL_SAND,Items.NETHERITE_INGOT,ModItem.BASE_GESSENCE.get()))
                .save(consumer);
    }
}
