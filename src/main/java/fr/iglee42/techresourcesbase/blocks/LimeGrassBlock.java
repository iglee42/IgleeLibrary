package fr.iglee42.techresourcesbase.blocks;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootContext;
import net.minecraftforge.common.ToolType;

import java.util.ArrayList;
import java.util.List;

public class LimeGrassBlock extends Block {
    public LimeGrassBlock(Properties of) {
        super(of.harvestTool(ToolType.SHOVEL).strength(0.6f).sound(SoundType.GRASS));
    }

    @Override
    public List<ItemStack> getDrops(BlockState p_220076_1_, LootContext.Builder p_220076_2_) {
        List<ItemStack> it = new ArrayList<>();
        it.add(new ItemStack(Items.DIRT));
        return it;
    }
}
