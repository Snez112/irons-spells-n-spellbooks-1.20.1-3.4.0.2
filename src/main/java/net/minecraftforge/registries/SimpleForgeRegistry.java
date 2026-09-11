package net.minecraftforge.registries;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import java.util.*;

/**
 * In-memory IForgeRegistry for custom mod registries (e.g. spells, schools) on Fabric.
 */
public class SimpleForgeRegistry<V> implements IForgeRegistry<V> {

    private final ResourceKey<Registry<V>> registryKey;
    private final ResourceLocation registryName;
    private final Map<ResourceLocation, V> map = new LinkedHashMap<>();
    private final Map<V, ResourceLocation> reverseMap = new IdentityHashMap<>();

    public SimpleForgeRegistry(ResourceKey<Registry<V>> registryKey) {
        this.registryKey = registryKey;
        this.registryName = registryKey.location();
    }

    @Override
    public ResourceKey<Registry<V>> getRegistryKey() {
        return registryKey;
    }

    @Override
    public ResourceLocation getRegistryName() {
        return registryName;
    }

    @Override
    public void register(String key, V value) {
        register(new ResourceLocation(key), value);
    }

    @Override
    public synchronized void register(ResourceLocation key, V value) {
        map.put(key, value);
        reverseMap.put(value, key);
    }

    @Override
    public boolean containsKey(ResourceLocation key) {
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(V value) {
        return reverseMap.containsKey(value);
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public V getValue(ResourceLocation key) {
        return map.get(key);
    }

    @Override
    public V getValue(ResourceKey<V> key) {
        return map.get(key.location());
    }

    @Override
    public ResourceLocation getKey(V value) {
        return reverseMap.get(value);
    }

    @Override
    public Set<ResourceKey<V>> getKeys() {
        Set<ResourceKey<V>> set = new LinkedHashSet<>();
        for (ResourceLocation loc : map.keySet()) {
            set.add(ResourceKey.create(registryKey, loc));
        }
        return set;
    }

    @Override
    public Set<V> getValues() {
        return new LinkedHashSet<>(map.values());
    }

    @Override
    public Set<Map.Entry<ResourceKey<V>, V>> getEntries() {
        Set<Map.Entry<ResourceKey<V>, V>> entries = new LinkedHashSet<>();
        for (Map.Entry<ResourceLocation, V> e : map.entrySet()) {
            entries.add(Map.entry(ResourceKey.create(registryKey, e.getKey()), e.getValue()));
        }
        return entries;
    }

    @Override
    public int size() {
        return map.size();
    }
}
