package fr.iglee42.igleelib.common.baseutils;

import fr.iglee42.igleelib.IgleeLibrary;
import fr.iglee42.igleelib.common.blocks.entity.GhostBlockEntity;
import fr.iglee42.igleelib.common.client.ghostblock.GhostBlockModel;
import fr.iglee42.igleelib.common.client.ghostblock.GhostBlockRenderer;
import fr.iglee42.igleelib.common.init.ModBlock;
import fr.iglee42.igleelib.common.init.ModBlockEntities;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockModelShaper;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.event.TextureAtlasStitchedEvent;

import java.util.Map;
import java.util.function.Function;

@EventBusSubscriber(modid = IgleeLibrary.MODID,bus = EventBusSubscriber.Bus.MOD,value = Dist.CLIENT)
public class ClientEvents {

   public static final ResourceLocation GHOST_OVERLAY_LOCATION = ResourceLocation.fromNamespaceAndPath(IgleeLibrary.MODID, "block/ghost_block_overlay");
    public static TextureAtlasSprite ghostOverlaySprite;

    @SubscribeEvent
    public static void clientStuff(final FMLClientSetupEvent event) {
        ItemBlockRenderTypes.setRenderLayer(ModBlock.GHOST_BLOCK.get(), RenderType.translucent());
    }

    @SubscribeEvent
    public static void onTextureStitched(TextureAtlasStitchedEvent event) {
        if(InventoryMenu.BLOCK_ATLAS.equals(event.getAtlas().location())) {
            ghostOverlaySprite = event.getAtlas().getSprite(GHOST_OVERLAY_LOCATION);
        }
    }
    @SubscribeEvent
    public static void onBlockColors(RegisterColorHandlersEvent.Block event) {
        BlockColors colors = event.getBlockColors();
        colors.register((state, world, pos, index) -> colors.getColor(((GhostBlockEntity)world.getBlockEntity(pos)).getStockedBlock(),world,pos,index), ModBlock.GHOST_BLOCK.get());
    }
    @SubscribeEvent
    public static void onModelBaked(ModelEvent.ModifyBakingResult event) {
        Map<ModelResourceLocation, BakedModel> registry = event.getModels();
        put(registry, GhostBlockModel::new,ModBlock.GHOST_BLOCK.get());
    }
    @SubscribeEvent
    public static void registerRenderers(final EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(ModBlockEntities.GHOST_BLOCK.get(),
                GhostBlockRenderer::new);
    }

    private static void put(Map<ModelResourceLocation, BakedModel> registry, Function<BakedModel, BakedModel> creator, Block block) {
        for (BlockState state : block.getStateDefinition().getPossibleStates()) {
            registry.put(BlockModelShaper.stateToModelLocation(state), creator.apply(registry.get(BlockModelShaper.stateToModelLocation(state))));

        }
    }

}
