package net.minecraftforge.event.entity;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.event.EntityEvent;

/**
 * Fabric-port shim of Forge's EntityJoinLevelEvent.
 */
public class EntityJoinLevelEvent extends EntityEvent {

    private final ServerLevel level;

    public EntityJoinLevelEvent(Entity entity, ServerLevel level) {
        super(entity);
        this.level = level;
    }

    public ServerLevel getLevel() {
        return level;
    }
}