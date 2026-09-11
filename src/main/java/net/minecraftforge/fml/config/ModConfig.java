package net.minecraftforge.fml.config;

import com.google.gson.JsonObject;
import net.minecraftforge.fml.ModLoadingContext;

/**
 * Fabric-port shim of Forge's ModConfig.
 */
public class ModConfig {

    public enum Type {
        CLIENT,
        SERVER
    }

    private final Type type;
    private final String fileName;
    private final JsonObject configJson;

    public ModConfig(Type type, String fileName, JsonObject configJson) {
        this.type = type;
        this.fileName = fileName;
        this.configJson = configJson;
    }

    public Type getType() {
        return type;
    }

    public String getFileName() {
        return fileName;
    }

    public JsonObject getConfigJson() {
        return configJson;
    }

    public String getBaseFolder() {
        return "config";
    }
}