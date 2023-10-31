package fr.iglee42.igleelib.common.baseutils;

import fr.iglee42.igleelib.IgleeLibrary;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = IgleeLibrary.MODID,bus = Mod.EventBusSubscriber.Bus.MOD,value = Dist.CLIENT)
public class ClientEvents {

   /* private static final ResourceLocation GHOST_OVERLAY_LOCATION = new ResourceLocation(IgleeLibrary.MODID, "block/ghost_block_overlay");
    public static TextureAtlasSprite ghostOverlaySprite;

    @SubscribeEvent
    public static void clientStuff(final FMLClientSetupEvent event) {
        ItemBlockRenderTypes.setRenderLayer(ModBlock.GHOST_BLOCK.get(), RenderType.translucent());
    }
    @SubscribeEvent
    public static void onTextureStitch(TextureStitchEvent event) {
        if(InventoryMenu.BLOCK_ATLAS.equals(event.getAtlas().location())) {
            event.(GHOST_OVERLAY_LOCATION);
        }
    }
    @SubscribeEvent
    public static void onTextureStitched(TextureStitchEvent.Post event) {
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
    public static void onModelBaked(ModelEvent.BakingCompleted event) {
        Map<ResourceLocation, BakedModel> registry = event.getModels();
        //put(registry, GhostBlockModel::new,ModBlock.GHOST_BLOCK.get());
    }
    @SubscribeEvent
    public static void registerRenderers(final EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(ModBlockEntities.GHOST_BLOCK.get(),
                GhostBlockRenderer::new);
    }

    private static void put(Map<ResourceLocation, BakedModel> registry, Function<BakedModel, BakedModel> creator, Block block) {
        for (BlockState state : block.getStateDefinition().getPossibleStates()) {
            registry.put(BlockModelShaper.stateToModelLocation(state), creator.apply(registry.get(BlockModelShaper.stateToModelLocation(state))));

        }
    }*/
    @Mod.EventBusSubscriber(modid = IgleeLibrary.MODID,bus = Mod.EventBusSubscriber.Bus.FORGE,value = Dist.CLIENT)
    public class Forge {

    }

}
