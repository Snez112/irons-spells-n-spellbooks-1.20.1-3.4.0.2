package net.minecraftforge.registries;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

/**
 * Fabric-port shim of Forge's DeferredRegister.
 * On Fabric, registrations are flushed eagerly when {@link #register(IEventBus)} is called
 * (this runs inside the mod initializer, which is a valid time to write vanilla registries).
 */
public class DeferredRegister<T> {

    private record Registration<E>(String name, Supplier<? extends E> sup, boolean hasNamespace) {}

    private final IForgeRegistry<T> registry;
    private final String modid;
    private final List<Registration<T>> pending = new ArrayList<>();
    private final List<RegistryObject<T>> entries = new ArrayList<>();
    private boolean flushed = false;

    private DeferredRegister(IForgeRegistry<T> registry, String modid) {
        this.registry = registry;
        this.modid = modid;
    }

    private static final java.util.Map<ResourceKey<?>, IForgeRegistry<?>> CUSTOM_REGISTRIES = new java.util.concurrent.ConcurrentHashMap<>();

    public static <B> DeferredRegister<B> create(IForgeRegistry<B> reg, String modid) {
        return new DeferredRegister<>(reg, modid);
    }

    public static <B> DeferredRegister<B> create(ResourceLocation loc, String modid) {
        return create(ResourceKey.createRegistryKey(loc), modid);
    }

    public static <B> DeferredRegister<B> create(ResourceKey<? extends net.minecraft.core.Registry<B>> key, String modid) {
        for (IForgeRegistry<?> reg : new IForgeRegistry<?>[]{ForgeRegistries.ITEMS, ForgeRegistries.BLOCKS, ForgeRegistries.ENTITY_TYPES, ForgeRegistries.MOB_EFFECTS, ForgeRegistries.SOUND_EVENTS, ForgeRegistries.POTIONS, ForgeRegistries.ATTRIBUTES, ForgeRegistries.PARTICLE_TYPES, ForgeRegistries.MENU_TYPES, ForgeRegistries.BLOCK_ENTITY_TYPES, ForgeRegistries.COMMAND_ARGUMENT_TYPES, ForgeRegistries.BIOMES}) {
            if (reg.getRegistryKey().equals(key)) {
                @SuppressWarnings("unchecked")
                IForgeRegistry<B> typed = (IForgeRegistry<B>) reg;
                return new DeferredRegister<>(typed, modid);
            }
        }
        if (BuiltInRegistries.REGISTRY.containsKey(key.location())) {
            @SuppressWarnings("unchecked")
            net.minecraft.core.Registry<B> vanilla = (net.minecraft.core.Registry<B>) BuiltInRegistries.REGISTRY.get(key.location());
            if (vanilla != null) {
                @SuppressWarnings("unchecked")
                ResourceKey<net.minecraft.core.Registry<B>> typedKey = (ResourceKey<net.minecraft.core.Registry<B>>) (ResourceKey<?>) key;
                return new DeferredRegister<>(RegistryAdapter.of(vanilla, typedKey), modid);
            }
        }
        @SuppressWarnings("unchecked")
        IForgeRegistry<B> custom = (IForgeRegistry<B>) CUSTOM_REGISTRIES.computeIfAbsent(key, k -> new SimpleForgeRegistry<>((ResourceKey) k));
        return new DeferredRegister<>(custom, modid);
    }

    public ResourceKey<? extends net.minecraft.core.Registry<T>> getRegistryKey() {
        return this.registry.getRegistryKey();
    }

    public ResourceLocation getRegistryName() {
        return this.registry.getRegistryName();
    }

    public Supplier<IForgeRegistry<T>> makeRegistry(Supplier<RegistryBuilder<T>> builder) {
        return () -> this.registry;
    }

    public <I extends T> RegistryObject<I> register(String name, Supplier<? extends I> sup) {
        ResourceLocation loc = new ResourceLocation(modid, name);
        pending.add(new Registration(name, sup, false));
        return RegistryObject.create(loc, registry, () -> {
            if (!registry.containsKey(loc)) {
                I val = sup.get();
                registry.register(loc, val);
                return val;
            }
            @SuppressWarnings("unchecked")
            I existing = (I) registry.getValue(loc);
            return existing;
        });
    }

    public <I extends T> RegistryObject<I> register(ResourceLocation name, Supplier<? extends I> sup) {
        pending.add(new Registration(name.toString(), sup, true));
        return RegistryObject.create(name, registry, () -> {
            if (!registry.containsKey(name)) {
                I val = sup.get();
                registry.register(name, val);
                return val;
            }
            @SuppressWarnings("unchecked")
            I existing = (I) registry.getValue(name);
            return existing;
        });
    }

    public <I extends T> RegistryObject<I> register(String namespace, String name, Supplier<? extends I> sup) {
        ResourceLocation loc = new ResourceLocation(namespace, name);
        pending.add(new Registration(namespace + ":" + name, sup, true));
        return RegistryObject.create(loc, registry, () -> {
            if (!registry.containsKey(loc)) {
                I val = sup.get();
                registry.register(loc, val);
                return val;
            }
            @SuppressWarnings("unchecked")
            I existing = (I) registry.getValue(loc);
            return existing;
        });
    }

    /**
     * On Fabric this eagerly performs the registration (see class javadoc).
     */
    public void register(IEventBus bus) {
        flush();
    }

    public Collection<RegistryObject<T>> getEntries() {
        flush();
        return entries;
    }

    private void flush() {
        if (flushed) {
            return;
        }
        flushed = true;
        for (Registration r : pending) {
            ResourceLocation loc = r.hasNamespace ? new ResourceLocation(r.name) : new ResourceLocation(modid, r.name);
            if (!registry.containsKey(loc)) {
                @SuppressWarnings("unchecked")
                T value = (T) r.sup.get();
                registry.register(loc, value);
            }
            entries.add(RegistryObject.create(loc, registry));
        }
    }
}