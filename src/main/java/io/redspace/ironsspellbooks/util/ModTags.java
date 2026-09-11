package io.redspace.ironsspellbooks.util;

import io.redspace.ironsspellbooks.IronsSpellbooks;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.structure.Structure;

public class ModTags {
    public static final TagKey<Item> SCHOOL_FOCUS = TagKey.create(Registries.ITEM, new ResourceLocation(IronsSpellbooks.MODID, "school_focus"));
    public static final TagKey<Item> FIRE_FOCUS = TagKey.create(Registries.ITEM, new ResourceLocation(IronsSpellbooks.MODID, "fire_focus"));
    public static final TagKey<Item> ICE_FOCUS = TagKey.create(Registries.ITEM, new ResourceLocation(IronsSpellbooks.MODID, "ice_focus"));
    public static final TagKey<Item> LIGHTNING_FOCUS = TagKey.create(Registries.ITEM, new ResourceLocation(IronsSpellbooks.MODID, "lightning_focus"));
    public static final TagKey<Item> ENDER_FOCUS = TagKey.create(Registries.ITEM, new ResourceLocation(IronsSpellbooks.MODID, "ender_focus"));
    public static final TagKey<Item> HOLY_FOCUS = TagKey.create(Registries.ITEM, new ResourceLocation(IronsSpellbooks.MODID, "holy_focus"));
    public static final TagKey<Item> BLOOD_FOCUS = TagKey.create(Registries.ITEM, new ResourceLocation(IronsSpellbooks.MODID, "blood_focus"));
    public static final TagKey<Item> EVOCATION_FOCUS = TagKey.create(Registries.ITEM, new ResourceLocation(IronsSpellbooks.MODID, "evocation_focus"));
    public static final TagKey<Item> ELDRITCH_FOCUS = TagKey.create(Registries.ITEM, new ResourceLocation(IronsSpellbooks.MODID, "eldritch_focus"));
    public static final TagKey<Item> NATURE_FOCUS = TagKey.create(Registries.ITEM, new ResourceLocation(IronsSpellbooks.MODID, "nature_focus"));
    public static final TagKey<Item> INSCRIBED_RUNES = TagKey.create(Registries.ITEM, new ResourceLocation(IronsSpellbooks.MODID, "inscribed_rune"));
    public static final TagKey<Block> SPECTRAL_HAMMER_MINEABLE = TagKey.create(Registries.BLOCK, new ResourceLocation(IronsSpellbooks.MODID, "spectral_hammer_mineable"));
    public static final TagKey<Block> GUARDED_BY_WIZARDS = TagKey.create(Registries.BLOCK, new ResourceLocation(IronsSpellbooks.MODID, "guarded_by_wizards"));

    public static final TagKey<Structure> WAYWARD_COMPASS_LOCATOR = TagKey.create(Registries.STRUCTURE, new ResourceLocation(IronsSpellbooks.MODID, "wayward_compass_locator"));

    public static final TagKey<EntityType<?>> ALWAYS_HEAL = TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation(IronsSpellbooks.MODID, "always_heal"));
    public static final TagKey<EntityType<?>> CANT_ROOT = TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation(IronsSpellbooks.MODID, "cant_root"));
    public static final TagKey<EntityType<?>> VILLAGE_ALLIES = TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation(IronsSpellbooks.MODID, "village_allies"));
    public static final TagKey<EntityType<?>> CANT_USE_PORTAL = TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation(IronsSpellbooks.MODID, "cant_use_portal"));

    public static final TagKey<Biome> NO_DEFAULT_SPAWNS = TagKey.create(Registries.BIOME, new ResourceLocation("forge", "no_default_monsters"));

    private static TagKey<DamageType> createDamageType(String tag) {
        return TagKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(IronsSpellbooks.MODID, tag));
    }

    public static final TagKey<DamageType> BYPASS_EVASION = createDamageType("bypass_evasion");
    public static final TagKey<DamageType> LONG_CAST_IGNORE = createDamageType("long_cast_ignore");
}
