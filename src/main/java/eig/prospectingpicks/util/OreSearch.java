package eig.prospectingpicks.util;

import eig.prospectingpicks.Config;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;

public class OreSearch {
	private static OreCounter[] makeOreCounter() {
		OreCounter[] oreCounter = new OreCounter[Config.ores.size()];

		int i = 0;
		for (OreTagConfig ore : Config.ores) {
			oreCounter[i] = new OreCounter(ore);
			i++;
		}

		return oreCounter;
	}

	private static Component getOreTextComponent(OreCounter cnt) {
		if (cnt.getTranslationKey() == null) {
			return Component.literal(cnt.getFallbackName()).withStyle(cnt.ore.isTreasure ? ChatFormatting.LIGHT_PURPLE : ChatFormatting.AQUA);
		} else {
			return Component.translatable(cnt.getTranslationKey()).withStyle(cnt.ore.isTreasure ? ChatFormatting.LIGHT_PURPLE : ChatFormatting.AQUA);
		}
	}

	private static void sendOreMessage(Player player, String tlKey, OreCounter cnt, ChatFormatting baseFormat) {
		Component msg = Component.translatable(
				tlKey,
				getOreTextComponent(cnt)
		).withStyle(baseFormat);

		player.sendSystemMessage(msg);
	}

	private static void sendOreMessageNumeric(Player player, String tlKey, OreCounter cnt) {
		Component msg = Component.translatable(
				tlKey,
				String.valueOf(cnt.count),
				getOreTextComponent(cnt)
		).withStyle(ChatFormatting.GOLD);

		player.sendSystemMessage(msg);
	}

	private static void sendOreVeinMessageNumeric(Player player, String tlKey, OreCounter cnt) {
		Component msg = Component.translatable(
				tlKey,
				String.valueOf(cnt.count),
				String.valueOf(cnt.rawCount),
				getOreTextComponent(cnt)
		).withStyle(ChatFormatting.GOLD);

		player.sendSystemMessage(msg);
	}

	public static void oreSearch(BlockPos pos, LevelAccessor level, Player player, int radius) {
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
							if (block.is(ModTags.blockTag(ResourceLocation.parse("forge:ores/" + cnt.ore.ore)))) {
								cnt.count++;
							}
							if (block.is(ModTags.blockTag(ResourceLocation.parse("forge:storage_blocks/" + cnt.ore.rawOre)))) {
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
					sendOreVeinMessageNumeric(player, "prospectingpicks.message.found_ore_vein_numeric", cnt);
				} else if (cnt.rawCount >= Config.veinThreshold2_rawOre && cnt.count >= Config.veinThreshold2_ore) {
					sendOreMessage(player, "prospectingpicks.message.found_ore_vein_2", cnt, ChatFormatting.YELLOW);
				} else if (cnt.rawCount >= Config.veinThreshold1_rawOre && cnt.count >= Config.veinThreshold1_ore) {
					sendOreMessage(player, "prospectingpicks.message.found_ore_vein_1", cnt, ChatFormatting.YELLOW);
				} else {
					sendOreMessage(player, "prospectingpicks.message.found_ore_vein_0", cnt, ChatFormatting.YELLOW);
				}
			} else if (cnt.count >= Config.clusterThreshold0) {
				// Found a regular ore cluster
				foundSomething = true;

				if (Config.displayNumericCount) {
					sendOreMessageNumeric(player, "prospectingpicks.message.found_ore_cluster_numeric", cnt);
				} else if (cnt.count >= Config.clusterThreshold4) {
					sendOreMessage(player, "prospectingpicks.message.found_ore_cluster_4", cnt, ChatFormatting.GOLD);
				}  else if (cnt.count >= Config.clusterThreshold3) {
					sendOreMessage(player, "prospectingpicks.message.found_ore_cluster_3", cnt, ChatFormatting.GOLD);
				} else if (cnt.count >= Config.clusterThreshold2) {
					sendOreMessage(player, "prospectingpicks.message.found_ore_cluster_2", cnt, ChatFormatting.GOLD);
				} else if (cnt.count >= Config.clusterThreshold1) {
					sendOreMessage(player, "prospectingpicks.message.found_ore_cluster_1", cnt, ChatFormatting.WHITE);
				} else {
					sendOreMessage(player, "prospectingpicks.message.found_ore_cluster_0", cnt, ChatFormatting.WHITE);
				}
			}
		}

		if (!foundSomething) {
			player.sendSystemMessage(
					Component.translatable("prospectingpicks.message.no_results").withStyle(ChatFormatting.GRAY)
			);
		}
	}

}
