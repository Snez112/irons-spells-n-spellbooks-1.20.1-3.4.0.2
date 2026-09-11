package se.mickelus.tetra.effect;

public class ItemEffect {
    private final String key;

    public ItemEffect(String key) {
        this.key = key;
    }

    public static ItemEffect get(String key) {
        return new ItemEffect(key);
    }

    public String getKey() {
        return key;
    }
}
