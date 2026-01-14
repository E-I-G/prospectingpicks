package eig.prospectingpicks;

import eig.prospectingpicks.util.OreTagPair;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Mod.EventBusSubscriber(modid = ProspectingPicks.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config {
	private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

	private static final ForgeConfigSpec.BooleanValue ENABLE_SILVER_TOOL = BUILDER
			.define("enableTierSilver", false);

	private static final ForgeConfigSpec.BooleanValue ENABLE_BRONZE_TOOL = BUILDER
			.define("enableTierBronze", false);

	private static final ForgeConfigSpec.BooleanValue ENABLE_STEEL_TOOL = BUILDER
			.define("enableTierSteel", false);

	private static final ForgeConfigSpec.BooleanValue DISPLAY_NUMERIC_COUNT = BUILDER
			.comment("Display ore count numerically instead of descriptively")
			.define("displayNumericCount", false);

	private static final ForgeConfigSpec.IntValue SEARCH_RADIUS = BUILDER
			.comment("Spherical ore search radius")
			.defineInRange("searchRadius", 7, 1, 32);

	private static final ForgeConfigSpec.IntValue THRESHOLD_CLUSTER_1 = BUILDER
		.comment("Cluster threshold 1 - labeled as moderate amounts")
		.defineInRange("clusterThreshold1", 3, 1, Integer.MAX_VALUE);

	private static final ForgeConfigSpec.IntValue THRESHOLD_CLUSTER_2 = BUILDER
		.comment("Cluster threshold 2 - labeled as large amounts")
		.defineInRange("clusterThreshold2", 9, 1, Integer.MAX_VALUE);

	private static final ForgeConfigSpec.IntValue THRESHOLD_CLUSTER_3 = BUILDER
		.comment("Cluster threshold 3 - labeled as massive amounts")
		.defineInRange("clusterThreshold3", 32, 1, Integer.MAX_VALUE);

	private static final ForgeConfigSpec.IntValue THRESHOLD_CLUSTER_4 = BUILDER
		.comment("Cluster threshold 4 - labeled as extraordinary amounts")
		.defineInRange("clusterThreshold4", 72, 1, Integer.MAX_VALUE);

	private static final ForgeConfigSpec.IntValue THRESHOLD_VEIN_0_RAWORE = BUILDER
		.comment("Vein raw ore threshold 0 - number of raw ore blocks needed to confirm vein detection")
		.defineInRange("veinThreshold0_rawOre", 1, 1, Integer.MAX_VALUE);

	private static final ForgeConfigSpec.IntValue THRESHOLD_VEIN_0_ORE = BUILDER
		.comment("Vein raw ore threshold 0 - number of ores needed to confirm vein detection")
		.defineInRange("veinThreshold0_ore", 1, 1, Integer.MAX_VALUE);

	private static final ForgeConfigSpec.IntValue THRESHOLD_VEIN_1_RAWORE = BUILDER
		.comment("Vein raw ore threshold 1 - number of raw ore blocks needed to label vein as large")
		.defineInRange("veinThreshold1_rawOre", 2, 1, Integer.MAX_VALUE);

	private static final ForgeConfigSpec.IntValue THRESHOLD_VEIN_1_ORE = BUILDER
		.comment("Vein ore threshold 1 - number of ores needed to label vein as large")
		.defineInRange("veinThreshold1_ore", 5, 1, Integer.MAX_VALUE);

	private static final ForgeConfigSpec.IntValue THRESHOLD_VEIN_2_RAWORE = BUILDER
		.comment("Vein raw ore threshold 2 - number of raw ore blocks needed to label vein as enormous")
		.defineInRange("veinThreshold2_rawOre", 5, 1, Integer.MAX_VALUE);

	private static final ForgeConfigSpec.IntValue THRESHOLD_VEIN_2_ORE = BUILDER
		.comment("Vein ore threshold 2 - number of ores needed to label vein as enormous")
		.defineInRange("veinThreshold2_ore", 24, 1, Integer.MAX_VALUE);


	private static final ForgeConfigSpec.ConfigValue<List<? extends String>> ORE_STRINGS = BUILDER
			.comment("A list of ore tags to search for. Format: ore;raw_ore")
			.defineListAllowEmpty("ores", Arrays.asList(
					"coal;coal",
					"diamond;diamond",
					"lapis;lapis",
					"emerald;emerald",
					"iron;raw_iron",
					"redstone;redstone",
					"gold;raw_gold",
					"copper;raw_copper",
					"silver;raw_silver",
					"platinum;raw_platinum",
					"uranium;raw_uranium",
					"tin;raw_tin",
					"zinc;raw_zinc",
					"aluminum;raw_aluminum"
			), Config::validateOreListValue);


	static final ForgeConfigSpec SPEC = BUILDER.build();

	public static boolean enableTierSilver;
	public static boolean enableTierBronze;
	public static boolean enableTierSteel;

	public static boolean displayNumericCount;

	public static int searchRadius;

	public static int clusterThreshold1;
	public static int clusterThreshold2;
	public static int clusterThreshold3;
	public static int clusterThreshold4;

	public static int veinThreshold0_rawOre;
	public static int veinThreshold0_ore;
	public static int veinThreshold1_rawOre;
	public static int veinThreshold1_ore;
	public static int veinThreshold2_rawOre;
	public static int veinThreshold2_ore;

	public static Set<OreTagPair> ores;


	private static boolean validateOreListValue(final Object obj) {
		return obj instanceof final String entry && StringUtils.countMatches(entry, ';') == 1;
	}

	@SubscribeEvent
	static void onLoad(final ModConfigEvent event) {
		enableTierSilver = ENABLE_SILVER_TOOL.get();
		enableTierBronze = ENABLE_BRONZE_TOOL.get();
		enableTierSteel = ENABLE_STEEL_TOOL.get();

		displayNumericCount = DISPLAY_NUMERIC_COUNT.get();

		searchRadius = SEARCH_RADIUS.get();

		clusterThreshold1 = THRESHOLD_CLUSTER_1.get();
		clusterThreshold2 = THRESHOLD_CLUSTER_2.get();
		clusterThreshold3 = THRESHOLD_CLUSTER_3.get();
		clusterThreshold4 = THRESHOLD_CLUSTER_4.get();

		veinThreshold0_rawOre = THRESHOLD_VEIN_0_RAWORE.get();
		veinThreshold0_ore = THRESHOLD_VEIN_0_ORE.get();
		veinThreshold1_rawOre = THRESHOLD_VEIN_1_RAWORE.get();
		veinThreshold1_ore = THRESHOLD_VEIN_1_ORE.get();
		veinThreshold2_rawOre = THRESHOLD_VEIN_2_RAWORE.get();
		veinThreshold2_ore = THRESHOLD_VEIN_2_ORE.get();

		List<? extends String> itemStrings = ORE_STRINGS.get();

		ores = new HashSet<OreTagPair>();
		itemStrings.forEach((String value) -> {
			if (StringUtils.countMatches(value, ';') == 1) {
				ores.add(new OreTagPair(value));
			}
		});
	}
}
