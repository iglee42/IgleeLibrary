package fr.iglee42.techresourcesbase.data.recipe;

import fr.iglee42.techresourcesbase.init.ModItem;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.item.Items;

import java.util.function.Consumer;

public class RecipeGenerator extends RecipeProvider {

    public RecipeGenerator(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    protected void buildShapelessRecipes(Consumer<IFinishedRecipe> consumer) {

        ShapedRecipeBuilder.shaped(ModItem.COAL_GESSENCE.get())
                .pattern("CCC")
                .pattern("CWC")
                .pattern("CCC")
                .define('C',Items.COBBLESTONE)
                .define('W', Items.OAK_LOG)
                .unlockedBy("unlock",InventoryChangeTrigger.Instance.hasItems(Items.COBBLESTONE,Items.OAK_LOG))
                .save(consumer);
        ShapedRecipeBuilder.shaped(ModItem.IRON_GESSENCE.get())
                .pattern("CCC")
                .pattern("CGC")
                .pattern("CCC")
                .define('C',Items.COBBLESTONE)
                .define('G',ModItem.COAL_GESSENCE.get())
                .unlockedBy("unlock",InventoryChangeTrigger.Instance.hasItems(Items.COBBLESTONE,ModItem.COAL_GESSENCE.get()))
                .save(consumer);
        ShapedRecipeBuilder.shaped(ModItem.GOLD_GESSENCE.get())
                .pattern("CCC")
                .pattern("GIG")
                .pattern("CCC")
                .define('C',Items.COBBLESTONE)
                .define('G',ModItem.COAL_GESSENCE.get())
                .define('I',ModItem.IRON_GESSENCE.get())
                .unlockedBy("unlock",InventoryChangeTrigger.Instance.hasItems(Items.COBBLESTONE,ModItem.COAL_GESSENCE.get(),ModItem.IRON_GESSENCE.get()))
                .save(consumer);
        ShapedRecipeBuilder.shaped(ModItem.REDSTONE_GESSENCE.get())
                .pattern("CCC")
                .pattern("IGI")
                .pattern("CCC")
                .define('C',Items.COBBLESTONE)
                .define('G',ModItem.GOLD_GESSENCE.get())
                .define('I',ModItem.IRON_GESSENCE.get())
                .unlockedBy("unlock",InventoryChangeTrigger.Instance.hasItems(Items.COBBLESTONE,ModItem.GOLD_GESSENCE.get(),ModItem.IRON_GESSENCE.get()))
                .save(consumer);
        ShapedRecipeBuilder.shaped(ModItem.LAPIS_GESSENCE.get())
                .pattern("CCC")
                .pattern("GRG")
                .pattern("CCC")
                .define('C',Items.COBBLESTONE)
                .define('G',ModItem.GOLD_GESSENCE.get())
                .define('R',ModItem.REDSTONE_GESSENCE.get())
                .unlockedBy("unlock",InventoryChangeTrigger.Instance.hasItems(Items.COBBLESTONE,ModItem.GOLD_GESSENCE.get(),ModItem.REDSTONE_GESSENCE.get()))
                .save(consumer);
        ShapedRecipeBuilder.shaped(ModItem.DIAMOND_GESSENCE.get())
                .pattern("CCC")
                .pattern("RLR")
                .pattern("CCC")
                .define('C',Items.COBBLESTONE)
                .define('L',ModItem.LAPIS_GESSENCE.get())
                .define('R',ModItem.REDSTONE_GESSENCE.get())
                .unlockedBy("unlock",InventoryChangeTrigger.Instance.hasItems(Items.COBBLESTONE,ModItem.REDSTONE_GESSENCE.get(),ModItem.LAPIS_GESSENCE.get()))
                .save(consumer);
        ShapedRecipeBuilder.shaped(ModItem.EMERALD_GESSENCE.get())
                .pattern("CCC")
                .pattern("LDL")
                .pattern("CCC")
                .define('C',Items.COBBLESTONE)
                .define('L',ModItem.LAPIS_GESSENCE.get())
                .define('D',ModItem.DIAMOND_GESSENCE.get())
                .unlockedBy("unlock",InventoryChangeTrigger.Instance.hasItems(Items.COBBLESTONE,ModItem.DIAMOND_GESSENCE.get(),ModItem.LAPIS_GESSENCE.get()))
                .save(consumer);
        ShapedRecipeBuilder.shaped(ModItem.NETHERITE_GESSENCE.get())
                .pattern("CCC")
                .pattern("DMD")
                .pattern("CCC")
                .define('C',Items.COBBLESTONE)
                .define('M',ModItem.EMERALD_GESSENCE.get())
                .define('D',ModItem.DIAMOND_GESSENCE.get())
                .unlockedBy("unlock",InventoryChangeTrigger.Instance.hasItems(Items.COBBLESTONE,ModItem.DIAMOND_GESSENCE.get(),ModItem.EMERALD_GESSENCE.get()))
                .save(consumer);
        ShapedRecipeBuilder.shaped(ModItem.MODIUM_GESSENCE.get())
                .pattern("CCC")
                .pattern("MOM")
                .pattern("CCC")
                .define('C',Items.COBBLESTONE)
                .define('M',ModItem.EMERALD_GESSENCE.get())
                .define('O',ModItem.NETHERITE_GESSENCE.get())
                .unlockedBy("unlock",InventoryChangeTrigger.Instance.hasItems(Items.COBBLESTONE,ModItem.NETHERITE_GESSENCE.get(),ModItem.EMERALD_GESSENCE.get()))
                .save(consumer);
        ShapedRecipeBuilder.shaped(ModItem.DERIUM_GESSENCE.get())
                .pattern("CCC")
                .pattern("NON")
                .pattern("CCC")
                .define('C',Items.COBBLESTONE)
                .define('N',ModItem.NETHERITE_GESSENCE.get())
                .define('O',ModItem.MODIUM_GESSENCE.get())
                .unlockedBy("unlock",InventoryChangeTrigger.Instance.hasItems(Items.COBBLESTONE,ModItem.MODIUM_GESSENCE.get(),ModItem.NETHERITE_GESSENCE.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(ModItem.BLAZUM_GESSENCE.get())
                .pattern("CCC")
                .pattern("ODO")
                .pattern("CCC")
                .define('C',Items.COBBLESTONE)
                .define('D',ModItem.DERIUM_GESSENCE.get())
                .define('O',ModItem.MODIUM_GESSENCE.get())
                .unlockedBy("unlock",InventoryChangeTrigger.Instance.hasItems(Items.COBBLESTONE,ModItem.MODIUM_GESSENCE.get(),ModItem.DERIUM_GESSENCE.get()))
                .save(consumer);


    }
}
