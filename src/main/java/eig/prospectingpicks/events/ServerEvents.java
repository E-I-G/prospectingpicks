package eig.prospectingpicks.events;

import eig.prospectingpicks.Config;
import eig.prospectingpicks.ProspectingPicks;
import eig.prospectingpicks.util.ModTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.event.level.BlockEvent;

import static eig.prospectingpicks.util.OreSearch.oreSearch;

@Mod(value = ProspectingPicks.MODID)
public class ServerEvents {
	@SubscribeEvent
	public static void blockBroken(BlockEvent.BreakEvent event) {
		BlockState block = event.getState();

		Player player = event.getPlayer();
		if (player.getMainHandItem().is(ModTags.PROSPECTING_PICK)) {
			if (block.is(Tags.Blocks.STONES) || block.is(Tags.Blocks.END_STONES) || block.is(BlockTags.BASE_STONE_OVERWORLD) || block.is(BlockTags.BASE_STONE_NETHER)) {
				oreSearch(event.getPos(), event.getLevel(), player, Config.SEARCH_RADIUS.get());
			}
		}
	}
}
