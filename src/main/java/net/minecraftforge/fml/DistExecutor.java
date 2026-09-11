package net.minecraftforge.fml;

import net.minecraftforge.api.distmarker.Dist;

import java.util.function.Supplier;

/**
 * Fabric-port shim: DistExecutor. The client flag is set by the fabric client entrypoint.
 */
public class DistExecutor {

    private static volatile boolean isClient = false;

    /** Set by the Fabric client initializer. */
    public static void markClient() {
        isClient = true;
    }

    public static boolean isClient() {
        return isClient;
    }

    public static Dist currentDist() {
        return isClient ? Dist.CLIENT : Dist.SERVER;
    }

    public static <T> T safeRunForDist(Supplier<Dist> dist, Supplier<T> what) {
        return dist.get() == currentDist() ? what.get() : null;
    }

    public static void unsafeRunWhenOn(Dist dist, Supplier<Runnable> toRun) {
        if (dist == currentDist()) {
            Runnable r = toRun.get();
            if (r != null) {
                r.run();
            }
        }
    }

    public static void unsafeRunWhenOn(Dist dist, Runnable what) {
        if (dist == currentDist()) {
            what.run();
        }
    }
}