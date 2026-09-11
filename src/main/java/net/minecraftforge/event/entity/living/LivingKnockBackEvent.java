package net.minecraftforge.event.entity.living;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.eventbus.api.Cancelable;

@Cancelable
public class LivingKnockBackEvent extends LivingEvent {

    private final Entity sourceEntity;
    private final float strength;
    private final double ratioX;
    private final double ratioZ;

    public LivingKnockBackEvent(LivingEntity target, Entity sourceEntity, float strength, double ratioX, double ratioZ) {
        super(target);
        this.sourceEntity = sourceEntity;
        this.strength = strength;
        this.ratioX = ratioX;
        this.ratioZ = ratioZ;
    }

    public LivingKnockBackEvent(LivingEntity target, float strength, double ratioX, double ratioZ) {
        this(target, null, strength, ratioX, ratioZ);
    }

    public Entity getSourceEntity() {
        return sourceEntity;
    }

    public float getStrength() {
        return strength;
    }

    public double getRatioX() {
        return ratioX;
    }

    public double getRatioZ() {
        return ratioZ;
    }
}