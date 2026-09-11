package net.minecraftforge.fluids;

import net.minecraft.resources.ResourceLocation;

public abstract class FluidType {

    private final ResourceLocation id;

    protected FluidType(ResourceLocation id) {
        this.id = id;
    }

    public ResourceLocation getId() {
        return id;
    }
}