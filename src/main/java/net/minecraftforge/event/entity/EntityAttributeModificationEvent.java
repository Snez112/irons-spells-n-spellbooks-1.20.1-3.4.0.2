package net.minecraftforge.event.entity;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraftforge.eventbus.api.Event;

import java.util.ArrayList;
import java.util.List;

/**
 * Fabric-port shim of Forge's EntityAttributeModificationEvent.
 */
public class EntityAttributeModificationEvent extends Event {

    public EntityAttributeModificationEvent() {
    }

    @SuppressWarnings("unchecked")
    public List<EntityType<? extends LivingEntity>> getTypes() {
        List<EntityType<? extends LivingEntity>> list = new ArrayList<>();
        for (EntityType<?> type : BuiltInRegistries.ENTITY_TYPE) {
            list.add((EntityType<? extends LivingEntity>) type);
        }
        return list;
    }

    public void add(EntityType<? extends LivingEntity> entityType, Attribute attribute) {
    }

    public void add(EntityType<? extends LivingEntity> entityType, Attribute attribute, double baseValue) {
    }
}