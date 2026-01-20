package eig.prospectingpicks.util;

import eig.prospectingpicks.Config;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Set;

public class OreSearch {
	private static OreCounter[] makeOreCounter() {
		Set<OreTagConfig> ores = Config.getOres();
		OreCounter[] oreCounter = new OreCounter[ores.size()];

		int i = 0;
		for (OreTagConfig ore : ores) {
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

	private static void sendOreMessageNumeric(Player player, OreCounter cnt) {
		Component msg = Component.translatable(
				"prospectingpicks.message.found_ore_cluster_numeric",
				getOreTextComponent(cnt),
				String.valueOf(cnt.count)
		).withStyle(ChatFormatting.GOLD);

		player.sendSystemMessage(msg);
	}

	private static void sendOreVeinMessageNumeric(Player player, OreCounter cnt) {
		Component msg = Component.translatable(
				"prospectingpicks.message.found_ore_vein_numeric",
				getOreTextComponent(cnt),
				String.valueOf(cnt.count),
				String.valueOf(cnt.rawCount)
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
							if (block.is(ModTags.blockTag(ResourceLocation.parse("c:ores/" + cnt.ore.ore)))) {
								cnt.count++;
							}
							if (block.is(ModTags.blockTag(ResourceLocation.parse("c:storage_blocks/" + cnt.ore.rawOre)))) {
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
			if (cnt.rawCount >= Config.THRESHOLD_VEIN_0_RAWORE.get() && cnt.count >= Config.THRESHOLD_VEIN_0_ORE.get()) {
				// Found a vein!
				foundSomething = true;

				if (Config.DISPLAY_NUMERIC_COUNT.get()) {
					sendOreVeinMessageNumeric(player, cnt);
				} else if (cnt.rawCount >= Config.THRESHOLD_VEIN_2_RAWORE.get() && cnt.count >= Config.THRESHOLD_VEIN_2_ORE.get()) {
					sendOreMessage(player, "prospectingpicks.message.found_ore_vein_2", cnt, ChatFormatting.YELLOW);
				} else if (cnt.rawCount >= Config.THRESHOLD_VEIN_1_RAWORE.get() && cnt.count >= Config.THRESHOLD_VEIN_1_ORE.get()) {
					sendOreMessage(player, "prospectingpicks.message.found_ore_vein_1", cnt, ChatFormatting.YELLOW);
				} else {
					sendOreMessage(player, "prospectingpicks.message.found_ore_vein_0", cnt, ChatFormatting.YELLOW);
				}
			} else if (cnt.count >= Config.THRESHOLD_CLUSTER_0.get()) {
				// Found a regular ore cluster
				foundSomething = true;

				if (Config.DISPLAY_NUMERIC_COUNT.get()) {
					sendOreMessageNumeric(player, cnt);
				} else if (cnt.count >= Config.THRESHOLD_CLUSTER_4.get()) {
					sendOreMessage(player, "prospectingpicks.message.found_ore_cluster_4", cnt, ChatFormatting.GOLD);
				}  else if (cnt.count >= Config.THRESHOLD_CLUSTER_3.get()) {
					sendOreMessage(player, "prospectingpicks.message.found_ore_cluster_3", cnt, ChatFormatting.GOLD);
				} else if (cnt.count >= Config.THRESHOLD_CLUSTER_2.get()) {
					sendOreMessage(player, "prospectingpicks.message.found_ore_cluster_2", cnt, ChatFormatting.GOLD);
				} else if (cnt.count >= Config.THRESHOLD_CLUSTER_1.get()) {
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
