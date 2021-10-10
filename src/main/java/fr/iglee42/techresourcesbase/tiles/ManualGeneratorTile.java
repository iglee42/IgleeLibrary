package fr.iglee42.techresourcesbase.tiles;

import fr.iglee42.techresourcesbase.init.ModTileEntity;
import fr.iglee42.techresourcesbase.utils.GessenceType;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;

public class ManualGeneratorTile extends TileEntity {
    private GessenceType gessence;

    public ManualGeneratorTile() {
        super(ModTileEntity.MANUAL_GENERATOR_TILE.get());
    }

    @Override
    public CompoundNBT save(CompoundNBT p_189515_1_) {
        super.save(p_189515_1_);

        if (this.gessence != null)p_189515_1_.putString("gessence",this.gessence.getRessourceName());

        return p_189515_1_;
    }

    @Override
    public void load(BlockState p_230337_1_, CompoundNBT p_230337_2_) {
        super.load(p_230337_1_, p_230337_2_);

        if (p_230337_2_.getString("gessence") != null ) { this.setGessence(GessenceType.getByResourceName(p_230337_2_.getString("gessence")));}
    }


    public boolean hasGessence(){
        return gessence != null;
    }

    public GessenceType getGessence() {
        return gessence;
    }

    public void setGessence(GessenceType gessence){
        this.gessence = gessence;
    }
}
