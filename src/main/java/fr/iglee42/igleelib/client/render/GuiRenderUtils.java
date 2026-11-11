package fr.iglee42.igleelib.client.render;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.joml.Matrix4f;

@OnlyIn(Dist.CLIENT)
public final class GuiRenderUtils {

    private GuiRenderUtils() {
    }

	/**
	 * Renders a nine-sliced texture region using a single buffered draw call.
	 * <p>
	 * This overload is the convenient symmetric variant where left/right share the same corner width
	 * and top/bottom share the same corner height. The source region starts at {@code (0, 0)} and the
	 * quad is rendered at z-level {@code 0}.
	 *
	 * @param guiGraphics the active gui graphics context
	 * @param texture the texture to sample
	 * @param x destination x position
	 * @param y destination y position
	 * @param width destination width
	 * @param height destination height
	 * @param sourceWidth source nine-slice width inside the texture
	 * @param sourceHeight source nine-slice height inside the texture
	 * @param cornerWidth corner width used for both left and right borders
	 * @param cornerHeight corner height used for both top and bottom borders
	 * @param textureWidth full texture width used to normalize U coordinates
	 * @param textureHeight full texture height used to normalize V coordinates
	 */
	public static void blitNineSlicedSized(
		GuiGraphics guiGraphics,
		ResourceLocation texture,
		int x,
		int y,
		int width,
		int height,
		int sourceWidth,
		int sourceHeight,
		int cornerWidth,
		int cornerHeight,
		int textureWidth,
		int textureHeight
	) {
		blitNineSlicedSized(guiGraphics, texture, x, y, 0, width, height, sourceWidth, sourceHeight,  cornerWidth, cornerHeight, textureWidth, textureHeight);
	}


	/**
     * Renders a nine-sliced texture region using a single buffered draw call.
     * <p>
     * This overload is the convenient symmetric variant where left/right share the same corner width
     * and top/bottom share the same corner height. The source region starts at {@code (0, 0)}.
     *
     * @param guiGraphics the active gui graphics context
     * @param texture the texture to sample
     * @param x destination x position
     * @param y destination y position
	 * @param z destination z level
	 * @param width destination width
     * @param height destination height
     * @param sourceWidth source nine-slice width inside the texture
     * @param sourceHeight source nine-slice height inside the texture
     * @param cornerWidth corner width used for both left and right borders
     * @param cornerHeight corner height used for both top and bottom borders
     * @param textureWidth full texture width used to normalize U coordinates
     * @param textureHeight full texture height used to normalize V coordinates
     */
    public static void blitNineSlicedSized(
        GuiGraphics guiGraphics,
        ResourceLocation texture,
        int x,
        int y,
		int z,
        int width,
        int height,
        int sourceWidth,
        int sourceHeight,
        int cornerWidth,
        int cornerHeight,
        int textureWidth,
        int textureHeight
    ) {
        blitNineSlicedSized(guiGraphics, texture, x, y, z, width, height, 0, 0, sourceWidth, sourceHeight, textureWidth, textureHeight, cornerWidth, cornerWidth, cornerHeight, cornerHeight);
    }

    /**
     * Renders a nine-sliced texture region using one {@link BufferBuilder} for all 9 quads.
     * <p>
     * Borders are clamped against source and destination dimensions to keep center dimensions non-negative.
     *
     * @param guiGraphics the active gui graphics context
     * @param texture the texture to sample
     * @param x destination x position
     * @param y destination y position
     * @param z destination z level
     * @param width destination width
     * @param height destination height
     * @param uOffset source x offset inside the texture
     * @param vOffset source y offset inside the texture
     * @param sourceWidth source nine-slice width inside the texture
     * @param sourceHeight source nine-slice height inside the texture
     * @param textureWidth full texture width used to normalize U coordinates
     * @param textureHeight full texture height used to normalize V coordinates
     * @param left left border width
     * @param right right border width
     * @param top top border height
     * @param bottom bottom border height
     */
    public static void blitNineSlicedSized(
        GuiGraphics guiGraphics,
        ResourceLocation texture,
        int x,
        int y,
        int z,
        int width,
        int height,
        int uOffset,
        int vOffset,
        int sourceWidth,
        int sourceHeight,
        int textureWidth,
        int textureHeight,
        int left,
        int right,
        int top,
        int bottom
    ) {
        if (width <= 0 || height <= 0 || sourceWidth <= 0 || sourceHeight <= 0 || textureWidth <= 0 || textureHeight <= 0) {
            return;
        }

        // Clamp borders against both source and destination to avoid negative center sizes.
        int clampedLeft = Math.min(left, Math.min(width / 2, sourceWidth / 2));
        int clampedRight = Math.min(right, Math.min((width - clampedLeft), (sourceWidth - clampedLeft)));
        int clampedTop = Math.min(top, Math.min(height / 2, sourceHeight / 2));
        int clampedBottom = Math.min(bottom, Math.min((height - clampedTop), (sourceHeight - clampedTop)));

        int centerDstWidth = width - clampedLeft - clampedRight;
        int centerDstHeight = height - clampedTop - clampedBottom;
        int centerSrcWidth = sourceWidth - clampedLeft - clampedRight;
        int centerSrcHeight = sourceHeight - clampedTop - clampedBottom;

        PoseStack poseStack = guiGraphics.pose();
        Matrix4f pose = poseStack.last().pose();

        RenderSystem.setShaderTexture(0, texture);
        RenderSystem.setShader(GameRenderer::getPositionTexShader);

        BufferBuilder builder = Tesselator.getInstance().begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);

        // Top row: corners are fixed, top edge repeats horizontally.
        addQuad(builder, pose, z, x, y, clampedLeft, clampedTop, uOffset, vOffset, textureWidth, textureHeight);
        addTiledRegion(builder, pose, z, x + clampedLeft, y, centerDstWidth, clampedTop, uOffset + clampedLeft, vOffset, centerSrcWidth, clampedTop, textureWidth, textureHeight);
        addQuad(builder, pose, z, x + width - clampedRight, y, clampedRight, clampedTop, uOffset + sourceWidth - clampedRight, vOffset, clampedRight, clampedTop, textureWidth, textureHeight);

        // Middle row: side edges repeat vertically and center repeats both axes.
        addTiledRegion(builder, pose, z, x, y + clampedTop, clampedLeft, centerDstHeight, uOffset, vOffset + clampedTop, clampedLeft, centerSrcHeight, textureWidth, textureHeight);
        addTiledRegion(builder, pose, z, x + clampedLeft, y + clampedTop, centerDstWidth, centerDstHeight, uOffset + clampedLeft, vOffset + clampedTop, centerSrcWidth, centerSrcHeight, textureWidth, textureHeight);
        addTiledRegion(builder, pose, z, x + width - clampedRight, y + clampedTop, clampedRight, centerDstHeight, uOffset + sourceWidth - clampedRight, vOffset + clampedTop, clampedRight, centerSrcHeight, textureWidth, textureHeight);

        // Bottom row: corners are fixed, bottom edge repeats horizontally.
        addQuad(builder, pose, z, x, y + height - clampedBottom, clampedLeft, clampedBottom, uOffset, vOffset + sourceHeight - clampedBottom, clampedLeft, clampedBottom, textureWidth, textureHeight);
        addTiledRegion(builder, pose, z, x + clampedLeft, y + height - clampedBottom, centerDstWidth, clampedBottom, uOffset + clampedLeft, vOffset + sourceHeight - clampedBottom, centerSrcWidth, clampedBottom, textureWidth, textureHeight);
        addQuad(builder, pose, z, x + width - clampedRight, y + height - clampedBottom, clampedRight, clampedBottom, uOffset + sourceWidth - clampedRight, vOffset + sourceHeight - clampedBottom, clampedRight, clampedBottom, textureWidth, textureHeight);

        BufferUploader.drawWithShader(builder.buildOrThrow());
    }

    private static void addQuad(
        BufferBuilder builder,
        Matrix4f pose,
        int z,
        int x,
        int y,
        int width,
        int height,
        int u,
        int v,
        int textureWidth,
        int textureHeight
    ) {
        addQuad(builder, pose, z, x, y, width, height, u, v, width, height, textureWidth, textureHeight);
    }

    private static void addQuad(
        BufferBuilder builder,
        Matrix4f pose,
        int z,
        int x,
        int y,
        int width,
        int height,
        int u,
        int v,
        int uWidth,
        int vHeight,
        int textureWidth,
        int textureHeight
    ) {
        if (width <= 0 || height <= 0 || uWidth <= 0 || vHeight <= 0) {
            return;
        }

        float minU = u / (float) textureWidth;
        float maxU = (u + uWidth) / (float) textureWidth;
        float minV = v / (float) textureHeight;
        float maxV = (v + vHeight) / (float) textureHeight;

        float minX = x;
        float maxX = x + width;
        float minY = y;
        float maxY = y + height;

        builder.addVertex(pose, minX, minY, z).setUv(minU, minV);
        builder.addVertex(pose, minX, maxY, z).setUv(minU, maxV);
        builder.addVertex(pose, maxX, maxY, z).setUv(maxU, maxV);
        builder.addVertex(pose, maxX, minY, z).setUv(maxU, minV);
    }

    private static void addTiledRegion(
        BufferBuilder builder,
        Matrix4f pose,
        int z,
        int x,
        int y,
        int width,
        int height,
        int u,
        int v,
        int tileUWidth,
        int tileVHeight,
        int textureWidth,
        int textureHeight
    ) {
        if (width <= 0 || height <= 0 || tileUWidth <= 0 || tileVHeight <= 0) {
            return;
        }

        for (int dx = 0; dx < width; dx += tileUWidth) {
            int drawWidth = Math.min(tileUWidth, width - dx);
            for (int dy = 0; dy < height; dy += tileVHeight) {
                int drawHeight = Math.min(tileVHeight, height - dy);
                addQuad(builder, pose, z, x + dx, y + dy, drawWidth, drawHeight, u, v, drawWidth, drawHeight, textureWidth, textureHeight);
            }
        }
    }
}

