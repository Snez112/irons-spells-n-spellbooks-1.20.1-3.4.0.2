package net.minecraftforge.common.capabilities;

import net.minecraftforge.common.util.LazyOptional;

import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Fabric-port shim of Forge's CapabilityManager.
 */
public class CapabilityManager {

    private static final Map<CapabilityToken<?>, Capability<?>> REGISTRY = new ConcurrentHashMap<>();
    private static final Map<Object, Map<Capability<?>, LazyOptional<?>>> ATTACHED = new WeakHashMap<>();

    @SuppressWarnings("unchecked")
    public static <T> Capability<T> get(CapabilityToken<T> token) {
        return (Capability<T>) REGISTRY.computeIfAbsent(token, k -> new Capability<>(k.getType(), k.getName()));
    }

    public static synchronized <T> void attach(Object obj, Capability<T> cap, LazyOptional<T> optional) {
        ATTACHED.computeIfAbsent(obj, k -> new ConcurrentHashMap<>()).put(cap, optional);
    }

    @SuppressWarnings("unchecked")
    public static synchronized <T> LazyOptional<T> get(Object obj, Capability<T> cap) {
        Map<Capability<?>, LazyOptional<?>> map = ATTACHED.get(obj);
        if (map != null) {
            LazyOptional<?> opt = map.get(cap);
            if (opt != null) {
                return (LazyOptional<T>) opt;
            }
        }
        if (obj instanceof ICapabilityProvider provider) {
            return provider.getCapability(cap);
        }
        return LazyOptional.empty();
    }
}