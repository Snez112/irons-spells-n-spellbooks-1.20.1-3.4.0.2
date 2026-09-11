package net.minecraftforge.network;

import io.redspace.ironsspellbooks.IronsSpellbooks;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;

import java.util.List;
import java.util.function.Supplier;

public class PacketDistributor<T> {

    public static final PacketDistributor<ServerPlayer> PLAYER = new PacketDistributor<>(Kind.PLAYER);
    public static final PacketDistributor<Void> ALL = new PacketDistributor<>(Kind.ALL);
    public static final PacketDistributor<Entity> TRACKING_ENTITY = new PacketDistributor<>(Kind.TRACKING_ENTITY);
    public static final PacketDistributor<Entity> TRACKING_ENTITY_AND_SELF = new PacketDistributor<>(Kind.TRACKING_ENTITY_AND_SELF);

    private enum Kind {
        PLAYER,
        ALL,
        TRACKING_ENTITY,
        TRACKING_ENTITY_AND_SELF
    }

    private final Kind kind;

    private PacketDistributor(Kind kind) {
        this.kind = kind;
    }

    public PacketTarget with(Supplier<T> supplier) {
        return new Target(kind, () -> (Object) supplier.get());
    }

    public PacketTarget noArg() {
        return new Target(kind, () -> null);
    }

    private static class Target implements PacketTarget {
        private final Kind kind;
        private final Supplier<Object> supplier;

        Target(Kind kind, Supplier<Object> supplier) {
            this.kind = kind;
            this.supplier = supplier;
        }

        @Override
        public void send(ResourceLocation channel, FriendlyByteBuf buf) {
            switch (kind) {
                case PLAYER -> {
                    Object o = supplier.get();
                    if (o instanceof ServerPlayer sp) {
                        ServerPlayNetworking.send(sp, channel, buf);
                    }
                }
                case ALL -> {
                    if (IronsSpellbooks.MCS != null) {
                        List<ServerPlayer> players = IronsSpellbooks.MCS.getPlayerList().getPlayers();
                        for (ServerPlayer p : players) {
                            ServerPlayNetworking.send(p, channel, buf);
                        }
                    }
                }
                case TRACKING_ENTITY -> {
                    Object o = supplier.get();
                    if (o instanceof Entity e) {
                        for (ServerPlayer p : PlayerLookup.tracking(e)) {
                            ServerPlayNetworking.send(p, channel, buf);
                        }
                    }
                }
                case TRACKING_ENTITY_AND_SELF -> {
                    Object o = supplier.get();
                    if (o instanceof Entity e) {
                        for (ServerPlayer p : PlayerLookup.tracking(e)) {
                            ServerPlayNetworking.send(p, channel, buf);
                        }
                        if (e instanceof ServerPlayer sp) {
                            ServerPlayNetworking.send(sp, channel, buf);
                        }
                    }
                }
            }
        }

        @Override
        public boolean skips(Entity entity) {
            return false;
        }
    }
}