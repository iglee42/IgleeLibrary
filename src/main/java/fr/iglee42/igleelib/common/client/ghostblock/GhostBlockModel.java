package fr.iglee42.igleelib.common.client.ghostblock;

import com.mojang.blaze3d.matrix.MatrixStack;
import fr.iglee42.igleelib.api.utils.ModelDataUtils;
import fr.iglee42.igleelib.common.baseutils.ClientEvents;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.*;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.Direction;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.data.IModelData;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.function.Supplier;

import static fr.iglee42.igleelib.common.blocks.GhostBlock.PS_BLOCKSTATE;
import static fr.iglee42.igleelib.common.blocks.GhostBlock.PS_FLUIDSTATE;
import static java.lang.Float.floatToRawIntBits;
import static java.lang.Float.intBitsToFloat;

public class GhostBlockModel implements IBakedModel {
    private static final Supplier<BlockRendererDispatcher> DISPATCHER = () -> Minecraft.getInstance().getBlockRenderer();

    private final IBakedModel model;

    public GhostBlockModel(IBakedModel model) {
        this.model = model;
    }

    @Override
    public List<BakedQuad> getQuads(BlockState state, Direction side, Random random, IModelData extraData) {
        Optional<BlockState> data = ModelDataUtils.getData(extraData, PS_BLOCKSTATE);
        if (!data.isPresent()) {
            return Collections.emptyList();
        }
        BlockState mirrorState = data.get();
        Optional<FluidState> dataFluid = ModelDataUtils.getData(extraData, PS_FLUIDSTATE);
        FluidState fluidState = dataFluid.get();

        Supplier<List<BakedQuad>> quads = () -> this.render(fluidState,mirrorState, state, DISPATCHER.get().getBlockModel(state), side, random, extraData);
        if (MinecraftForgeClient.getRenderLayer() == RenderType.translucent()){
            return this.getOverlay(gatherAllQuads(quads));
        }
        return quads.get();
    }

    private List<BakedQuad> getOverlay(List<BakedQuad> allQuads){
        List<BakedQuad> quads = new ArrayList<>(allQuads);
        for (BakedQuad quad : allQuads){
            quads.add(generateOverlayQuad(quad));
        }
        return quads;
    }
    protected List<BakedQuad> render(FluidState fluidState,@Nonnull BlockState mirrorState, @Nonnull BlockState baseState, @Nonnull IBakedModel model,  Direction side, @Nonnull Random rand, @Nonnull IModelData extraData) {
        List<BakedQuad> quads = new ArrayList<>(model.getQuads(mirrorState, side, rand, extraData));
        if (fluidState.getType() != Fluids.EMPTY)quads.addAll(new GhostBlockFluidModel(fluidState.getType()).bake(null,null, ModelLoader.defaultTextureGetter(), ModelRotation.X0_Y0, null,null).getQuads(fluidState.createLegacyBlock(),side,rand,extraData));
        return quads;
    }

    @Override
    public List<BakedQuad> getQuads( BlockState p_119123_,  Direction p_119124_, Random p_119125_) {
        return this.model.getQuads(p_119123_, p_119124_, p_119125_);
    }

    @Override
    public boolean useAmbientOcclusion() {
        return this.model.useAmbientOcclusion();
    }

    @Override
    public boolean isGui3d() {
        return this.model.isGui3d();
    }

    @Override
    public boolean usesBlockLight() {
        return this.model.usesBlockLight();
    }

    @Override
    public boolean isCustomRenderer() {
        return false;
    }

    @Override
    public TextureAtlasSprite getParticleIcon() {
        return this.model.getParticleIcon();
    }

    @Override
    public ItemOverrideList getOverrides() {
        return this.model.getOverrides();
    }

    @Override
    public IBakedModel handlePerspective(ItemCameraTransforms.TransformType cameraTransformType, MatrixStack mat) {
        return this.model.handlePerspective(cameraTransformType, mat);
    }

    public BakedQuad generateOverlayQuad(BakedQuad quad) {
        int[] data = Arrays.copyOf(quad.getVertices(), quad.getVertices().length);
        for (int i = 0; i < 4; i++) {
            int j = DefaultVertexFormats.BLOCK.getIntegerSize() * i;

            float x = intBitsToFloat(data[j]) + 0.001F*quad.getDirection().getStepX();
            float y = intBitsToFloat(data[j+1]) + 0.001F*quad.getDirection().getStepY();
            float z = intBitsToFloat(data[j+2]) + 0.001F*quad.getDirection().getStepZ();

            //data[j] = floatToRawIntBits(x);
            //data[j+1] = floatToRawIntBits(y);
            //data[j+2] = floatToRawIntBits(z);

            float ui;
            float vi;

            switch (quad.getDirection().getAxis()) {
                case X :
                    ui = z;
                    vi = 1 - y;
                    break;
                default :
                    ui = x;
                    vi = z;
                    break;
                case Z :
                    ui = x;
                    vi = 1 - y;
                    break;
            }

            data[j+4] = floatToRawIntBits(ClientEvents.ghostOverlaySprite.getU(ui*16F));
            data[j+5] = floatToRawIntBits(ClientEvents.ghostOverlaySprite.getV(vi*16F));

            //data[j+6] = (240 << 16) | 240;
        }

        return new BakedQuad(data, -1, quad.getDirection(), ClientEvents.ghostOverlaySprite, quad.isShade());

    }
    protected List<BakedQuad> gatherAllQuads(Supplier<List<BakedQuad>> superQuads) {
        RenderType layer = MinecraftForgeClient.getRenderLayer();

        ForgeHooksClient.setRenderLayer(null);
        List<BakedQuad> quads = new ArrayList<>(superQuads.get());
        ForgeHooksClient.setRenderLayer(layer);
        return quads;
    }


}