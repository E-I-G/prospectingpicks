package eig.prospectingpicks.util;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ModTags {
	public static TagKey<Item> itemTag(final ResourceLocation name) {
		return TagKey.create(Registries.ITEM, name);
	}

	public static TagKey<Block> blockTag(final ResourceLocation name) {
		return TagKey.create(Registries.BLOCK, name);
	}

	public static final TagKey<Item> PROSPECTING_PICK = itemTag(ResourceLocation.parse("prospectingpicks:prospecting_pick"));
}
