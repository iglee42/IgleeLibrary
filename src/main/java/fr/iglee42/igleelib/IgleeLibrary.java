package fr.iglee42.igleelib;

import fr.iglee42.igleelib.common.baseutils.ClientEvents;
import fr.iglee42.igleelib.common.baseutils.GhostBlock;
import fr.iglee42.igleelib.common.init.ModBlock;
import fr.iglee42.igleelib.common.init.ModBlockEntities;
import fr.iglee42.igleelib.common.init.ModCreativeTab;
import fr.iglee42.igleelib.common.init.ModItem;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

import java.util.ArrayList;
import java.util.List;


@Mod(IgleeLibrary.MODID)
public class IgleeLibrary {

    public static final List<GhostBlock> ghostBlocks = new ArrayList<>();
    public static final String MODID = "igleelib";

    public IgleeLibrary(IEventBus bus, ModContainer container) {
        ModBlock.BLOCKS.register(bus);
        ModBlockEntities.BLOCK_ENTITIES.register(bus);
        ModItem.ITEMS.register(bus);
        ModCreativeTab.TABS.register(bus);

        bus.addListener(this::addCreative);
    }


    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {
        if (event.getTabKey() == ModCreativeTab.TAB.getKey()){
            ModItem.ITEMS.getEntries().forEach(holder->{
                event.accept(holder.get());
            });
        }
    }

}
