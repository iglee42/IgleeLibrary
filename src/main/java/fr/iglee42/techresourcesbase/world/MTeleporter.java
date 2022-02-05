package fr.iglee42.techresourcesbase.world;

import fr.iglee42.techresourcesbase.blocks.MineralPortalBlock;
import fr.iglee42.techresourcesbase.init.ModBlock;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.template.BlockIgnoreStructureProcessor;
import net.minecraft.world.gen.feature.template.IntegrityProcessor;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraftforge.common.util.ITeleporter;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;
import java.util.function.Function;

public class MTeleporter implements ITeleporter {
    public static BlockPos thisPos = BlockPos.ZERO;
    public static boolean insideDimension = true;

    public MTeleporter(BlockPos pos, boolean insideDim) {
        thisPos = pos;
        insideDimension = insideDim;
    }

    @Override
    public Entity placeEntity(Entity entity, ServerWorld currentWorld, ServerWorld destinationWorld,
                              float yaw, Function<Boolean, Entity> repositionEntity) {
        entity = repositionEntity.apply(false);
        double y = 61;

        if (!insideDimension) {
            y = thisPos.getY();
        }

        BlockPos destinationPos = new BlockPos(thisPos.getX(), y, thisPos.getZ());

        int tries = 0;
        while ((!destinationWorld.getBlockState(destinationPos).getMaterial().isSolidBlocking()) &&
                !destinationWorld.getBlockState(destinationPos).canBeReplaced(Fluids.WATER) &&
                !destinationWorld.getBlockState(destinationPos.above()).getMaterial().isSolidBlocking() &&
                !destinationWorld.getBlockState(destinationPos.above()).canBeReplaced(Fluids.WATER) && tries < 128) {
            destinationPos = destinationPos.above(2);
            tries++;
        }
        System.out.println(destinationPos.getY());

        entity.setPos(destinationPos.getX(), destinationPos.getY(), destinationPos.getZ());

        if (insideDimension) {
            boolean doSetBlock = true;
            for (BlockPos checkPos : BlockPos.betweenClosed(destinationPos.below(10).west(10), destinationPos.above(10).east(10))) {
                if (destinationWorld.getBlockState(checkPos).getBlock() instanceof MineralPortalBlock) {
                    doSetBlock = false;
                    break;
                }
            }
            if (doSetBlock) {
                ChunkPos chunkpos = new ChunkPos(destinationPos);
                MutableBoundingBox mutableboundingbox = new MutableBoundingBox(chunkpos.getMinBlockX(), 0, chunkpos.getMinBlockZ(), chunkpos.getMaxBlockX(), 256, chunkpos.getMaxBlockZ());
                PlacementSettings placementsettings = (new PlacementSettings()).setBoundingBox(mutableboundingbox).setRandom(new Random()).addProcessor(BlockIgnoreStructureProcessor.STRUCTURE_AND_AIR);
                Template template =  destinationWorld.getStructureManager().getOrCreate(new ResourceLocation("techresourcesbase","mineral_spawn"));
                BlockPos blockpos1 = template.getZeroPositionWithTransform(destinationPos.offset(-2,-3,-2), Mirror.NONE, Rotation.NONE);
                IntegrityProcessor integrityprocessor = new IntegrityProcessor(1.0F);
                placementsettings.clearProcessors().addProcessor(integrityprocessor);
                template.placeInWorld(destinationWorld,blockpos1, blockpos1,placementsettings,new Random(),4);
                placementsettings.popProcessor(integrityprocessor);
            }
        }

        return entity;
    }
}
