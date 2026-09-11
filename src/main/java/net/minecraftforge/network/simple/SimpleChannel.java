package net.minecraftforge.network.simple;

import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.PacketTarget;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class SimpleChannel {

    private record Handler(int id, Class<?> type, BiConsumer<Object, FriendlyByteBuf> encoder, Function<FriendlyByteBuf, Object> decoder, BiConsumer<Object, Supplier<NetworkEvent.Context>> consumer, NetworkDirection direction) {}

    private final ResourceLocation channel;
    private final Map<Integer, Handler> handlers = new ConcurrentHashMap<>();
    private final Map<Class<?>, Integer> ids = new ConcurrentHashMap<>();
    private volatile boolean registered = false;

    public SimpleChannel(ResourceLocation channel) {
        this.channel = channel;
    }

    public ResourceLocation getChannel() {
        return channel;
    }

    public <M> MessageBuilder<M> messageBuilder(Class<M> type, int id) {
        return new MessageBuilder<>(this, type, id);
    }

    public <M> MessageBuilder<M> messageBuilder(Class<M> type, int id, NetworkDirection direction) {
        return new MessageBuilder<>(this, type, id).direction(direction);
    }

    private FriendlyByteBuf encode(Object message) {
        Integer id = ids.get(message.getClass());
        if (id == null) {
            throw new IllegalArgumentException("Unregistered message type " + message.getClass());
        }
        Handler h = handlers.get(id);
        FriendlyByteBuf buf = PacketByteBufs.create();
        buf.writeInt(id);
        h.encoder.accept(message, buf);
        return buf;
    }

    private Object decode(int id, FriendlyByteBuf buf) {
        Handler h = handlers.get(id);
        return h == null ? null : h.decoder.apply(buf);
    }

    public <M> void sendToServer(M message) {
        register();
        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
            ClientSender.send(channel, encode(message));
        }
    }

    public <M> void send(PacketTarget target, M message) {
        register();
        target.send(channel, encode(message));
    }

    public void register() {
        if (registered) {
            return;
        }
        registered = true;
        ServerPlayNetworking.registerGlobalReceiver(channel, (server, player, handler, buf, context) -> {
            int id = buf.readInt();
            Handler h = handlers.get(id);
            if (h != null && !h.direction.targetClient()) {
                Object message = h.decoder.apply(buf);
                NetworkEvent.Context ctx = new NetworkEvent.Context(channel, NetworkDirection.PLAY_TO_SERVER, player);
                server.execute(() -> h.consumer.accept(message, () -> ctx));
            }
        });
        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
            ClientReceiver.register(this);
        }
    }

    private static class ClientSender {
        static void send(ResourceLocation channel, FriendlyByteBuf buf) {
            ClientPlayNetworking.send(channel, buf);
        }
    }

    private static class ClientReceiver {
        static void register(SimpleChannel simpleChannel) {
            ClientPlayNetworking.registerGlobalReceiver(simpleChannel.channel, (client, handler, buf, context) -> {
                if (buf.readableBytes() > 0) {
                    int id = buf.readInt();
                    Handler h = simpleChannel.handlers.get(id);
                    if (h != null && h.direction.targetClient()) {
                        Object message = h.decoder.apply(buf);
                        NetworkEvent.Context ctx = new NetworkEvent.Context(simpleChannel.channel, NetworkDirection.PLAY_TO_CLIENT);
                        client.execute(() -> h.consumer.accept(message, () -> ctx));
                    }
                }
            });
        }
    }

    public static class MessageBuilder<M> {
        private final SimpleChannel owner;
        private final Class<M> type;
        private final int id;
        private NetworkDirection direction = NetworkDirection.PLAY_TO_SERVER;
        private Function<FriendlyByteBuf, M> decoder;
        private BiConsumer<M, FriendlyByteBuf> encoder;
        private BiConsumer<M, Supplier<NetworkEvent.Context>> consumer;

        MessageBuilder(SimpleChannel owner, Class<M> type, int id) {
            this.owner = owner;
            this.type = type;
            this.id = id;
        }

        public MessageBuilder<M> direction(NetworkDirection direction) {
            this.direction = direction;
            return this;
        }

        public MessageBuilder<M> encoder(BiConsumer<M, FriendlyByteBuf> encoder) {
            this.encoder = encoder;
            return this;
        }

        public MessageBuilder<M> decoder(Function<FriendlyByteBuf, M> decoder) {
            this.decoder = decoder;
            return this;
        }

        public MessageBuilder<M> consumerMainThread(BiConsumer<M, Supplier<NetworkEvent.Context>> consumer) {
            this.consumer = consumer;
            return this;
        }

        public void add() {
            @SuppressWarnings("unchecked")
            Handler h = new Handler(id, type, (BiConsumer<Object, FriendlyByteBuf>) (BiConsumer<?, ?>) encoder, (Function<FriendlyByteBuf, Object>) (Function<?, ?>) decoder, (BiConsumer<Object, Supplier<NetworkEvent.Context>>) (BiConsumer<?, ?>) consumer, direction);
            owner.handlers.put(id, h);
            owner.ids.put(type, id);
        }

        public interface ToBooleanBiFunction<T, U> {
            boolean apply(T t, U u);
        }
    }
}