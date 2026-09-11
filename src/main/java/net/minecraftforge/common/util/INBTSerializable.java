package net.minecraftforge.common.util;

/**
 * Fabric-port shim of Forge's INBTSerializable.
 */
public interface INBTSerializable<T> {
    T serializeNBT();

    void deserializeNBT(T nbt);
}