package net.minecraftforge.registries;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Adapts a vanilla 1.20.1 registry to the Forge IForgeRegistry surface used by the mod.
 */
public class RegistryAdapter<V> implements IForgeRegistry<V> {

    private final Registry<V> delegate;
    private final ResourceKey<Registry<V>> key;
    private final ResourceLocation name;

    private RegistryAdapter(Registry<V> delegate, ResourceKey<Registry<V>> key) {
        this.delegate = delegate;
        this.key = key;
        this.name = key != null ? key.location() : null;
    }

    public static <V> RegistryAdapter<V> of(Registry<V> delegate, ResourceKey<Registry<V>> key) {
        return new RegistryAdapter<>(delegate, key);
    }

    public static <V> RegistryAdapter<V> ofKey(ResourceKey<Registry<V>> key) {
        return new RegistryAdapter<>(null, key);
    }

    public Registry<V> unwrap() {
        return delegate;
    }

    @Override
    public ResourceKey<Registry<V>> getRegistryKey() {
        return key;
    }

    @Override
    public ResourceLocation getRegistryName() {
        return name;
    }

    @Override
    public void register(String key, V value) {
        if (delegate != null) {
            Registry.register(delegate, key, value);
        }
    }

    @Override
    public void register(ResourceLocation key, V value) {
        if (delegate != null) {
            Registry.register(delegate, key, value);
        }
    }

    @Override
    public boolean containsKey(ResourceLocation key) {
        return delegate != null && delegate.containsKey(key);
    }

    @Override
    public boolean containsValue(V value) {
        return containsKey(getKey(value));
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Nullable
    @Override
    public V getValue(ResourceLocation key) {
        return delegate != null ? delegate.get(key) : null;
    }

    @Nullable
    @Override
    public V getValue(ResourceKey<V> key) {
        return delegate != null ? delegate.get(key) : null;
    }

    @Nullable
    @Override
    public ResourceLocation getKey(V value) {
        return delegate != null ? delegate.getKey(value) : null;
    }

    @Override
    public Set<ResourceKey<V>> getKeys() {
        return delegate != null ? delegate.registryKeySet() : Set.of();
    }

    @Override
    public Set<V> getValues() {
        return delegate != null ? delegate.keySet().stream().map(delegate::get).collect(Collectors.toCollection(LinkedHashSet::new)) : Set.of();
    }

    @Override
    public Set<java.util.Map.Entry<ResourceKey<V>, V>> getEntries() {
        if (delegate == null) {
            return Set.of();
        }
        return delegate.entrySet().stream().collect(Collectors.toSet());
    }

    @Override
    public int size() {
        return delegate != null ? delegate.size() : 0;
    }

    @Override
    public Iterator<V> iterator() {
        return getValues().iterator();
    }
}