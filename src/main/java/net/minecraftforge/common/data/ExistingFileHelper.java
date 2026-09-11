package net.minecraftforge.common.data;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;

public class ExistingFileHelper {
    public boolean exists(ResourceLocation loc, PackType type, String pathSuffix, String pathPrefix) {
        return true;
    }

    public boolean trackGenerated(ResourceLocation loc, PackType type, String pathSuffix, String pathPrefix) {
        return true;
    }
}
