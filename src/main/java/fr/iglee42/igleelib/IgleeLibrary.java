package fr.iglee42.igleelib;

import com.google.gson.JsonElement;
import fr.iglee42.igleelib.common.init.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;


@Mod(IgleeLibrary.MODID)
public class IgleeLibrary {

    public static final String MODID = "igleelib";
    private static final HashMap<Class<?>,Function<? extends JsonElement,Object>> CLASS_PARSERS = new HashMap<>();;

    public IgleeLibrary() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        ModBlock.BLOCKS.register(bus);
        ModBlockEntities.BLOCK_ENTITIES.register(bus);
        ModItem.ITEMS.register(bus);
        ModCreativeTab.TABS.register(bus);

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::addCreative);


    }

    private void setup(FMLCommonSetupEvent e){
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {
        if (event.getTabKey() == ModCreativeTab.TAB.getKey()){
            ForgeRegistries.ITEMS.getKeys().stream().filter(rs-> rs.getNamespace().equals(MODID)).forEach(rs->
                    event.accept(ForgeRegistries.ITEMS.getValue(rs)));
        }
    }

    public static <T, J extends JsonElement> void addClassParser(Class<T> clazz, Function<J,T> parser){
        CLASS_PARSERS.put(clazz, (Function<? extends JsonElement, Object>) parser);
    }

    public static <T,J extends JsonElement> Function<J,T> getClassParser(Class<T> clazz){
        if (CLASS_PARSERS.containsKey(clazz))
            return (Function<J, T>) CLASS_PARSERS.get(clazz);
        throw new IllegalArgumentException("There isn't a class parse for " + clazz.getName());
    }

}
