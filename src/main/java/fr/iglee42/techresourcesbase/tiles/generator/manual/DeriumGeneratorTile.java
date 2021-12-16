package fr.iglee42.techresourcesbase.tiles.generator.manual;

import fr.iglee42.techresourcesbase.init.ModTileEntity;
import fr.iglee42.techresourcesbase.tiles.generator.GeneratorTile;
import fr.iglee42.techresourcesbase.utils.GessenceType;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;

public class DeriumGeneratorTile extends TileEntity implements ITickableTileEntity {

    private GessenceType gessence;
    private int delay;
    private int tick;

    public DeriumGeneratorTile() {
        super(ModTileEntity.DERIUM_GENERATOR_TILE.get());
    }


    @Override
    public CompoundNBT save(CompoundNBT p_189515_1_) {
        super.save(p_189515_1_);

        p_189515_1_.putInt("delay",delay);
        if (this.gessence != null)p_189515_1_.putString("gessence",this.gessence.getRessourceName());

        return p_189515_1_;
    }

    @Override
    public void load(BlockState p_230337_1_, CompoundNBT p_230337_2_) {
        super.load(p_230337_1_, p_230337_2_);

        this.delay = p_230337_2_.getInt("delay");
        this.setGessence(GessenceType.getByResourceName(p_230337_2_.getString("gessence")));


    }

    @Override
    public void tick() {
        tick++;
        if (tick == 20){
            tick = 0;
            if (delay > 0) delay--;
        }
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


    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }
}
