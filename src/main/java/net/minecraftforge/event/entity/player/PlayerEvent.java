package net.minecraftforge.event.entity.player;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.EntityEvent;

/**
 * Fabric-port shim base of Forge's PlayerEvent.
 */
public class PlayerEvent extends EntityEvent {

    private final Player player;

    public PlayerEvent(Player player) {
        super(player);
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public Player getEntity() {
        return player;
    }

    public static class PlayerLoggedInEvent extends PlayerEvent {
        public PlayerLoggedInEvent(Player player) {
            super(player);
        }
    }

    public static class PlayerLoggedOutEvent extends PlayerEvent {
        public PlayerLoggedOutEvent(Player player) {
            super(player);
        }
    }

    public static class StartTracking extends PlayerEvent {
        private final Entity target;

        public StartTracking(Player player, Entity target) {
            super(player);
            this.target = target;
        }

        public Entity getTarget() {
            return target;
        }
    }

    public static class StopTracking extends PlayerEvent {
        private final Entity target;

        public StopTracking(Player player, Entity target) {
            super(player);
            this.target = target;
        }

        public Entity getTarget() {
            return target;
        }
    }

    public static class Clone extends PlayerEvent {
        private final Player original;
        private final boolean wasDeath;

        public Clone(Player _new, Player original, boolean wasDeath) {
            super(_new);
            this.original = original;
            this.wasDeath = wasDeath;
        }

        public Player getOriginal() {
            return original;
        }

        public boolean isWasDeath() {
            return wasDeath;
        }
    }

    public static class PlayerChangedDimensionEvent extends PlayerEvent {
        private final ResourceKey<Level> from;
        private final ResourceKey<Level> to;

        public PlayerChangedDimensionEvent(Player player, ResourceKey<Level> from, ResourceKey<Level> to) {
            super(player);
            this.from = from;
            this.to = to;
        }

        public ResourceKey<Level> getFrom() {
            return from;
        }

        public ResourceKey<Level> getTo() {
            return to;
        }
    }

    public static class PlayerRespawnEvent extends PlayerEvent {
        private final boolean endConquered;

        public PlayerRespawnEvent(Player player, boolean endConquered) {
            super(player);
            this.endConquered = endConquered;
        }

        public boolean isEndConquered() {
            return endConquered;
        }
    }

    @net.minecraftforge.eventbus.api.Cancelable
    public static class BreakSpeed extends PlayerEvent {
        private final net.minecraft.world.level.block.state.BlockState state;
        private final float originalSpeed;
        private float newSpeed;
        private final java.util.Optional<net.minecraft.core.BlockPos> position;

        public BreakSpeed(Player player, net.minecraft.world.level.block.state.BlockState state, float originalSpeed, net.minecraft.core.BlockPos pos) {
            super(player);
            this.state = state;
            this.originalSpeed = originalSpeed;
            this.newSpeed = originalSpeed;
            this.position = java.util.Optional.ofNullable(pos);
        }

        public net.minecraft.world.level.block.state.BlockState getState() { return state; }
        public float getOriginalSpeed() { return originalSpeed; }
        public float getNewSpeed() { return newSpeed; }
        public void setNewSpeed(float newSpeed) { this.newSpeed = newSpeed; }
        public java.util.Optional<net.minecraft.core.BlockPos> getPosition() { return position; }
    }
}