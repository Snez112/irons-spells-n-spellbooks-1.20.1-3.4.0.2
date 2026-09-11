package net.minecraftforge.fml;

import java.util.function.Supplier;

/**
 * Fabric-port shim: InterModComms is not supported on Fabric. On Fabric, Curios slots
 * are declared via data JSON (data/curios/slots/*.json).
 */
public class InterModComms {

    private InterModComms() {
    }

    public static <T> void sendTo(String modId, String key, Supplier<T> payload) {
        // no-op
    }
}