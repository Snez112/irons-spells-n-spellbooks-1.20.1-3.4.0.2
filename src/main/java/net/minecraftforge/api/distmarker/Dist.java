package net.minecraftforge.api.distmarker;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Fabric-port shim: Dist is a pure metadata marker (annotations are not enforced at runtime).
 */
public enum Dist {
    CLIENT(true),
    SERVER(false),
    DEDICATED_SERVER(false);

    private final boolean server;

    Dist(boolean server) {
        this.server = server;
    }

    public boolean isClient() {
        return !server;
    }

    public boolean isServer() {
        return server;
    }

    @Retention(RetentionPolicy.RUNTIME)
    public @interface OnlyIn {
        Dist value();
    }
}