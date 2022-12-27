package fr.iglee42.techresourcesbase.data;

import fr.iglee42.techresourcesbase.TechResourcesBase;
import fr.iglee42.techresourcesbase.data.recipe.RecipeGenerator;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = TechResourcesBase.MODID,bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGeneration {

    @SubscribeEvent
    public static void gatherData(final GatherDataEvent e){
        DataGenerator generator = e.getGenerator();

        if (e.includeServer()){
            generator.addProvider(true,new RecipeGenerator(generator));
        }
    }
}
