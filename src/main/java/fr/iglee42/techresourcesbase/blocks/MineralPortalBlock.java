package fr.iglee42.techresourcesbase.blocks;

import fr.iglee42.techresourcesbase.init.ModDimension;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class MineralPortalBlock extends Block {
    public MineralPortalBlock() {
        super(Properties.of(Material.HEAVY_METAL).strength(5.0f,10.0f).noCollission());
    }

    @Override
    public void entityInside(BlockState p_196262_1_, World p_196262_2_, BlockPos p_196262_3_, Entity p_196262_4_) {
        if (p_196262_4_.isShiftKeyDown()){
            if (p_196262_2_ instanceof ServerWorld){
                MinecraftServer minecraftserver = p_196262_4_.getServer();
                RegistryKey<World> registrykey = p_196262_4_.level.dimension() == ModDimension.MINERAL_DIMENSION_WORLD ? World.OVERWORLD : ModDimension.MINERAL_DIMENSION_WORLD;
                ServerWorld serverworld1 = minecraftserver.getLevel(registrykey);
                if (serverworld1 != null){
                    p_196262_2_.getProfiler().push("portal");
                    p_196262_4_.changeDimension(serverworld1);
                    p_196262_2_.getProfiler().pop();
                }
            }

        }
    }
}
