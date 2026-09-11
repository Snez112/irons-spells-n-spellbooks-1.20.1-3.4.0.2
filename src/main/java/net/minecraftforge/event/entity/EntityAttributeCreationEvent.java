package net.minecraftforge.event.entity;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.eventbus.api.Event;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Fabric-port shim of Forge's EntityAttributeCreationEvent.
 */
public class EntityAttributeCreationEvent extends Event {

    private final Map<EntityType<?>, net.minecraft.world.entity.ai.attributes.AttributeSupplier> attributes = new ConcurrentHashMap<>();

    public void put(EntityType<?> type, net.minecraft.world.entity.ai.attributes.AttributeSupplier supplier) {
        attributes.put(type, supplier);
    }

    public Map<EntityType<?>, net.minecraft.world.entity.ai.attributes.AttributeSupplier> getPartiallyBuiltAttributes() {
        return attributes;
    }

    public boolean isModifiable() {
        return true;
    }
}