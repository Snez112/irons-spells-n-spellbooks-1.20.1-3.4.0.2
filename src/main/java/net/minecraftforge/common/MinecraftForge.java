package net.minecraftforge.common;

import net.minecraftforge.eventbus.api.EventBusImpl;
import net.minecraftforge.eventbus.api.IEventBus;

/**
 * Fabric-port shim: exposes the shared EVENT_BUS used throughout the (ported) mod.
 */
public class MinecraftForge {
    public static final IEventBus EVENT_BUS = new EventBusImpl();
}