package net.minecraftforge.event;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.fml.LogicalSide;

/**
 * Fabric-port shim of Forge's TickEvent.
 */
public class TickEvent extends Event {

    public enum Phase {
        START,
        END
    }

    public final Phase phase;
    public final LogicalSide side;

    public TickEvent(Phase phase, LogicalSide side) {
        this.phase = phase;
        this.side = side;
    }

    public Phase getPhase() {
        return phase;
    }

    public static class ServerTickEvent extends Event {
        public final Phase phase;
        private final MinecraftServer server;

        public ServerTickEvent(Phase phase, MinecraftServer server) {
            this.phase = phase;
            this.server = server;
        }

        public MinecraftServer getServer() {
            return server;
        }
    }

    public static class ClientTickEvent extends Event {
        public final Phase phase;

        public ClientTickEvent(Phase phase) {
            this.phase = phase;
        }
    }

    public static class LevelTickEvent extends Event {
        public final Level level;
        public final Phase phase;

        public LevelTickEvent(Level level, Phase phase) {
            this.level = level;
            this.phase = phase;
        }
    }

    public static class PlayerTickEvent extends Event {
        public final Player player;
        public final Phase phase;
        public final LogicalSide side;

        public PlayerTickEvent(Player player, Phase phase, LogicalSide side) {
            this.player = player;
            this.phase = phase;
            this.side = side;
        }
    }
}