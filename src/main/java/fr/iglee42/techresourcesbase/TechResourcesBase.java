package fr.iglee42.techresourcesbase;

import fr.iglee42.techresourcesbase.init.ModBlock;
import fr.iglee42.techresourcesbase.init.ModItem;
import fr.iglee42.techresourcesbase.init.ModTileEntity;
import fr.iglee42.techresourcesbase.utils.CustomGroup;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.gen.GenerationStage;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(TechResourcesBase.MODID)
public class TechResourcesBase {

    public static final String MODID = "techresourcesbase";




    public TechResourcesBase() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);

        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        ModBlock.BLOCKS.register(bus);
        ModTileEntity.TILE_ENTITIES.register(bus);
        ModItem.ITEMS.register(bus);

    }

    @SubscribeEvent
    public void biomeEvent(BiomeLoadingEvent event){
        event.getGeneration().getFeatures(GenerationStage.Decoration.UNDERGROUND_ORES).clear();
        event.getGeneration().getFeatures(GenerationStage.Decoration.UNDERGROUND_DECORATION).clear();
        event.getGeneration().getFeatures(GenerationStage.Decoration.UNDERGROUND_STRUCTURES).clear();
    }

    private void setup(FMLCommonSetupEvent e){
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void clientSetup(FMLClientSetupEvent e){
        RenderTypeLookup.setRenderLayer(ModBlock.MODIUM_GENERATOR.get(), RenderType.cutoutMipped());
        RenderTypeLookup.setRenderLayer(ModBlock.DERIUM_GENERATOR.get(), RenderType.cutoutMipped());
        RenderTypeLookup.setRenderLayer(ModBlock.BLAZUM_GENERATOR.get(), RenderType.cutoutMipped());
    }
}
