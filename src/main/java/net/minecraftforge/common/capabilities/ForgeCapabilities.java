package net.minecraftforge.common.capabilities;

import net.minecraftforge.items.IItemHandler;

/**
 * Fabric-port shim of Forge's ForgeCapabilities.
 */
public class ForgeCapabilities {
    public static final Capability<IItemHandler> ITEM_HANDLER = CapabilityManager.get(new CapabilityToken<>() {});
}