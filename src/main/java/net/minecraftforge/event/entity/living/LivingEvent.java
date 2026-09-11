package net.minecraftforge.event.entity.living;

import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.EntityEvent;

/**
 * Fabric-port shim base of Forge's LivingEvent.
 */
public class LivingEvent extends EntityEvent {

    private final LivingEntity entity;

    public LivingEvent(LivingEntity entity) {
        super(entity);
        this.entity = entity;
    }

    @Override
    public LivingEntity getEntity() {
        return entity;
    }

    public static class LivingTickEvent extends LivingEvent {
        public LivingTickEvent(LivingEntity entity) {
            super(entity);
        }
    }
}