package net.minecraftforge.fml.common;

import net.minecraftforge.api.distmarker.Dist;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Fabric-port shim: marker annotation preserved for source compatibility.
 * Entry points are declared in fabric.mod.json instead.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Mod {
    String value();

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    @interface EventBusSubscriber {
        Dist[] value() default {Dist.CLIENT, Dist.DEDICATED_SERVER};
        Dist[] dist() default {Dist.CLIENT, Dist.DEDICATED_SERVER};
        String modid() default "";
        Bus bus() default Bus.FORGE;

        enum Bus {
            MOD,
            FORGE,
            ASYNC
        }
    }
}