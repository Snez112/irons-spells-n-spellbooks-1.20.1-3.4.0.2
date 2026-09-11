package net.minecraftforge.common.world;

import net.minecraft.core.HolderSet;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class ForgeBiomeModifiers {

    public record AddFeaturesBiomeModifier(
            HolderSet<Biome> biomes,
            HolderSet<PlacedFeature> features,
            GenerationStep.Decoration step
    ) implements BiomeModifier {}
}
