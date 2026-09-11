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
        if (containerSupplier instanceof net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory extended) {
            player.openMenu(extended);
        } else {
            player.openMenu(new net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory() {
                @Override
                public void writeScreenOpeningData(net.minecraft.server.level.ServerPlayer player, net.minecraft.network.FriendlyByteBuf buf) {
                    buf.writeBlockPos(pos);
                }

                @Override
                public net.minecraft.network.chat.Component getDisplayName() {
                    return containerSupplier.getDisplayName();
                }

                @org.jetbrains.annotations.Nullable
                @Override
                public net.minecraft.world.inventory.AbstractContainerMenu createMenu(int syncId, net.minecraft.world.entity.player.Inventory inv, net.minecraft.world.entity.player.Player player) {
                    return containerSupplier.createMenu(syncId, inv, player);
                }
            });
        }
    }

    public static void openScreen(net.minecraft.server.level.ServerPlayer player, net.minecraft.world.MenuProvider containerSupplier, java.util.function.Consumer<net.minecraft.network.FriendlyByteBuf> extraDataWriter) {
        if (containerSupplier instanceof net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory extended) {
            player.openMenu(extended);
        } else {
            player.openMenu(new net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory() {
                @Override
                public void writeScreenOpeningData(net.minecraft.server.level.ServerPlayer player, net.minecraft.network.FriendlyByteBuf buf) {
                    extraDataWriter.accept(buf);
                }

                @Override
                public net.minecraft.network.chat.Component getDisplayName() {
                    return containerSupplier.getDisplayName();
                }

                @org.jetbrains.annotations.Nullable
                @Override
                public net.minecraft.world.inventory.AbstractContainerMenu createMenu(int syncId, net.minecraft.world.entity.player.Inventory inv, net.minecraft.world.entity.player.Player player) {
                    return containerSupplier.createMenu(syncId, inv, player);
                }
            });
        }
    }
}