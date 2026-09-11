package net.minecraftforge.fml;

import net.fabricmc.loader.api.FabricLoader;

/**
 * Fabric-port shim: ModLoadingContext. Config registration is a no-op on Fabric
 * (configs are read from config/*.json directly by ForgeConfigSpec).
 */
public class ModLoadingContext {

    private static final ModLoadingContext INSTANCE = new ModLoadingContext();

    private ModLoadingContext() {
    }

    public static ModLoadingContext get() {
        return INSTANCE;
    }

    public void registerConfig(net.minecraftforge.fml.config.ModConfig.Type type, com.google.gson.JsonObject config) {
        // no-op on Fabric
    }

    public void registerConfig(net.minecraftforge.fml.config.ModConfig.Type type, String fileName) {
        // no-op on Fabric
    }

    public void registerConfig(net.minecraftforge.fml.config.ModConfig.Type type, net.minecraftforge.common.ForgeConfigSpec spec) {
        if (spec != null) {
            String suffix = type == net.minecraftforge.fml.config.ModConfig.Type.CLIENT ? "client.json" : "server.json";
            spec.setFile("irons_spellbooks-" + suffix);
        }
    }

    public void registerConfig(net.minecraftforge.fml.config.ModConfig.Type type, net.minecraftforge.common.ForgeConfigSpec spec, String fileName) {
        if (spec != null) {
            if (fileName != null && fileName.endsWith(".toml")) {
                fileName = fileName.substring(0, fileName.length() - 5) + ".json";
            }
            spec.setFile(fileName != null ? fileName : (type == net.minecraftforge.fml.config.ModConfig.Type.CLIENT ? "irons_spellbooks-client.json" : "irons_spellbooks-server.json"));
        }
    }
}