package eig.prospectingpicks.registry;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ForgeItemTagsProvider;

import static net.minecraftforge.common.Tags.Blocks.NEEDS_NETHERITE_TOOL;

public class ModTiers {
	public static final Tier COPPER_PROSP = new ForgeTier(
			1,
			95,
			2,
			1,
			5,
			BlockTags.NEEDS_STONE_TOOL,
			() -> Ingredient.of(Tags.Items.INGOTS_COPPER)
	);

	public static final Tier SILVER_PROSP = new ForgeTier(
			2,
			125,
			3,
			2,
			5,
			BlockTags.NEEDS_IRON_TOOL,
			() -> Ingredient.of(ItemTags.create(ResourceLocation.parse("forge:ingots/silver")))
	);

	public static final Tier IRON_PROSP = new ForgeTier(
			2,
			125,
			3,
			2,
			5,
			BlockTags.NEEDS_IRON_TOOL,
			() -> Ingredient.of(Tags.Items.INGOTS_IRON)
	);

	public static final Tier STEEL_PROSP = new ForgeTier(
			2,
			190,
			3,
			2,
			5,
			BlockTags.NEEDS_IRON_TOOL,
			() -> Ingredient.of(ItemTags.create(ResourceLocation.parse("forge:ingots/steel")))
	);

	public static final Tier BRONZE_PROSP = new ForgeTier(
			2,
			105,
			2,
			2,
			5,
			BlockTags.NEEDS_IRON_TOOL,
			() -> Ingredient.of(ItemTags.create(ResourceLocation.parse("forge:ingots/bronze")))
	);

	public static final Tier DIAMOND_PROSP = new ForgeTier(
			3,
			400,
			4,
			3,
			5,
			BlockTags.NEEDS_DIAMOND_TOOL,
			() -> Ingredient.of(Items.DIAMOND)
	);

	public static final Tier NETHERITE_PROSP = new ForgeTier(
			4,
			550,
			5,
			4,
			5,
			NEEDS_NETHERITE_TOOL,
			() -> Ingredient.of(Items.NETHERITE_INGOT)
	);
}
