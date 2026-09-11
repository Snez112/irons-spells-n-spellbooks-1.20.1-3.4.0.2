package net.minecraftforge.event.entity;

import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;

/**
 * Fabric-port shim of Forge's ProjectileImpactEvent.
 */
@Cancelable
public class ProjectileImpactEvent extends Event {

    private final Projectile projectile;
    private final HitResult rayTraceResult;

    public ProjectileImpactEvent(Projectile projectile, HitResult rayTraceResult) {
        this.projectile = projectile;
        this.rayTraceResult = rayTraceResult;
    }

    public Projectile getProjectile() {
        return projectile;
    }

    public HitResult getRayTraceResult() {
        return rayTraceResult;
    }
}