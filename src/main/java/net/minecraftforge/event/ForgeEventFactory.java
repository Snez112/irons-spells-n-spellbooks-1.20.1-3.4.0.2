package net.minecraftforge.event;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;

/**
 * Fabric-port shim: ForgeEventFactory is not applicable on Fabric.
 */
public class ForgeEventFactory {

    private ForgeEventFactory() {
    }

    public static boolean onExplosionStart() {
        return false;
    }

    public static boolean onExplosionStart(Level level, Explosion explosion) {
        return false;
    }

    public static boolean onProjectileImpact() {
        return false;
    }

    public static boolean onProjectileImpact(Entity entity, HitResult hitResult) {
        return false;
    }
}