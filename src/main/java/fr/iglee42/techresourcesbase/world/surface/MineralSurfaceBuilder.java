package fr.iglee42.techresourcesbase.world.surface;

import com.mojang.serialization.Codec;
import fr.iglee42.techresourcesbase.init.ModBlock;
import net.minecraft.block.BlockState;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

import java.util.Random;

public class MineralSurfaceBuilder extends SurfaceBuilder<SurfaceBuilderConfig> {

    public static final BlockState LIME_GRASS = ModBlock.LIME_GRASS.get().defaultBlockState();

    public static final SurfaceBuilderConfig LIME_GRASS_CONFIG = new SurfaceBuilderConfig(LIME_GRASS,
            LIME_GRASS, LIME_GRASS);

    public MineralSurfaceBuilder(Codec<SurfaceBuilderConfig> codec) {
        super(codec);
    }

    @Override
    public void apply(Random random, IChunk chunkIn, Biome biomeIn, int x, int y, int z, double p_205610_7_, BlockState p_205610_9_, BlockState p_205610_10_, int p_205610_11_, long p_205610_12_, SurfaceBuilderConfig p_205610_14_) {
        SurfaceBuilder.DEFAULT.apply(random, chunkIn, biomeIn, x, y, z, p_205610_7_, p_205610_9_, p_205610_10_, p_205610_11_, p_205610_12_, LIME_GRASS_CONFIG);
    }


}
