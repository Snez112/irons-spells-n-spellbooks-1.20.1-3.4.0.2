package net.minecraftforge.event.entity;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraftforge.eventbus.api.Event;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Fabric-port shim of Forge's EntityAttributeCreationEvent.
 */
public class EntityAttributeCreationEvent extends Event {

    private final Map<EntityType<?>, AttributeSupplier> attributes = new ConcurrentHashMap<>();

    @SuppressWarnings("unchecked")
    public void put(EntityType<?> type, AttributeSupplier supplier) {
        if (type != null && supplier != null) {
            attributes.put(type, supplier);
            try {
                FabricDefaultAttributeRegistry.register((EntityType<? extends LivingEntity>) type, supplier);
            } catch (Exception e) {
                // Ignore if already registered or incompatible
            }
        }
    }

    public Map<EntityType<?>, AttributeSupplier> getPartiallyBuiltAttributes() {
        return attributes;
    }

    public boolean isModifiable() {
        return true;
    }
}