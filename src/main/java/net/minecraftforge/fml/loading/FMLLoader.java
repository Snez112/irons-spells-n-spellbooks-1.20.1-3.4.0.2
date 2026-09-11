package net.minecraftforge.fml.loading;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;

/**
 * Fabric-port shim.
 */
public class FMLLoader {

    public static Dist getDist() {
        return DistExecutor.currentDist();
    }

    public static boolean isProduction() {
        return !net.fabricmc.loader.api.FabricLoader.getInstance().isDevelopmentEnvironment();
    }
}