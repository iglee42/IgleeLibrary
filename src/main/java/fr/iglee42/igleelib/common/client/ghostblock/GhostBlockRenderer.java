package fr.iglee42.igleelib.common.client.ghostblock;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import fr.iglee42.igleelib.common.blocks.entity.GhostBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.block.ModelBlockRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.client.model.data.ModelData;

public class GhostBlockRenderer implements BlockEntityRenderer<GhostBlockEntity> {
    public GhostBlockRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(GhostBlockEntity gbe, float p_112308_, PoseStack stack, MultiBufferSource source, int p_112311_, int p_112312_) {
        // if (gbe.getStockedBlock().hasBlockEntity() && gbe.getStockedBlock().getBlock() instanceof BaseEntityBlock beb){
        //BlockEntity be = beb.newBlockEntity(gbe.getBlockPos(),gbe.getStockedBlock());
        //Minecraft.getInstance().getBlockEntityRenderDispatcher().getRenderer(be).render(be,p_112308_,p_112309_,p_112310_,p_112311_, OverlayTexture.RED_OVERLAY_V);
        BlockRenderDispatcher dispatcher = Minecraft.getInstance().getBlockRenderer();
        ModelBlockRenderer renderer = dispatcher.getModelRenderer();
        switch (gbe.getStockedBlock().getRenderShape()) {
            case MODEL -> {
                BakedModel model = dispatcher.getBlockModel(gbe.getStockedBlock());
                ModelData data = model.getModelData(gbe.getLevel(), gbe.getBlockPos(), gbe.getStockedBlock(), ModelData.EMPTY);
                int i = Minecraft.getInstance().getBlockColors().getColor(gbe.getStockedBlock(), null, null, 0);

                float red = (i >> 16 & 0xFF) / 255F;
                float green = (i >> 8 & 0xFF) / 255F;
                float blue = (i & 0xFF) / 255F;

                VertexConsumer vc = source.getBuffer(RenderType.TRANSLUCENT);

                renderer.renderModel(stack.last(), vc, gbe.getStockedBlock(), model, red, green, blue, 0xF000F0, OverlayTexture.RED_OVERLAY_V);
            }
            case ENTITYBLOCK_ANIMATED -> {
                BlockEntity be = ((EntityBlock) gbe.getStockedBlock().getBlock()).newBlockEntity(gbe.getBlockPos(), gbe.getStockedBlock());
                Minecraft.getInstance().getBlockEntityRenderDispatcher().getRenderer(be).render(be, p_112308_, stack, source, p_112311_, OverlayTexture.RED_OVERLAY_V);

            }

        }
    }
}