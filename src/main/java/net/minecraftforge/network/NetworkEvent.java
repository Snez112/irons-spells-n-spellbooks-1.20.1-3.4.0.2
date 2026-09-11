package net.minecraftforge.network;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

import javax.annotation.Nullable;
import java.util.function.Supplier;

/**
 * Fabric-port shim: context supplied to message handlers.
 */
public class NetworkEvent {

    public static class Context {
        private final ResourceLocation channel;
        private final NetworkDirection direction;
        private final ServerPlayer sender;

        public Context(ResourceLocation channel, NetworkDirection direction) {
            this(channel, direction, null);
        }

        public Context(ResourceLocation channel, NetworkDirection direction, ServerPlayer sender) {
            this.channel = channel;
            this.direction = direction;
            this.sender = sender;
        }

        @Nullable
        public ServerPlayer getSender() {
            return sender;
        }

        public NetworkDirection getDirection() {
            return direction;
        }

        public ResourceLocation getChannel() {
            return channel;
        }

        public void enqueueWork(Runnable work) {
            work.run();
        }

        public void setPacketHandled(boolean handled) {
        }

        public boolean getPacketHandled() {
            return true;
        }
    }
}