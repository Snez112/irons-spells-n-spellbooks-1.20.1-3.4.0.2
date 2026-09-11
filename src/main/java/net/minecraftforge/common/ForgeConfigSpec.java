package net.minecraftforge.common;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

/**
 * Fabric-port shim of Forge's ForgeConfigSpec. Config files are stored as JSON
 * under config/&lt;modid&gt;-server.json (converted from the Forge .toml names).
 */
public class ForgeConfigSpec {

    private final Map<String, ConfigValue<?>> values = new ConcurrentHashMap<>();
    private final List<String> order = new ArrayList<>();
    private volatile String fileName;
    private volatile JsonObject json;

    public static class ConfigValue<T> implements Supplier<T> {
        private final ForgeConfigSpec owner;
        private final String path;
        private final T defaultValue;

        ConfigValue(ForgeConfigSpec owner, String path, T defaultValue) {
            this.owner = owner;
            this.path = path;
            this.defaultValue = defaultValue;
        }

        public T getDefault() {
            return defaultValue;
        }

        public boolean isPresent() {
            return owner.json() != null && owner.json().has(path);
        }

        @Override
        @SuppressWarnings("unchecked")
        public T get() {
            JsonElement el = owner.json() == null ? null : owner.json().get(path);
            if (el != null) {
                T parsed = (T) parseElement(el, defaultValue);
                if (parsed != null) {
                    return parsed;
                }
            }
            return defaultValue;
        }
    }

    @SuppressWarnings("unchecked")
    private static Object parseElement(JsonElement el, Object fallback) {
        if (el == null || el.isJsonNull()) {
            return null;
        }
        if (fallback instanceof Boolean) return el.getAsBoolean();
        if (fallback instanceof Integer) return el.getAsInt();
        if (fallback instanceof Double) return el.getAsDouble();
        if (fallback instanceof Long) return el.getAsLong();
        if (fallback instanceof String) return el.getAsString();
        if (fallback instanceof List<?> list) {
            if (el.isJsonArray()) {
                List<Object> out = new ArrayList<>();
                Object first = list.isEmpty() ? null : list.get(0);
                for (JsonElement e : el.getAsJsonArray()) {
                    Object proto = first == null ? (e.getAsJsonPrimitive().isString() ? "" : e.getAsJsonPrimitive().isNumber() ? 0d : false) : first;
                    out.add(parseElement(e, proto));
                }
                return out;
            }
        }
        if (fallback instanceof Enum<?> e) {
            try {
                for (Enum<?> constant : e.getDeclaringClass().getEnumConstants()) {
                    if (constant.name().equalsIgnoreCase(el.getAsString())) return constant;
                }
            } catch (Exception ignored) {
            }
            return null;
        }
        return null;
    }

    public void setFile(String fileName) {
        this.fileName = fileName;
        load();
    }

    private synchronized void load() {
        if (fileName == null || fileName.isBlank()) {
            this.json = new JsonObject();
            return;
        }
        JsonObject result = new JsonObject();
        for (String candidate : new String[]{"config/" + fileName, fileName}) {
            if (candidate != null) {
                try {
                    Path p = Path.of(candidate);
                    if (Files.exists(p)) {
                        result = JsonParser.parseString(Files.readString(p)).getAsJsonObject();
                        break;
                    }
                } catch (Exception ignored) {
                }
            }
        }
        this.json = result != null ? result : new JsonObject();
    }
public static class Builder {
        private final ForgeConfigSpec spec = new ForgeConfigSpec();
        private final List<String> path = new ArrayList<>();

        public Builder comment(String... comments) {
            return this;
        }

        public Builder worldRestart() {
            return this;
        }

        public Builder push(String segment) {
            path.add(segment);
            return this;
        }

        public Builder push(List<String> segments) {
            path.addAll(segments);
            return this;
        }

        public Builder push(boolean toggle) {
            return this;
        }

        public Builder pop() {
            if (!path.isEmpty()) path.remove(path.size() - 1);
            return this;
        }

        private String makePath(String name) {
            StringBuilder sb = new StringBuilder();
            for (String s : path) sb.append(s).append('.');
            return sb.append(name).toString();
        }

        public <T> ConfigValue<T> define(String name, T defaultValue) {
            String p = makePath(name);
            ConfigValue<T> cv = new ConfigValue<>(spec, p, defaultValue);
            spec.values.put(p, cv);
            spec.order.add(p);
            return cv;
        }

        public <T> ConfigValue<List<? extends T>> defineList(String name, List<? extends T> defaultValue, java.util.function.Predicate<Object> elementValidator) {
            @SuppressWarnings("unchecked")
            List<? extends T> copy = new ArrayList<T>((List<T>) (List<?>) defaultValue);
            return define(name, copy);
        }

        public <T> ConfigValue<List<? extends T>> defineList(String name, Supplier<List<? extends T>> defaultSupplier, java.util.function.Predicate<Object> elementValidator) {
            List<? extends T> list = defaultSupplier.get();
            return define(name, list);
        }

        public <E extends Enum<E>> ConfigValue<E> defineEnum(String name, E defaultValue) {
            String p = makePath(name);
            ConfigValue<E> cv = new ConfigValue<>(spec, p, defaultValue);
            spec.values.put(p, cv);
            spec.order.add(p);
            return cv;
        }

        public ForgeConfigSpec build() {
            return spec;
        }
    }

    private JsonObject json() {
        if (json == null) {
            load();
        }
        return json;
    }

    public Map<String, ConfigValue<?>> getValues() {
        return values;
    }

    public List<String> getOrder() {
        return order;
    }
}