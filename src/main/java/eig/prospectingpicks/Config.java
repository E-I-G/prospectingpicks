package eig.prospectingpicks;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import eig.prospectingpicks.util.OreTagConfig;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.StringUtils;


public class Config {
	private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

	public static final ModConfigSpec.BooleanValue ENABLE_SILVER_TOOL = BUILDER
			.define("enableTierSilver", false);

	public static final ModConfigSpec.BooleanValue ENABLE_BRONZE_TOOL = BUILDER
			.define("enableTierBronze", false);

	public static final ModConfigSpec.BooleanValue ENABLE_STEEL_TOOL = BUILDER
			.define("enableTierSteel", false);

	public static final ModConfigSpec.BooleanValue DISPLAY_NUMERIC_COUNT = BUILDER
			.comment("Display ore count numerically instead of descriptively")
			.define("displayNumericCount", false);

	public static final ModConfigSpec.IntValue SEARCH_RADIUS = BUILDER
			.comment("Spherical ore search radius")
			.defineInRange("searchRadius", 7, 1, 32);

	public static final ModConfigSpec.IntValue THRESHOLD_CLUSTER_0 = BUILDER
			.comment("Cluster threshold 0 - min number of ores found for detection")
			.defineInRange("clusterThreshold0", 1, 1, Integer.MAX_VALUE);

	public static final ModConfigSpec.IntValue THRESHOLD_CLUSTER_1 = BUILDER
			.comment("Cluster threshold 1 - labeled as moderate amounts")
			.defineInRange("clusterThreshold1", 3, 1, Integer.MAX_VALUE);

	public static final ModConfigSpec.IntValue THRESHOLD_CLUSTER_2 = BUILDER
			.comment("Cluster threshold 2 - labeled as large amounts")
			.defineInRange("clusterThreshold2", 9, 1, Integer.MAX_VALUE);

	public static final ModConfigSpec.IntValue THRESHOLD_CLUSTER_3 = BUILDER
			.comment("Cluster threshold 3 - labeled as massive amounts")
			.defineInRange("clusterThreshold3", 32, 1, Integer.MAX_VALUE);

	public static final ModConfigSpec.IntValue THRESHOLD_CLUSTER_4 = BUILDER
			.comment("Cluster threshold 4 - labeled as extraordinary amounts")
			.defineInRange("clusterThreshold4", 72, 1, Integer.MAX_VALUE);

	public static final ModConfigSpec.IntValue THRESHOLD_VEIN_0_RAWORE = BUILDER
			.comment("Vein raw ore threshold 0 - number of raw ore blocks needed to confirm vein detection")
			.defineInRange("veinThreshold0_rawOre", 1, 1, Integer.MAX_VALUE);

	public static final ModConfigSpec.IntValue THRESHOLD_VEIN_0_ORE = BUILDER
			.comment("Vein raw ore threshold 0 - number of ores needed to confirm vein detection")
			.defineInRange("veinThreshold0_ore", 1, 1, Integer.MAX_VALUE);

	public static final ModConfigSpec.IntValue THRESHOLD_VEIN_1_RAWORE = BUILDER
			.comment("Vein raw ore threshold 1 - number of raw ore blocks needed to label vein as large")
			.defineInRange("veinThreshold1_rawOre", 2, 1, Integer.MAX_VALUE);

	public static final ModConfigSpec.IntValue THRESHOLD_VEIN_1_ORE = BUILDER
			.comment("Vein ore threshold 1 - number of ores needed to label vein as large")
			.defineInRange("veinThreshold1_ore", 5, 1, Integer.MAX_VALUE);

	public static final ModConfigSpec.IntValue THRESHOLD_VEIN_2_RAWORE = BUILDER
			.comment("Vein raw ore threshold 2 - number of raw ore blocks needed to label vein as enormous")
			.defineInRange("veinThreshold2_rawOre", 5, 1, Integer.MAX_VALUE);

	public static final ModConfigSpec.IntValue THRESHOLD_VEIN_2_ORE = BUILDER
			.comment("Vein ore threshold 2 - number of ores needed to label vein as enormous")
			.defineInRange("veinThreshold2_ore", 24, 1, Integer.MAX_VALUE);


	private static final ModConfigSpec.ConfigValue<List<? extends String>> ORE_STRINGS = BUILDER
			.comment("A list of ore tags to search for. Format: ore_tag;raw_ore_tag;[canonical_block_id];is_treasure. canonical_block_id is used to optionally fetch the translation key.")
			.defineListAllowEmpty("ores", Arrays.asList(
					"coal;coal;;false",
					"diamond;diamond;;true",
					"lapis;lapis;;false",
					"emerald;emerald;;true",
					"iron;raw_iron;;false",
					"redstone;redstone;;false",
					"gold;raw_gold;;false",
					"copper;raw_copper;;false",
					"quartz;quartz;;false",
					"netherite_scrap;netherite;;true",
					"silver;raw_silver;;false",
					"platinum;raw_platinum;;true",
					"uranium;raw_uranium;;false",
					"tin;raw_tin;;false",
					"zinc;raw_zinc;;false",
					"aluminum;raw_aluminum;;false",
					"sapphire;sapphire;;true",
					"ruby;ruby;;false",
					"tungsten;raw_tungsten;;false",
					"osmium;raw_osmium;;false",
					"bismuth;raw_bismuth;;false",
					"fluorite;fluorite;;false",
					"nickel;raw_nickel;;false",
					"sulfur;sulfur;;false",
					"niter;niter;;false",
					"cinnabar;cinnabar;;false",
					"apatite;apatite;;false"
			), () -> {return "";}, Config::validateOreListValue);


	static final ModConfigSpec SPEC = BUILDER.build();

	public static Set<OreTagConfig> getOres() {
		Set<OreTagConfig> ores = new HashSet<>();
		ORE_STRINGS.get().forEach((String value) -> {
			if (StringUtils.countMatches(value, ';') >= 1) {
				ores.add(new OreTagConfig(value));
			}
		});

		return ores;
	};




	private static boolean validateOreListValue(final Object obj) {
		return obj instanceof final String entry && StringUtils.countMatches(entry, ';') >= 1;
	}
}
