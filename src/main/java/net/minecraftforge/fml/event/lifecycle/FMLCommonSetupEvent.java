package net.minecraftforge.fml.event.lifecycle;

import net.minecraftforge.eventbus.api.Event;

/**
 * Fabric-port shim of FMLCommonSetupEvent. enqueueWork runs immediately
 * (Fabric onInitialize runs on the main thread).
 */
public class FMLCommonSetupEvent extends Event {

    public void enqueueWork(Runnable runnable) {
        runnable.run();
    }
}