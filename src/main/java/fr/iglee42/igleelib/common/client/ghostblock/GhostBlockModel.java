package fr.iglee42.igleelib.common.client.ghostblock;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import fr.iglee42.igleelib.api.utils.ModelDataUtils;
import fr.iglee42.igleelib.common.baseutils.ClientEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.client.model.data.ModelData;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Supplier;

import static fr.iglee42.igleelib.common.blocks.GhostBlock.PS_BLOCKSTATE;
import static fr.iglee42.igleelib.common.blocks.GhostBlock.PS_FLUIDSTATE;
import static java.lang.Float.floatToRawIntBits;
import static java.lang.Float.intBitsToFloat;

public class GhostBlockModel implements BakedModel {
    private static final Supplier<BlockRenderDispatcher> DISPATCHER = () -> Minecraft.getInstance().getBlockRenderer();

    private final BakedModel model;
    private Integer quadsNumber = null;

    public GhostBlockModel(BakedModel model) {
        this.model = model;
    }



    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, RandomSource random, ModelData extraData, RenderType renderType) {
        Optional<BlockState> data = ModelDataUtils.getData(extraData, PS_BLOCKSTATE);
        if (!data.isPresent()) {
            return Collections.emptyList();
        }
        BlockState mirrorState = data.get();
        Optional<FluidState> dataFluid = ModelDataUtils.getData(extraData, PS_FLUIDSTATE);
        FluidState fluidState = dataFluid.get();

        Supplier<List<BakedQuad>> quads = () -> this.render(fluidState,mirrorState, state, DISPATCHER.get().getBlockModel(state), side, random, extraData,renderType);
        if (renderType == RenderType.translucent()){
            return this.getOverlay(gatherAllQuads(quads));
        }
        return quads.get();
    }

    private List<BakedQuad> getOverlay(List<BakedQuad> allQuads){
        List<BakedQuad> quads = new ArrayList<>(allQuads);
        quadsNumber = Math.toIntExact(allQuads.stream().filter(b -> !b.getSprite().equals(ClientEvents.ghostOverlaySprite)).count());
        if (quads.size() < quadsNumber*2) {
            for (BakedQuad quad : allQuads) {
                quads.add(generateOverlayQuad(quad));
            }
        }
        return quads;
    }
    protected List<BakedQuad> render(FluidState fluidState, @Nonnull BlockState mirrorState, @Nonnull BlockState baseState, @Nonnull BakedModel model, @Nullable Direction side, @Nonnull RandomSource rand, @Nonnull ModelData extraData, RenderType renderType) {
        List<BakedQuad> quads = new ArrayList<>(model.getQuads(mirrorState, side, rand, extraData,renderType));
        return quads;
    }



    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState p_235039_, @Nullable Direction p_235040_, RandomSource p_235041_) {
        return this.model.getQuads(p_235039_,p_235040_,p_235041_);
    }

    @Override
    public boolean useAmbientOcclusion() {
        return this.model.useAmbientOcclusion();
    }

    @Override
    public boolean useAmbientOcclusion(BlockState state) {
        return this.model.useAmbientOcclusion(state);
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
    public ItemOverrides getOverrides() {
        return this.model.getOverrides();
    }

    public BakedQuad generateOverlayQuad(BakedQuad quad) {
        TextureAtlasSprite overlaySprite = Minecraft.getInstance().getModelManager().getAtlas(InventoryMenu.BLOCK_ATLAS).getSprite(ClientEvents.GHOST_OVERLAY_LOCATION);
        int[] data = Arrays.copyOf(quad.getVertices(), quad.getVertices().length);
        for (int i = 0; i < 4; i++) {
            int j = DefaultVertexFormat.BLOCK.getIntegerSize() * i;

            float x = intBitsToFloat(data[j]) + 0.001F*quad.getDirection().getStepX();
            float y = intBitsToFloat(data[j+1]) + 0.001F*quad.getDirection().getStepY();
            float z = intBitsToFloat(data[j+2]) + 0.001F*quad.getDirection().getStepZ();

            data[j] = floatToRawIntBits(x);
            data[j+1] = floatToRawIntBits(y);
            data[j+2] = floatToRawIntBits(z);

            float ui;
            float vi;

            switch (quad.getDirection().getAxis()) {
                case X -> {
                    ui = z;
                    vi = 1 - y;
                }
                default -> {
                    ui = x;
                    vi = z;
                }
                case Z -> {
                    ui = x;
                    vi = 1 - y;
                }
            }

            data[j+4] = floatToRawIntBits(overlaySprite.getU(ui*16F));
            data[j+5] = floatToRawIntBits(overlaySprite.getV(vi*16F));

            data[j+6] = (240 << 16) | 240;
        }
        /*for (int i = 0; i < 4; i++) {
            int j = DefaultVertexFormat.BLOCK.getIntegerSize() * i;

            float x = intBitsToFloat(data[j]) + 0.001F*quad.getDirection().getStepX();
            float y = intBitsToFloat(data[j+1]) + 0.001F*quad.getDirection().getStepY();
            float z = intBitsToFloat(data[j+2]) + 0.001F*quad.getDirection().getStepZ();

            //data[j] = floatToRawIntBits(x);
            //data[j+1] = floatToRawIntBits(y);
            //data[j+2] = floatToRawIntBits(z);

            float ui;
            float vi;

            switch (quad.getDirection().getAxis()) {
                case X -> {
                    ui = z;
                    vi = 1 - y;
                }
                default -> {
                    ui = x;
                    vi = z;
                }
                case Z -> {
                    ui = x;
                    vi = 1 - y;
                }
            }

            //data[j+4] = floatToRawIntBits(ClientEvents.ghostOverlaySprite.getU(ui*16F));
            //data[j+5] = floatToRawIntBits(ClientEvents.ghostOverlaySprite.getV(vi*16F));

            //data[j+6] = (240 << 16) | 240;
        }*/

        return new BakedQuad(data, -1, quad.getDirection(), overlaySprite, quad.isShade());

    }
    protected List<BakedQuad> gatherAllQuads(Supplier<List<BakedQuad>> superQuads) {
        List<BakedQuad> quads = new ArrayList<>(superQuads.get());
        return quads;
    }


}