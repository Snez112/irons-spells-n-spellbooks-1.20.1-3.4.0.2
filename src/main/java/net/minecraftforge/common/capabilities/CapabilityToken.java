package net.minecraftforge.common.capabilities;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Fabric-port shim of Forge's CapabilityToken.
 */
public abstract class CapabilityToken<T> {

    private final Type type;

    public CapabilityToken() {
        Type superclass = getClass().getGenericSuperclass();
        if (superclass instanceof ParameterizedType pt) {
            this.type = pt.getActualTypeArguments()[0];
        } else {
            this.type = Object.class;
        }
    }

    public Type getType() {
        return type;
    }

    public String getName() {
        return "capability";
    }
}