package fr.iglee42.techresourcesbase.blocks.generator;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;

public class GeneratorBase extends Block {
    public GeneratorBase(Properties p_i48440_1_) {
        super(p_i48440_1_.noOcclusion());
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }



}
