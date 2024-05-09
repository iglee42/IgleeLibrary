package fr.iglee42.igleelib;

import fr.iglee42.igleelib.common.init.ModBlock;
import fr.iglee42.igleelib.common.init.ModBlockEntities;
import fr.iglee42.igleelib.common.init.ModCreativeTab;
import fr.iglee42.igleelib.common.init.ModItem;
import net.neoforged.bus.EventBus;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;


@Mod(IgleeLibrary.MODID)
public class IgleeLibrary {

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
