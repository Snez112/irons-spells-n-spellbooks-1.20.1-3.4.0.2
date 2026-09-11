package net.minecraftforge.api.distmarker;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Fabric-port shim: kept for source compatibility, treated as a no-op at runtime.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface OnlyIn {
    Dist value();
}