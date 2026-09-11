package net.minecraftforge.entity;

import net.minecraft.network.FriendlyByteBuf;

/**
 * Fabric-port shim of Forge's IEntityAdditionalSpawnData. Serialization of
 * additional spawn data is not wired on the initial Fabric port.
 */
public interface IEntityAdditionalSpawnData {
    void writeSpawnData(FriendlyByteBuf buffer);

    void readSpawnData(FriendlyByteBuf additionalData);
}