package net.minecraftforge.fml.javafmlmod;

import net.minecraftforge.eventbus.api.EventBusImpl;
import net.minecraftforge.eventbus.api.IEventBus;

/**
 * Fabric-port shim: provides the mod-scoped event bus originally used by @Mod classes.
 */
public class FMLJavaModLoadingContext {

    private static final FMLJavaModLoadingContext INSTANCE = new FMLJavaModLoadingContext();
    private final IEventBus modEventBus = new EventBusImpl();

    private FMLJavaModLoadingContext() {
    }

    public static FMLJavaModLoadingContext get() {
        return INSTANCE;
    }

    public IEventBus getModEventBus() {
        return modEventBus;
    }
}