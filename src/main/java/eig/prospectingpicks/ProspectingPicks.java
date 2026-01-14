package eig.prospectingpicks;

import com.mojang.logging.LogUtils;
import eig.prospectingpicks.events.ModEvents;
import eig.prospectingpicks.events.ServerEvents;
import eig.prospectingpicks.registry.ModItems;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(ProspectingPicks.MODID)
public class ProspectingPicks
{
    public static final String MODID = "prospectingpicks";
    private static final Logger LOGGER = LogUtils.getLogger();

    public ProspectingPicks(FMLJavaModLoadingContext context)
    {
        IEventBus modEventBus = context.getModEventBus();

		modEventBus.addListener(ModEvents::buildCreativeTabContents);
		ModItems.ITEMS.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);
		MinecraftForge.EVENT_BUS.register(ModEvents.class);
		MinecraftForge.EVENT_BUS.register(ServerEvents.class);

        context.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }
}
