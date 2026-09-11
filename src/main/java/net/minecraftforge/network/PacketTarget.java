package net.minecraftforge.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

/**
 * Target for a distributed packet.
 */
public interface PacketTarget {
    void send(ResourceLocation channel, FriendlyByteBuf buf);

    default boolean skips(Entity entity) {
        return false;
    }
}