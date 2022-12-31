package fr.iglee42.techresourcesbase.common.blocks;

import fr.iglee42.techresourcesbase.config.TechResourcesBaseCommonConfig;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootContext;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomOre extends Block {
    Random RANDOM = new Random();
    public RandomOre(AbstractBlock.Properties prop) {
        super(prop);
    }

    @Override
    public List<ItemStack> getDrops(BlockState p_60537_, LootContext.Builder p_60538_) {
        List<ItemStack> drop = new ArrayList<>();
        if (TechResourcesBaseCommonConfig.RANDOM_ORE_CAN_DROP_COBBLESTONE.get()){
            if (RANDOM.nextInt(100) <= (TechResourcesBaseCommonConfig.PERCENT_DROP_COBBLE.get()-1)){
                ItemStack ore = new ItemStack(selectOre(),RANDOM.nextInt(TechResourcesBaseCommonConfig.MAX_RANDOM_ORE_DROPS_COUNT.get())+1);
                drop.add(ore);
            }else{
                drop.add(new ItemStack(Items.COBBLESTONE,TechResourcesBaseCommonConfig.COUNT_DROP_COBBLE.get()));
            }
        } else {
            ItemStack ore = new ItemStack(selectOre(),RANDOM.nextInt(TechResourcesBaseCommonConfig.MAX_RANDOM_ORE_DROPS_COUNT.get())+1);
            drop.add(ore);
        }
        return drop;
    }

    private Item selectOre() {
        Item ore = randomOre();
        List<Item> blacklisted = new ArrayList<>();
        if (TechResourcesBaseCommonConfig.BLACKLISTED_RANDOM_ORE_DROPS.get().isEmpty()) return ore;
        for (String s : TechResourcesBaseCommonConfig.BLACKLISTED_RANDOM_ORE_DROPS.get()){
            blacklisted.add(ForgeRegistries.ITEMS.getValue(new ResourceLocation(s)));
        }
        if (blacklisted.contains(ore)){
            ore = selectOre();
        }

        return ore;
    }

    private Item randomOre(){
        List<Item> itemsCanDrop = new ArrayList<>();
        if (TechResourcesBaseCommonConfig.RANDOM_ORE_CAN_DROP_MINERALS.get()){
            itemsCanDrop.addAll(Tags.Items.ORES.getValues());
        }
        if (!TechResourcesBaseCommonConfig.CUSTOM_RANDOM_ORE_DROPS.get().isEmpty()){
            for (String canDrop : TechResourcesBaseCommonConfig.CUSTOM_RANDOM_ORE_DROPS.get()){
                itemsCanDrop.add(ForgeRegistries.ITEMS.getValue(new ResourceLocation(canDrop)));
            }
        }
        Item random;
        random = itemsCanDrop.get(RANDOM.nextInt(itemsCanDrop.size()));
        return random;
    }

    @Override
    public void appendHoverText(ItemStack p_190948_1_, @Nullable IBlockReader p_190948_2_, List<ITextComponent> p_190948_3_, ITooltipFlag p_190948_4_) {
        p_190948_3_.add(new StringTextComponent("Drop a random ore").withStyle(TextFormatting.YELLOW));
        super.appendHoverText(p_190948_1_, p_190948_2_, p_190948_3_, p_190948_4_);
    }
}
