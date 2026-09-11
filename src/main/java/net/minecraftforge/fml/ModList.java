package net.minecraftforge.fml;

import net.fabricmc.loader.api.FabricLoader;

import java.util.List;
import java.util.Optional;

/**
 * Fabric-port shim backed by FabricLoader.
 */
public class ModList {

    private static final ModList INSTANCE = new ModList();

    private ModList() {
    }

    public static ModList get() {
        return INSTANCE;
    }

    public boolean isLoaded(String modid) {
        return FabricLoader.getInstance().isModLoaded(modid);
    }

    public List<?> getAllMods() {
        return getMods();
    }

    public List<IModInfo> getMods() {
        return FabricLoader.getInstance().getAllMods().stream()
                .map(m -> new IModInfo(
                        m.getMetadata().getId(),
                        m.getMetadata().getName(),
                        m.getMetadata().getVersion().getFriendlyString(),
                        m.getMetadata().getContact().get("homepage").orElse("")
                ))
                .toList();
    }

    public ModInfo getModFileById(String modid) {
        return new ModInfo(modid);
    }

    public static class ModInfo {
        private final String modid;

        ModInfo(String modid) {
            this.modid = modid;
        }

        public String getModId() {
            return modid;
        }

        public String getFile() {
            return modid;
        }
    }

    public static class IModInfo {
        private final String modId;
        private final String displayName;
        private final String version;
        private final String url;

        public IModInfo(String modId, String displayName, String version, String url) {
            this.modId = modId;
            this.displayName = displayName;
            this.version = version;
            this.url = url;
        }

        public String getModId() {
            return modId;
        }

        public String getDisplayName() {
            return displayName;
        }

        public String getVersion() {
            return version;
        }

        public Optional<String> getModURL() {
            return url.isEmpty() ? Optional.empty() : Optional.of(url);
        }

        public OwningFile getOwningFile() {
            return new OwningFile(modId);
        }

        public ModConfig getConfig() {
            return new ModConfig();
        }
    }

    public static class OwningFile {
        private final String name;

        public OwningFile(String name) {
            this.name = name;
        }

        public FileInfo getFile() {
            return new FileInfo(name + ".jar");
        }

        public ModConfig getConfig() {
            return new ModConfig();
        }
    }

    public record FileInfo(String getFileName) {
    }

    public static class ModConfig {
        public Optional<Object> getConfigElement(String key) {
            return Optional.empty();
        }
    }
}