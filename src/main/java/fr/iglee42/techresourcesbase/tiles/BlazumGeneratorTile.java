package fr.iglee42.techresourcesbase.tiles;

import fr.iglee42.techresourcesbase.init.ModTileEntity;
import fr.iglee42.techresourcesbase.utils.GessenceType;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;

public class BlazumGeneratorTile extends GeneratorTile {
    public BlazumGeneratorTile() {
        super(ModTileEntity.BLAZUM_GENERATOR_TILE.get(), 48);
    }


    @Override
    public CompoundNBT save(CompoundNBT p_189515_1_) {
        super.save(p_189515_1_);

        p_189515_1_.putInt("tick",this.getTick());
        if (this.getGessence() != null)p_189515_1_.putString("gessence",this.getGessence().getRessourceName());

        return p_189515_1_;
    }

    @Override
    public void load(BlockState p_230337_1_, CompoundNBT p_230337_2_) {
        super.load(p_230337_1_, p_230337_2_);

        this.setTick( p_230337_2_.getInt("tick"));
        if (p_230337_2_.getString("gessence") != null ) { this.setGessence(GessenceType.getByResourceName(p_230337_2_.getString("gessence")));}
    }
}
