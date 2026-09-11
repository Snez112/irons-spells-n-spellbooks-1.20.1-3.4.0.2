package io.redspace.ironsspellbooks.mixin;

import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.DefaultAttributes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Mixin(DefaultAttributes.class)
public class DefaultAttributesMixin {
    private static final Map<EntityType<?>, AttributeSupplier> CACHED_SUPPLIERS = new ConcurrentHashMap<>();

    @Inject(method = "getSupplier", at = @At("RETURN"), cancellable = true)
    private static void onGetSupplier(EntityType<? extends LivingEntity> entityType, CallbackInfoReturnable<AttributeSupplier> cir) {
        AttributeSupplier original = cir.getReturnValue();
        if (original != null) {
            AttributeSupplier cached = CACHED_SUPPLIERS.get(entityType);
            if (cached != null) {
                cir.setReturnValue(cached);
                return;
            }

            if (!original.hasAttribute(AttributeRegistry.CAST_TIME_REDUCTION.get())) {
                Map<Attribute, AttributeInstance> map = new HashMap<>(((AttributeSupplierAccessor) original).getInstances());
                for (var entry : AttributeRegistry.ATTRIBUTES.getEntries()) {
                    var attribute = entry.get();
                    if (!map.containsKey(attribute)) {
                        map.put(attribute, new AttributeInstance(attribute, inst -> {}));
                    }
                }
                AttributeSupplier enhanced = new AttributeSupplier(map);
                CACHED_SUPPLIERS.put(entityType, enhanced);
                cir.setReturnValue(enhanced);
            }
        }
    }
}
