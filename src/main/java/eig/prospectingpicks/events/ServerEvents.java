package eig.prospectingpicks.events;

import eig.prospectingpicks.Config;
import eig.prospectingpicks.util.ModTags;
import eig.prospectingpicks.util.OreCounter;
import eig.prospectingpicks.util.OreTagConfig;

import net.minecraft.core.BlockPos;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.player.Player;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.Tags;
import net.minecraftforge.event.level.BlockEvent.BreakEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static eig.prospectingpicks.util.OreSearch.oreSearch;


@Mod.EventBusSubscriber(modid = "prospectingpicks", bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.DEDICATED_SERVER)
public class ServerEvents {

	@SubscribeEvent
	public static void blockBroken(BreakEvent event) {
		BlockState block = event.getState();

		Player player = event.getPlayer();
		if (player.getMainHandItem().is(ModTags.PROSPECTING_PICK)) {
			if (block.is(Tags.Blocks.STONE) || block.is(Tags.Blocks.END_STONES) || block.is(BlockTags.BASE_STONE_OVERWORLD) || block.is(BlockTags.BASE_STONE_NETHER)) {
				oreSearch(event.getPos(), event.getLevel(), player, Config.SEARCH_RADIUS.get());
			}
		}
	}

}
