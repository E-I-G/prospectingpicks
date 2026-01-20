package eig.prospectingpicks.registry;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

import net.neoforged.neoforge.common.SimpleTier;
import net.neoforged.neoforge.common.Tags;


public class ModTiers {
	public static final Tier COPPER_PROSP = new SimpleTier(
			BlockTags.INCORRECT_FOR_STONE_TOOL,
			95,
			2f,
			1f,
			5,
			() -> Ingredient.of(Tags.Items.INGOTS_COPPER)
	);

	public static final Tier SILVER_PROSP = new SimpleTier(
			BlockTags.INCORRECT_FOR_IRON_TOOL,
			125,
			3f,
			2f,
			5,
			() -> Ingredient.of(ItemTags.create(ResourceLocation.parse("forge:ingots/silver")))
	);

	public static final Tier IRON_PROSP = new SimpleTier(
			BlockTags.INCORRECT_FOR_IRON_TOOL,
			125,
			3f,
			2f,
			5,
			() -> Ingredient.of(Tags.Items.INGOTS_IRON)
	);

	public static final Tier STEEL_PROSP = new SimpleTier(
			BlockTags.INCORRECT_FOR_IRON_TOOL,
			190,
			3f,
			2f,
			5,
			() -> Ingredient.of(ItemTags.create(ResourceLocation.parse("forge:ingots/steel")))
	);

	public static final Tier BRONZE_PROSP = new SimpleTier(
			BlockTags.INCORRECT_FOR_IRON_TOOL,
			105,
			2f,
			2f,
			5,
			() -> Ingredient.of(ItemTags.create(ResourceLocation.parse("forge:ingots/bronze")))
	);

	public static final Tier DIAMOND_PROSP = new SimpleTier(
			BlockTags.INCORRECT_FOR_DIAMOND_TOOL,
			400,
			4f,
			3f,
			5,
			() -> Ingredient.of(Items.DIAMOND)
	);

	public static final Tier NETHERITE_PROSP = new SimpleTier(
			BlockTags.INCORRECT_FOR_NETHERITE_TOOL,
			550,
			5,
			4,
			5,
			() -> Ingredient.of(Items.NETHERITE_INGOT)
	);

}
