package eig.prospectingpicks.events;

import eig.prospectingpicks.Config;
import eig.prospectingpicks.ProspectingPicks;
import eig.prospectingpicks.registry.ModItems;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

@Mod(ProspectingPicks.MODID)
public class ModEvents {

	@SubscribeEvent
	public static void addCreative(BuildCreativeModeTabContentsEvent event) {
		if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
			event.accept(ModItems.COPPER_PROSPECTOR_PICK.get());

			if (Config.ENABLE_SILVER_TOOL.getAsBoolean())
				event.accept(ModItems.SILVER_PROSPECTOR_PICK.get());

			event.accept(ModItems.IRON_PROSPECTOR_PICK.get());

			if (Config.ENABLE_BRONZE_TOOL.getAsBoolean())
				event.accept(ModItems.BRONZE_PROSPECTOR_PICK.get());

			if (Config.ENABLE_STEEL_TOOL.getAsBoolean())
				event.accept(ModItems.STEEL_PROSPECTOR_PICK.get());

			event.accept(ModItems.DIAMOND_PROSPECTOR_PICK.get());

			event.accept(ModItems.NETHERITE_PROSPECTOR_PICK.get());
		}
	}
}
