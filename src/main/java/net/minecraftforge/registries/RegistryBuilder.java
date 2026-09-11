package net.minecraftforge.registries;

import net.minecraft.resources.ResourceLocation;

/**
 * Fabric-port shim: RegistryBuilder builder dummy for custom registries.
 */
public class RegistryBuilder<T> {

    private ResourceLocation name;

    public RegistryBuilder<T> setName(ResourceLocation name) {
        this.name = name;
        return this;
    }

    public RegistryBuilder<T> disableSaving() {
        return this;
    }

    public RegistryBuilder<T> disableOverrides() {
        return this;
    }

    public RegistryBuilder<T> hasTags() {
        return this;
    }

    public RegistryBuilder<T> setDefaultKey(ResourceLocation defaultKey) {
        return this;
    }
}
