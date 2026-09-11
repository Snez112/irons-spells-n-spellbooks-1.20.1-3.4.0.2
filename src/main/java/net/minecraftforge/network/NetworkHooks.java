package net.minecraftforge.network;

import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.world.entity.Entity;

/**
 * Fabric-port shim: Forge's NetworkHooks.
 */
public class NetworkHooks {

    private NetworkHooks() {
    }

    public static Packet<ClientGamePacketListener> getEntitySpawningPacket(Entity entity) {
        return new ClientboundAddEntityPacket(entity);
    }

    public static void openScreen(net.minecraft.server.level.ServerPlayer player, net.minecraft.world.MenuProvider containerSupplier) {
        player.openMenu(containerSupplier);
    }

    public static void openScreen(net.minecraft.server.level.ServerPlayer player, net.minecraft.world.MenuProvider containerSupplier, net.minecraft.core.BlockPos pos) {
        player.openMenu(containerSupplier);
    }

    public static void openScreen(net.minecraft.server.level.ServerPlayer player, net.minecraft.world.MenuProvider containerSupplier, java.util.function.Consumer<net.minecraft.network.FriendlyByteBuf> extraDataWriter) {
        player.openMenu(containerSupplier);
    }
}