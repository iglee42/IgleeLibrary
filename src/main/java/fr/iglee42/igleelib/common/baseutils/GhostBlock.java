package fr.iglee42.igleelib.common.baseutils;

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.ByteBufferBuilder;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import fr.iglee42.igleelib.IgleeLibrary;
import fr.iglee42.igleelib.mixins.AccessorMultiBufferSource;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.model.data.ModelData;

import java.util.IdentityHashMap;
import java.util.Map;
import java.util.SequencedMap;

public class GhostBlock {

    private BlockPos pos;
    private BlockState state;
    private int time;

    public GhostBlock(BlockPos pos, BlockState state, int time) {
        this.pos = pos;
        this.state = state;
        this.time = time;
    }

    public BlockPos getPos() {
        return pos;
    }

    public BlockState getState() {
        return state;
    }

    public int getTime() {
        return time;
    }


    private void renderBlock(PoseStack matrix, MultiBufferSource.BufferSource buffers, boolean shouldTranslate) {
        if (state.liquid()){
            matrix.pushPose();
            if (shouldTranslate)matrix.translate(pos.getX(), pos.getY(), pos.getZ());
            matrix.translate(0,2/16f,0);
            Lighting.setupForFlatItems();
            if (!state.getFluidState().isEmpty() && Minecraft.getInstance().level != null){
                Minecraft.getInstance().getBlockRenderer().renderLiquid(pos,Minecraft.getInstance().level,new LiquidBlockVertexConsumer(buffers.getBuffer(ItemBlockRenderTypes.getRenderLayer(state.getFluidState())),matrix,pos),state,state.getFluidState());
            }
            matrix.popPose();
            return;
        }
        if (state.getRenderShape() == RenderShape.MODEL) {
            if (shouldTranslate)matrix.translate(pos.getX(), pos.getY(), pos.getZ());
            BlockRenderDispatcher blockRenderer = Minecraft.getInstance().getBlockRenderer();
            BakedModel model = blockRenderer.getBlockModel(state);
            for (RenderType layer : model.getRenderTypes(state, Minecraft.getInstance().level.random, ModelData.EMPTY)) {
                matrix.pushPose();
                Lighting.setupForFlatItems();
                blockRenderer.renderSingleBlock(state, matrix, buffers, (int) (LightTexture.FULL_BLOCK * 0.8), OverlayTexture.NO_OVERLAY, ModelData.EMPTY, layer);
                if (!state.getFluidState().isEmpty() && Minecraft.getInstance().level != null){
                    matrix.translate(0,2/16f,0);
                    Minecraft.getInstance().getBlockRenderer().renderLiquid(pos,Minecraft.getInstance().level,new LiquidBlockVertexConsumer(buffers.getBuffer(ItemBlockRenderTypes.getRenderLayer(state.getFluidState())),matrix,pos),state,state.getFluidState());
                }
                matrix.popPose();
            }
        }
    }

    public void render(PoseStack stack){
        Vec3 renderView = Minecraft.getInstance().gameRenderer.getMainCamera().getPosition();
        stack.pushPose();
        stack.translate(-renderView.x, -renderView.y, -renderView.z);
        stack.translate(pos.getX(),pos.getY(),pos.getZ());

        MultiBufferSource.BufferSource buffers = initBuffers(Minecraft.getInstance().renderBuffers().bufferSource());


        renderBlock(stack,buffers,false);

        buffers.endBatch();

        stack.popPose();
    }

    private static MultiBufferSource.BufferSource initBuffers(MultiBufferSource.BufferSource original) {
        ByteBufferBuilder fallback = ((AccessorMultiBufferSource) original).getFallbackBuffer();
        SequencedMap<RenderType, ByteBufferBuilder> layerBuffers = ((AccessorMultiBufferSource) original).getFixedBuffers();
        SequencedMap<RenderType, ByteBufferBuilder> remapped = new Object2ObjectLinkedOpenHashMap<>();
        for (Map.Entry<RenderType, ByteBufferBuilder> e : layerBuffers.entrySet()) {
            remapped.put(GhostRenderLayer.remap(e.getKey()), e.getValue());
        }
        return new GhostBuffers(fallback, remapped);
    }

    public boolean tick() {
        if (time == 0) return true;
        if (time > 0){
            time--;
        }
        return false;
    }

    private static class GhostBuffers extends MultiBufferSource.BufferSource {
        protected GhostBuffers(ByteBufferBuilder fallback, SequencedMap<RenderType, ByteBufferBuilder> layerBuffers) {
            super(fallback, layerBuffers);
        }

        @Override
        public VertexConsumer getBuffer(RenderType type) {
            return super.getBuffer(GhostRenderLayer.remap(type));
        }
    }

    private static class GhostRenderLayer extends RenderType {
        private static final Map<RenderType, RenderType> remappedTypes = new IdentityHashMap<>();

        private GhostRenderLayer(RenderType original) {
            super(String.format("%s_%s_ghost", original.toString(), IgleeLibrary.MODID), original.format(), original.mode(), original.bufferSize(), original.affectsCrumbling(), true, () -> {
                original.setupRenderState();

                RenderSystem.enableBlend();
                RenderSystem.setShaderColor(1, 1, 1, 0.5F);
            }, () -> {
                RenderSystem.setShaderColor(1, 1, 1, 1);
                RenderSystem.disableBlend();
                original.clearRenderState();
            });
        }


        public static RenderType remap(RenderType in) {
            if (in instanceof GhostRenderLayer) {
                return in;
            } else {
                return remappedTypes.computeIfAbsent(in, GhostRenderLayer::new);
            }
        }
    }

}
