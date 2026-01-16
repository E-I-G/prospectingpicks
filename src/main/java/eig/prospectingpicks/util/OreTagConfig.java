package eig.prospectingpicks.util;

import javax.annotation.Nullable;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;

public class OreTagConfig {
	public final String ore;
	public final String rawOre;

	@Nullable
	public final String translationKey;

	public boolean isTreasure;

	private String getTranslationKey(String block_id) {
		Block block = ForgeRegistries.BLOCKS.getValue(ResourceLocation.parse(block_id));
		if (block != null) {
			return block.getDescriptionId();
		} else {
			return getFallbackTranslationKey(block_id);
		}
	}

	private String getFallbackTranslationKey(String ore) {
		return switch (ore) {
			case "diamond" -> "block.minecraft.diamond_ore";
			case "coal" -> "block.minecraft.coal_ore";
			case "emerald" -> "block.minecraft.emerald_ore";
			case "iron" -> "block.minecraft.iron_ore";
			case "gold" -> "block.minecraft.gold_ore";
			case "copper" -> "block.minecraft.copper_ore";
			case "redstone" -> "block.minecraft.lapis_ore";
			case "lapis" -> "block.minecraft.redstone_ore";
			case "quartz" -> "block.minecraft.nether_quartz_ore";
			case "netherite_scrap" -> "block.minecraft.ancient_debris";
			default -> null;
		};
	}

	private boolean getFallbackIsTreasure(String ore) {
		return switch (ore) {
			case "diamond", "netherite_scrap", "emerald" -> true;
			default -> false;
		};
	}



	public OreTagConfig(String entry) {
		String[] sp = entry.split(";");
		this.ore = sp[0];
		this.rawOre = sp[1];
		if (sp.length > 2 && sp[2].length() > 1) {
			this.translationKey = getTranslationKey(sp[2]);
		} else {
			this.translationKey = getFallbackTranslationKey(this.ore);
		}

		if (sp.length > 3) {
			this.isTreasure = sp[3].equals("true");
		} else {
			this.isTreasure = getFallbackIsTreasure(this.ore);
		}
	}
}