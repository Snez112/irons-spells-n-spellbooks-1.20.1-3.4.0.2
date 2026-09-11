package net.minecraftforge.event.entity;

import net.minecraft.world.entity.Entity;
import net.minecraftforge.eventbus.api.Cancelable;

@Cancelable
public class EntityMountEvent extends EntityEvent {

    private final Entity entityMounting;
    private final Entity entityBeingMounted;
    private final boolean isMounting;

    public EntityMountEvent(Entity entityMounting, Entity entityBeingMounted, boolean isMounting) {
        super(entityMounting);
        this.entityMounting = entityMounting;
        this.entityBeingMounted = entityBeingMounted;
        this.isMounting = isMounting;
    }

    public EntityMountEvent(Entity entityMounting, Entity entityBeingMounted) {
        this(entityMounting, entityBeingMounted, true);
    }

    public Entity getEntityMounting() {
        return entityMounting;
    }

    public Entity getEntityBeingMounted() {
        return entityBeingMounted;
    }

    public Entity getEntityMountingOn() {
        return entityBeingMounted;
    }

    public boolean isMounting() {
        return isMounting;
    }

    public boolean isDismounting() {
        return !isMounting;
    }
}