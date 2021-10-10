package fr.iglee42.techresourcesbase.items;

import fr.iglee42.techresourcesbase.utils.CustomGroup;
import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class GeneratorItem extends BlockItem{
    public GeneratorItem(Block p_i48527_1_) {
        super(p_i48527_1_,  new Item.Properties().tab(CustomGroup.GENERATOR_GROUP));
    }


    @Override
    public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip.add(new TranslationTextComponent("tooltip.generator.nogessence"));
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
    }
}
