package net.minecraftforge.registries;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.commands.synchronization.ArgumentTypeInfo;

/**
 * Fabric-port shim of net.minecraftforge.registries.ForgeRegistries.
 * All constants back onto vanilla registries via RegistryAdapter.
 */
public class ForgeRegistries {

    public static final IForgeRegistry<Item> ITEMS = RegistryAdapter.of(BuiltInRegistries.ITEM, Registries.ITEM);
    public static final IForgeRegistry<Block> BLOCKS = RegistryAdapter.of(BuiltInRegistries.BLOCK, Registries.BLOCK);
    public static final IForgeRegistry<EntityType<?>> ENTITY_TYPES = RegistryAdapter.of(BuiltInRegistries.ENTITY_TYPE, Registries.ENTITY_TYPE);
    /** Forge 47 alias */
    public static final IForgeRegistry<EntityType<?>> ENTITIES = ENTITY_TYPES;
    public static final IForgeRegistry<MobEffect> MOB_EFFECTS = RegistryAdapter.of(BuiltInRegistries.MOB_EFFECT, Registries.MOB_EFFECT);
    public static final IForgeRegistry<SoundEvent> SOUND_EVENTS = RegistryAdapter.of(BuiltInRegistries.SOUND_EVENT, Registries.SOUND_EVENT);
    public static final IForgeRegistry<Potion> POTIONS = RegistryAdapter.of(BuiltInRegistries.POTION, Registries.POTION);
    public static final IForgeRegistry<Attribute> ATTRIBUTES = RegistryAdapter.of(BuiltInRegistries.ATTRIBUTE, Registries.ATTRIBUTE);
    public static final IForgeRegistry<ParticleType<?>> PARTICLE_TYPES = RegistryAdapter.of(BuiltInRegistries.PARTICLE_TYPE, Registries.PARTICLE_TYPE);
    public static final IForgeRegistry<MenuType<?>> MENU_TYPES = RegistryAdapter.of(BuiltInRegistries.MENU, Registries.MENU);
    public static final IForgeRegistry<BlockEntityType<?>> BLOCK_ENTITY_TYPES = RegistryAdapter.of(BuiltInRegistries.BLOCK_ENTITY_TYPE, Registries.BLOCK_ENTITY_TYPE);
    public static final IForgeRegistry<ArgumentTypeInfo<?, ?>> COMMAND_ARGUMENT_TYPES = RegistryAdapter.of(BuiltInRegistries.COMMAND_ARGUMENT_TYPE, Registries.COMMAND_ARGUMENT_TYPE);
    public static final IForgeRegistry<Biome> BIOMES = RegistryAdapter.ofKey(Registries.BIOME);

    public static class Keys {
        public static final ResourceKey<net.minecraft.core.Registry<com.mojang.serialization.Codec<? extends net.minecraftforge.common.loot.IGlobalLootModifier>>> GLOBAL_LOOT_MODIFIER_SERIALIZERS =
                ResourceKey.createRegistryKey(new net.minecraft.resources.ResourceLocation("forge", "global_loot_modifier_serializers"));
        public static final ResourceKey<net.minecraft.core.Registry<net.minecraftforge.common.world.BiomeModifier>> BIOME_MODIFIERS =
                ResourceKey.createRegistryKey(new net.minecraft.resources.ResourceLocation("forge", "biome_modifier"));
    }

    private ForgeRegistries() {
    }
}