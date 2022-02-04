package fr.iglee42.techresourcesbase.blocks;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraftforge.common.Tags;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomOreBlock extends Block {
    public RandomOreBlock(Properties properties) {
        super(properties);
    }

    @Override
    public List<ItemStack> getDrops(BlockState p_220076_1_, LootContext.Builder p_220076_2_) {
        List<Item> ores = new ArrayList<>();
        ores.addAll(Tags.Items.ORES.getValues());
        int ore = new Random().nextInt(ores.size());
        List<ItemStack> drop = new ArrayList<>();
        drop.add(new ItemStack(ores.get(ore)));
        return drop;
    }

}
