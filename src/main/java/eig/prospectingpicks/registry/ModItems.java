package eig.prospectingpicks.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;

import net.minecraft.world.item.PickaxeItem;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static eig.prospectingpicks.ProspectingPicks.MODID;

public class ModItems {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
	public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

	public static final RegistryObject<Item> COPPER_PROSPECTOR_PICK = ITEMS.register(
			"copper_prospector_pick",
			() -> new PickaxeItem(ModTiers.COPPER_PROSP, 1, -2.8f, new Item.Properties())
	);

	public static final RegistryObject<Item> SILVER_PROSPECTOR_PICK = ITEMS.register(
			"silver_prospector_pick",
			() -> new PickaxeItem(ModTiers.SILVER_PROSP, 1, -2.8f, new Item.Properties())
	);

	public static final RegistryObject<Item> IRON_PROSPECTOR_PICK = ITEMS.register(
			"iron_prospector_pick",
			() -> new PickaxeItem(ModTiers.IRON_PROSP, 1, -2.8f, new Item.Properties())
	);

	public static final RegistryObject<Item> BRONZE_PROSPECTOR_PICK = ITEMS.register(
			"bronze_prospector_pick",
			() -> new PickaxeItem(ModTiers.BRONZE_PROSP, 1, -2.8f, new Item.Properties())
	);

	public static final RegistryObject<Item> STEEL_PROSPECTOR_PICK = ITEMS.register(
			"steel_prospector_pick",
			() -> new PickaxeItem(ModTiers.STEEL_PROSP, 1, -2.8f, new Item.Properties())
	);

	public static final RegistryObject<Item> DIAMOND_PROSPECTOR_PICK = ITEMS.register(
			"diamond_prospector_pick",
			() -> new PickaxeItem(ModTiers.DIAMOND_PROSP, 1, -2.8f, new Item.Properties())
	);

	public static final RegistryObject<Item> NETHERITE_PROSPECTOR_PICK = ITEMS.register(
			"netherite_prospector_pick",
			() -> new PickaxeItem(ModTiers.NETHERITE_PROSP, 1, -2.8f, new Item.Properties())
	);
}
