package eig.prospectingpicks.registry;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.PickaxeItem;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

import static eig.prospectingpicks.ProspectingPicks.MODID;

public class ModItems {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(BuiltInRegistries.ITEM, MODID);
	public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

	public static final Supplier<PickaxeItem> COPPER_PROSPECTOR_PICK = ITEMS.register("copper_prospector_pick", () -> new PickaxeItem(
			ModTiers.COPPER_PROSP,
			new Item.Properties().attributes(
					PickaxeItem.createAttributes(
						ModTiers.COPPER_PROSP,
						1, -2.8f
					)
			)
	));

	public static final Supplier<PickaxeItem> SILVER_PROSPECTOR_PICK = ITEMS.register("silver_prospector_pick", () -> new PickaxeItem(
			ModTiers.SILVER_PROSP,
			new Item.Properties().attributes(
					PickaxeItem.createAttributes(
							ModTiers.SILVER_PROSP,
							1, -2.8f
					)
			)
	));

	public static final Supplier<PickaxeItem> BRONZE_PROSPECTOR_PICK = ITEMS.register("bronze_prospector_pick", () -> new PickaxeItem(
			ModTiers.BRONZE_PROSP,
			new Item.Properties().attributes(
					PickaxeItem.createAttributes(
							ModTiers.BRONZE_PROSP,
							1, -2.8f
					)
			)
	));

	public static final Supplier<PickaxeItem> IRON_PROSPECTOR_PICK = ITEMS.register("iron_prospector_pick", () -> new PickaxeItem(
			ModTiers.IRON_PROSP,
			new Item.Properties().attributes(
					PickaxeItem.createAttributes(
							ModTiers.IRON_PROSP,
							1, -2.8f
					)
			)
	));

	public static final Supplier<PickaxeItem> STEEL_PROSPECTOR_PICK = ITEMS.register("steel_prospector_pick", () -> new PickaxeItem(
			ModTiers.STEEL_PROSP,
			new Item.Properties().attributes(
					PickaxeItem.createAttributes(
							ModTiers.STEEL_PROSP,
							1, -2.8f
					)
			)
	));

	public static final Supplier<PickaxeItem> DIAMOND_PROSPECTOR_PICK = ITEMS.register("diamond_prospector_pick", () -> new PickaxeItem(
			ModTiers.DIAMOND_PROSP,
			new Item.Properties().attributes(
					PickaxeItem.createAttributes(
							ModTiers.DIAMOND_PROSP,
							1, -2.8f
					)
			)
	));

	public static final Supplier<PickaxeItem> NETHERITE_PROSPECTOR_PICK = ITEMS.register("netherite_prospector_pick", () -> new PickaxeItem(
			ModTiers.NETHERITE_PROSP,
			new Item.Properties().attributes(
					PickaxeItem.createAttributes(
							ModTiers.NETHERITE_PROSP,
							1, -2.8f
					)
			)
	));
}