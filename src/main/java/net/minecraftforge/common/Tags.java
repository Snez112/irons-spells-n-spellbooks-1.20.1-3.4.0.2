package net.minecraftforge.common;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

/**
 * Fabric-port shim for Forge's common Tags.
 */
public class Tags {

    public static class Blocks {
        public static final TagKey<Block> BOOKSHELVES = TagKey.create(Registries.BLOCK, new ResourceLocation("c", "bookshelves"));
    }

    public static class Items {
        public static final TagKey<Item> INGOTS_NETHERITE = TagKey.create(Registries.ITEM, new ResourceLocation("c", "netherite_ingots"));
    }
}
