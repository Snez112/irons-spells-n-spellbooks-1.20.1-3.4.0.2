package net.minecraftforge.registries;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Fabric-port shim of Forge's IForgeRegistry. Mirrors the surface used by the mod.
 */
public interface IForgeRegistry<V> extends Iterable<V> {

    ResourceKey<Registry<V>> getRegistryKey();

    ResourceLocation getRegistryName();

    void register(String key, V value);

    void register(ResourceLocation key, V value);

    boolean containsKey(ResourceLocation key);

    boolean containsValue(V value);

    boolean isEmpty();

    /**
     * Returns the value registered under the given location; {@code null} if absent.
     */
    V getValue(ResourceLocation key);

    V getValue(ResourceKey<V> key);

    /**
     * Returns the default-keyed value.
     */
    @SuppressWarnings("unchecked")
    default V getValue(String key) {
        return getValue(new ResourceLocation(key));
    }

    ResourceLocation getKey(V value);

    Set<ResourceKey<V>> getKeys();

    Set<V> getValues();

    Set<Map.Entry<ResourceKey<V>, V>> getEntries();

    int size();

    default Iterator<V> iterator() {
        return getValues().iterator();
    }
}