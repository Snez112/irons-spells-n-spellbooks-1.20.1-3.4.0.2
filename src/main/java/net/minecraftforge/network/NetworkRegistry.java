package net.minecraftforge.network;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Fabric-port shim of NetworkRegistry: a channel is an application-level
 * packet stream multiplexed over a single fabric-networking channel.
 */
public class NetworkRegistry {

    public static class ChannelBuilder {
        private ResourceLocation name;
        private Supplier<String> networkProtocolVersion = () -> "1.0";
        private Predicate<String> clientAcceptedVersions = s -> true;
        private Predicate<String> serverAcceptedVersions = s -> true;

        public static ChannelBuilder named(ResourceLocation name) {
            return new ChannelBuilder().name(name);
        }

        public ChannelBuilder name(ResourceLocation name) {
            this.name = name;
            return this;
        }

        public ChannelBuilder networkProtocolVersion(Supplier<String> networkProtocolVersion) {
            this.networkProtocolVersion = networkProtocolVersion;
            return this;
        }

        public ChannelBuilder clientAcceptedVersions(Predicate<String> clientAcceptedVersions) {
            this.clientAcceptedVersions = clientAcceptedVersions;
            return this;
        }

        public ChannelBuilder serverAcceptedVersions(Predicate<String> serverAcceptedVersions) {
            this.serverAcceptedVersions = serverAcceptedVersions;
            return this;
        }

        public SimpleChannel simpleChannel() {
            return new SimpleChannel(name);
        }
    }

    public static ChannelBuilder ChannelBuilder() {
        return new ChannelBuilder();
    }

    public static ChannelBuilder newChannelBuilder(ResourceLocation name) {
        return new ChannelBuilder().named(name);
    }
}