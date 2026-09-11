package net.minecraftforge.event.entity;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.eventbus.api.Event;

import javax.annotation.Nullable;

/**
 * Fabric-port shim of Forge's SpawnPlacementRegisterEvent. On Fabric, spawn
 * predicates cannot be registered through the vanilla spawner; registrations
 * are recorded but not applied.
 */
public class SpawnPlacementRegisterEvent extends Event {

    public enum Operation {
        AND,
        OR,
        REPLACE
    }

    public <T extends Entity> void register(EntityType<T> entityType, SpawnPlacements.SpawnPredicate<T> predicate) {
        register(entityType, null, null, predicate, Operation.OR);
    }

    public <T extends Entity> void register(EntityType<T> entityType, SpawnPlacements.SpawnPredicate<T> predicate, Operation operation) {
        register(entityType, null, null, predicate, operation);
    }

    public <T extends Entity> void register(EntityType<T> entityType, @Nullable SpawnPlacements.Type placementType, @Nullable Heightmap.Types heightmap, SpawnPlacements.SpawnPredicate<T> predicate, Operation operation) {
        // no-op: Fabric does not support custom spawn predicates
    }
}