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
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.*;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.util.Map;
import java.util.function.Function;

@Mod.EventBusSubscriber(modid = IgleeLibrary.MODID,bus = Mod.EventBusSubscriber.Bus.MOD,value = Dist.CLIENT)
public class ClientEvents {

    private static final ResourceLocation GHOST_OVERLAY_LOCATION = new ResourceLocation(IgleeLibrary.MODID, "block/ghost_block_overlay");
    public static TextureAtlasSprite ghostOverlaySprite;

    @SubscribeEvent
    public static void clientStuff(final FMLClientSetupEvent event) {
        ItemBlockRenderTypes.setRenderLayer(ModBlock.GHOST_BLOCK.get(), RenderType.translucent());
    }
    @SubscribeEvent
    public static void onTextureStitch(TextureStitchEvent.Pre event) {
        if(InventoryMenu.BLOCK_ATLAS.equals(event.getAtlas().location())) {
            event.addSprite(GHOST_OVERLAY_LOCATION);
        }
    }

    @SubscribeEvent
    public static void onTextureStitched(TextureStitchEvent.Post event) {
        if(InventoryMenu.BLOCK_ATLAS.equals(event.getAtlas().location())) {
            ghostOverlaySprite = event.getAtlas().getSprite(GHOST_OVERLAY_LOCATION);
        }
    }
    @SubscribeEvent
    public static void onBlockColors(ColorHandlerEvent.Block event) {
        BlockColors colors = event.getBlockColors();
        colors.register((state, world, pos, index) -> colors.getColor(((GhostBlockEntity)world.getBlockEntity(pos)).getStockedBlock(),world,pos,index), ModBlock.GHOST_BLOCK.get());
    }
    @SubscribeEvent
    public static void onModelBaked(ModelBakeEvent event) {
        Map<ResourceLocation, BakedModel> registry = event.getModelRegistry();
        put(registry, GhostBlockModel::new,ModBlock.GHOST_BLOCK.get());
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
    }
    @Mod.EventBusSubscriber(modid = IgleeLibrary.MODID,bus = Mod.EventBusSubscriber.Bus.FORGE,value = Dist.CLIENT)
    public class Forge {
        @SubscribeEvent
        public static void onKeyInput(InputEvent.KeyInputEvent event) {
            /*if (KeyBinding.NO_CLIP.isDown()) {
                ModMessages.sendToServer(new NoClipC2SPacket());
            }*/
        }
    }

}
