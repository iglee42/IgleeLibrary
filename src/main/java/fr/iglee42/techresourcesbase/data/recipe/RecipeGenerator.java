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
        ShapedRecipeBuilder.shaped(ModItem.WOOD_GESSENCE.get())
                .pattern("CCC")
                .pattern("LPL")
                .pattern("CCC")
                .define('C',Items.COBBLESTONE)
                .define('P',Items.STONE_PICKAXE)
                .define('L', Items.OAK_LOG)
                .unlockedBy("unlock",InventoryChangeTrigger.Instance.hasItems(Items.COBBLESTONE,Items.STONE_PICKAXE,Items.OAK_LOG))
                .save(consumer);
        ShapedRecipeBuilder.shaped(ModItem.COBBLESTONE_GESSENCE.get())
                .pattern("CCC")
                .pattern("CWC")
                .pattern("CCC")
                .define('C',Items.COBBLESTONE)
                .define('W',ModItem.WOOD_GESSENCE.get())
                .unlockedBy("unlock",InventoryChangeTrigger.Instance.hasItems(Items.COBBLESTONE,ModItem.WOOD_GESSENCE.get()))
                .save(consumer);
    }
}
