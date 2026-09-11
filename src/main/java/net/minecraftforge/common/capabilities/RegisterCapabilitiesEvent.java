package net.minecraftforge.common.capabilities;

import net.minecraftforge.eventbus.api.Event;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Fabric-port shim of Forge's RegisterCapabilitiesEvent.
 */
public class RegisterCapabilitiesEvent extends Event {

    private final List<Class<?>> capabilities = new CopyOnWriteArrayList<>();

    public <T> void register(Class<T> capabilityType) {
        capabilities.add(capabilityType);
    }

    public boolean contains(Class<?> capabilityType) {
        return capabilities.contains(capabilityType);
    }
}