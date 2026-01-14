package eig.prospectingpicks;

import eig.prospectingpicks.registry.ModItems;

import eig.prospectingpicks.util.ModTags;
import eig.prospectingpicks.util.OreTagPair;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Style;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTabs;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.Tags;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.level.BlockEvent.BreakEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


class OreCounter {
	public String ore;
	public String rawOre;
	public int count = 0;
	public int rawCount = 0;

	public OreCounter(String ore, String rawOre) {
		this.ore = ore;
		this.rawOre = rawOre;
	}
}

@Mod.EventBusSubscriber(modid = "mymod", bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.DEDICATED_SERVER)
public class ServerEvents {
	private static OreCounter[] makeOreCounter() {
		OreCounter[] oreCounter = new OreCounter[Config.ores.size()];

		int i = 0;
		for (OreTagPair pair : Config.ores) {
			oreCounter[i] = new OreCounter(pair.ore, pair.rawOre);
			i++;
		}

		return oreCounter;
	}

	private static void oreSearch(BlockPos pos, LevelAccessor level, Player player, int radius) {
		OreCounter[] oreCount = makeOreCounter();

		int r2 = radius * radius;
		for (int z = pos.getZ() - radius; z <= pos.getZ() + radius; z++) {
			for (int y = pos.getY() - radius; y <= pos.getY() + radius; y++) {
				for (int x = pos.getX() - radius; x <= pos.getX() + radius; x++) {
					if (y < level.getMinBuildHeight() || y > level.getMaxBuildHeight()) continue;
					int dx = x - pos.getX();
					int dy = y - pos.getY();
					int dz = z - pos.getZ();
					if (dx*dx + dy*dy + dz*dz <= r2) {
						BlockState block = level.getBlockState(new BlockPos(x, y, z));
						for (OreCounter cnt : oreCount) {
							if (block.is(ModTags.blockTag(ResourceLocation.parse("forge:ores/" + cnt.ore)))) {
								cnt.count++;
							}
							if (block.is(ModTags.blockTag(ResourceLocation.parse("forge:storage_blocks/" + cnt.rawOre)))) {
								cnt.rawCount++;
							}
						}
					}
				}
			}
		}

		boolean foundSomething = false;

		player.sendSystemMessage(
				Component.literal("------------------").withStyle(ChatFormatting.GRAY)
		);

		for (OreCounter cnt : oreCount) {
			if (cnt.rawCount >= Config.veinThreshold0_rawOre && cnt.count >= Config.veinThreshold0_ore) {
				// Found a vein!
				foundSomething = true;

				if (Config.displayNumericCount) {
					player.sendSystemMessage(
							Component.translatable(
									"prospectingpicks.message.found_ore_vein_numeric",
									cnt.ore,
									String.valueOf(cnt.count),
									String.valueOf(cnt.rawCount)
							).withStyle(ChatFormatting.YELLOW)
					);
				} else if (cnt.rawCount >= Config.veinThreshold2_rawOre && cnt.count >= Config.veinThreshold2_ore) {
					player.sendSystemMessage(
							Component.translatable(
									"prospectingpicks.message.found_ore_vein_2",
									cnt.ore
							).withStyle(ChatFormatting.YELLOW).withStyle(ChatFormatting.BOLD)
					);
				} else if (cnt.rawCount >= Config.veinThreshold1_rawOre && cnt.count >= Config.veinThreshold1_ore) {
					player.sendSystemMessage(
							Component.translatable(
									"prospectingpicks.message.found_ore_vein_1",
									cnt.ore
							).withStyle(ChatFormatting.YELLOW)
					);
				} else {
					player.sendSystemMessage(
							Component.translatable(
									"prospectingpicks.message.found_ore_vein_0",
									cnt.ore
							).withStyle(ChatFormatting.YELLOW)
					);
				}
			} else if (cnt.count > 0) {
				// Found a regular ore cluster
				foundSomething = true;

				if (Config.displayNumericCount) {
					player.sendSystemMessage(
							Component.translatable(
									"prospectingpicks.message.found_ore_cluster_numeric",
									cnt.ore,
									String.valueOf(cnt.count)
							).withStyle(ChatFormatting.GOLD)
					);
				} else if (cnt.count >= Config.clusterThreshold4) {
					player.sendSystemMessage(
							Component.translatable(
									"prospectingpicks.message.found_ore_cluster_4",
									cnt.ore
							).withStyle(ChatFormatting.GOLD).withStyle(ChatFormatting.BOLD)
					);
				}  else if (cnt.count >= Config.clusterThreshold3) {
					player.sendSystemMessage(
							Component.translatable(
									"prospectingpicks.message.found_ore_cluster_3",
									cnt.ore
							).withStyle(ChatFormatting.GOLD)
					);
				} else if (cnt.count >= Config.clusterThreshold2) {
					player.sendSystemMessage(
							Component.translatable(
									"prospectingpicks.message.found_ore_cluster_2",
									cnt.ore
							).withStyle(ChatFormatting.GOLD)
					);
				} else if (cnt.count >= Config.clusterThreshold1) {
					player.sendSystemMessage(
							Component.translatable(
									"prospectingpicks.message.found_ore_cluster_1",
									cnt.ore
							).withStyle(ChatFormatting.WHITE)
					);
				} else {
					player.sendSystemMessage(
							Component.translatable(
									"prospectingpicks.message.found_ore_cluster_0",
									cnt.ore
							).withStyle(ChatFormatting.WHITE)
					);
				}
			}
		}

		if (!foundSomething) {
			player.sendSystemMessage(
					Component.translatable("prospectingpicks.message.no_results").withStyle(ChatFormatting.GRAY)
			);
		}
	}

	@SubscribeEvent
	public static void blockBroken(BreakEvent event) {
		BlockState block = event.getState();

		Player player = event.getPlayer();
		if (player.getMainHandItem().is(ModTags.PROSPECTING_PICK)) {
			if (block.is(Tags.Blocks.STONE) || block.is(Tags.Blocks.END_STONES) || block.is(BlockTags.BASE_STONE_OVERWORLD) || block.is(BlockTags.BASE_STONE_NETHER)) {
				oreSearch(event.getPos(), event.getLevel(), player, Config.searchRadius);
			}
		}
	}

}
