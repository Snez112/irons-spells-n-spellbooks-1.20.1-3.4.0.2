package net.minecraftforge.common.capabilities;

import java.lang.reflect.Type;

/**
 * Fabric-port shim of Forge's Capability token.
 */
public class Capability<T> {
    private final Type type;
    private final String name;

    public Capability(Type type, String name) {
        this.type = type;
        this.name = name;
    }

    public Type getType() {
        return type;
    }

    public String getName() {
        return name;
    }
}