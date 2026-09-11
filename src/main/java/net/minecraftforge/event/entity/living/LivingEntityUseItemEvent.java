package net.minecraftforge.event.entity.living;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;

/**
 * Fabric-port shim of Forge's LivingEntityUseItemEvent.
 */
public class LivingEntityUseItemEvent extends Event {

    private final LivingEntity entity;
    private final ItemStack item;
    private int duration;

    public LivingEntityUseItemEvent(LivingEntity entity, ItemStack item, int duration) {
        this.entity = entity;
        this.item = item;
        this.duration = duration;
    }

    public LivingEntity getEntity() {
        return entity;
    }

    public ItemStack getItem() {
        return item;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Cancelable
    public static class Start extends LivingEntityUseItemEvent {
        public Start(LivingEntity entity, ItemStack item, int duration) {
            super(entity, item, duration);
        }
    }

    @Cancelable
    public static class Tick extends LivingEntityUseItemEvent {
        public Tick(LivingEntity entity, ItemStack item, int duration) {
            super(entity, item, duration);
        }
    }

    @Cancelable
    public static class Stop extends LivingEntityUseItemEvent {
        public Stop(LivingEntity entity, ItemStack item, int duration) {
            super(entity, item, duration);
        }
    }

    public static class Finish extends LivingEntityUseItemEvent {
        private ItemStack result;

        public Finish(LivingEntity entity, ItemStack item, int duration, ItemStack result) {
            super(entity, item, duration);
            this.result = result;
        }

        public ItemStack getResultStack() {
            return result;
        }

        public void setResultStack(ItemStack result) {
            this.result = result;
        }
    }
}