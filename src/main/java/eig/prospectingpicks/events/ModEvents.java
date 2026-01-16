package eig.prospectingpicks.events;

import eig.prospectingpicks.Config;
import eig.prospectingpicks.ProspectingPicks;
import eig.prospectingpicks.registry.ModItems;

import net.minecraft.world.item.CreativeModeTabs;

import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber(modid = ProspectingPicks.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEvents {

	@SubscribeEvent
	public static void buildCreativeTabContents(BuildCreativeModeTabContentsEvent event) {
		if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
			event.accept(ModItems.COPPER_PROSPECTOR_PICK);

			event.accept(ModItems.IRON_PROSPECTOR_PICK);

			event.accept(ModItems.DIAMOND_PROSPECTOR_PICK);

			event.accept(ModItems.NETHERITE_PROSPECTOR_PICK);

			if (Config.ENABLE_SILVER_TOOL.get())
				event.accept(ModItems.SILVER_PROSPECTOR_PICK);

			if (Config.ENABLE_BRONZE_TOOL.get())
				event.accept(ModItems.BRONZE_PROSPECTOR_PICK);

			if (Config.ENABLE_STEEL_TOOL.get())
				event.accept(ModItems.STEEL_PROSPECTOR_PICK);
		}
	}

}
