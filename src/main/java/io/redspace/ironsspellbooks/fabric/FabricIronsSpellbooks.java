package io.redspace.ironsspellbooks.fabric;

import io.redspace.ironsspellbooks.IronsSpellbooks;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.GenerationStep;

/**
 * Fabric entry point (fabric.mod.json "main").
 */
public class FabricIronsSpellbooks implements ModInitializer {

    @Override
    public void onInitialize() {
        IronsSpellbooks.init();

        BiomeModifications.addFeature(
                BiomeSelectors.foundInOverworld(),
                GenerationStep.Decoration.UNDERGROUND_ORES,
                ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation(IronsSpellbooks.MODID, "ore_arcane_debris"))
        );
    }
}