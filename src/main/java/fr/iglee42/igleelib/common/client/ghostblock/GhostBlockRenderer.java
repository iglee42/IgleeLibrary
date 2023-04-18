package fr.iglee42.igleelib.common.client.ghostblock;

import com.mojang.blaze3d.matrix.MatrixStack;
import fr.iglee42.igleelib.common.blocks.entity.GhostBlockEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.tileentity.TileEntity;

public class GhostBlockRenderer extends TileEntityRenderer<GhostBlockEntity> {

    public GhostBlockRenderer(TileEntityRendererDispatcher p_i226006_1_) {
        super(p_i226006_1_);
    }

    @Override
    public void render(GhostBlockEntity gbe, float p_112308_, MatrixStack p_112309_, IRenderTypeBuffer p_112310_, int p_112311_, int p_225616_6_) {
        if (gbe.getStockedBlock().hasTileEntity()){
            TileEntity be = gbe.getStockedBlock().createTileEntity(gbe.getLevel());
            TileEntityRendererDispatcher.instance.getRenderer(be).render(be,p_112308_,p_112309_,p_112310_,p_112311_, OverlayTexture.pack(10,10));
        }
    }


}