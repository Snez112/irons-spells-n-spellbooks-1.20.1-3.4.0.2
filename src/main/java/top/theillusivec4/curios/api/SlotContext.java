package top.theillusivec4.curios.api;

import net.minecraft.world.entity.LivingEntity;

public record SlotContext(String identifier, LivingEntity entity, int index, boolean cosmetic, boolean visible) {
    public SlotContext(String identifier, LivingEntity entity) {
        this(identifier, entity, 0, false, true);
    }
}
