package fr.iglee42.igleelib.common.baseutils;

import fr.iglee42.igleelib.IgleeLibrary;
import fr.iglee42.igleelib.common.blocks.entity.GhostBlockEntity;
import fr.iglee42.igleelib.common.client.ghostblock.GhostBlockModel;
import fr.iglee42.igleelib.common.client.ghostblock.GhostBlockRenderer;
import fr.iglee42.igleelib.common.init.ModBlock;
import fr.iglee42.igleelib.common.init.ModBlockEntities;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.BlockModelShapes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.model.SeparatePerspectiveModel;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.util.Map;
import java.util.function.Function;

@Mod.EventBusSubscriber(modid = IgleeLibrary.MODID,bus = Mod.EventBusSubscriber.Bus.MOD,value = Dist.CLIENT)
public class ClientEvents {

    private static final ResourceLocation GHOST_OVERLAY_LOCATION = new ResourceLocation(IgleeLibrary.MODID, "block/ghost_block_overlay");
    public static TextureAtlasSprite ghostOverlaySprite;

    @SubscribeEvent
    public static void clientStuff(FMLClientSetupEvent event){
        RenderTypeLookup.setRenderLayer(ModBlock.GHOST_BLOCK.get(), RenderType.translucent());
        ClientRegistry.bindTileEntityRenderer(ModBlockEntities.GHOST_BLOCK.get(),
                GhostBlockRenderer::new);
    }
    @SubscribeEvent
    public static void onTextureStitched(TextureStitchEvent.Post event) {
        if(PlayerContainer.BLOCK_ATLAS.equals(event.getMap().location())) {
            ghostOverlaySprite = event.getMap().getSprite(GHOST_OVERLAY_LOCATION);
        }
    }
    @SubscribeEvent
    public static void onBlockColors(ColorHandlerEvent.Block event) {
        BlockColors colors = event.getBlockColors();
        colors.register((state, world, pos, index) -> colors.getColor(((GhostBlockEntity)world.getBlockEntity(pos)).getStockedBlock(),world,pos,index), ModBlock.GHOST_BLOCK.get());
    }
    @SubscribeEvent
    public static void onModelBaked(ModelBakeEvent event) {
        Map<ResourceLocation, IBakedModel> registry = event.getModelRegistry();
        put(registry, GhostBlockModel::new,ModBlock.GHOST_BLOCK.get());
    }


    private static void put(Map<ResourceLocation, IBakedModel> registry, Function<IBakedModel, IBakedModel> creator, Block block) {
        for (BlockState state : block.getStateDefinition().getPossibleStates()) {
            registry.put(BlockModelShapes.stateToModelLocation(state), creator.apply(registry.get(BlockModelShapes.stateToModelLocation(state))));

        }
    }
    @Mod.EventBusSubscriber(modid = IgleeLibrary.MODID,bus = Mod.EventBusSubscriber.Bus.FORGE,value = Dist.CLIENT)
    public class Forge {

    }

}
