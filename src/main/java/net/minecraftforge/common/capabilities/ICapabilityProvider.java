package net.minecraftforge.common.capabilities;

import net.minecraft.core.Direction;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Fabric-port shim of Forge's ICapabilityProvider.
 */
public interface ICapabilityProvider {

    @Nonnull
    <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap);

    @Nonnull
    default <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return getCapability(cap);
    }
}