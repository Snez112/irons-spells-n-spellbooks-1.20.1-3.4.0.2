package net.minecraftforge.registries;

import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * Fabric-port shim of Forge's RegistryObject.
 */
public final class RegistryObject<T> implements Supplier<T> {

    private final ResourceLocation name;
    private final IForgeRegistry<? super T> registry;
    private final Supplier<? extends T> fallbackSupplier;

    private RegistryObject(ResourceLocation name, IForgeRegistry<? super T> registry, @Nullable Supplier<? extends T> fallbackSupplier) {
        this.name = name;
        this.registry = registry;
        this.fallbackSupplier = fallbackSupplier;
    }

    public static <T, U extends T> RegistryObject<U> create(ResourceLocation name, IForgeRegistry<T> registry) {
        return new RegistryObject<>(name, registry, null);
    }

    public static <T, U extends T> RegistryObject<U> create(ResourceLocation name, IForgeRegistry<T> registry, Supplier<? extends U> fallbackSupplier) {
        return new RegistryObject<>(name, registry, fallbackSupplier);
    }

    public static <T, U extends T> RegistryObject<U> create(ResourceLocation name, ResourceKey<net.minecraft.core.Registry<T>> registryKey, String modid) {
        throw new UnsupportedOperationException("create(RegistryKey,...) not ported; use create(name, IForgeRegistry)");
    }

    @Override
    public T get() {
        @SuppressWarnings("unchecked")
        T value = (T) registry.getValue(name);
        if (value == null && fallbackSupplier != null) {
            value = fallbackSupplier.get();
        }
        if (value == null) {
            throw new IllegalStateException("Missing value in registry for " + name + " (was it registered after first use?)");
        }
        return value;
    }

    public ResourceLocation getId() {
        return name;
    }

    public ResourceLocation getRegistryName() {
        return name;
    }

    @SuppressWarnings("unchecked")
    public ResourceKey<T> getKey() {
        return (ResourceKey<T>) ResourceKey.create(registry.getRegistryKey(), name);
    }

    public boolean isPresent() {
        return registry.containsKey(name) || (fallbackSupplier != null && get() != null);
    }

    @SuppressWarnings("unchecked")
    public void ifPresent(Consumer<? super T> consumer) {
        T value = (T) registry.getValue(name);
        if (value != null) {
            consumer.accept(value);
        }
    }

    public RegistryObject<T> filter(Predicate<? super T> predicate) {
        return this;
    }

    @SuppressWarnings("unchecked")
    public T orElse(T other) {
        T value = (T) registry.getValue(name);
        return value != null ? value : other;
    }

    @SuppressWarnings("unchecked")
    public T orElseGet(Supplier<? extends T> other) {
        T value = (T) registry.getValue(name);
        return value != null ? value : other.get();
    }

    public Stream<T> stream() {
        return Stream.of(get());
    }

    public int hashCode() {
        return name.hashCode();
    }

    public boolean equals(@Nullable Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RegistryObject<?> that)) {
            return false;
        }
        return Objects.equals(name, that.name);
    }

    @Override
    public String toString() {
        return name.toString();
    }
}