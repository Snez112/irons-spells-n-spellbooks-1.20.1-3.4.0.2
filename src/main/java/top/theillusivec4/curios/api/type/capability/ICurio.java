package top.theillusivec4.curios.api.type.capability;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;

import java.util.UUID;

public interface ICurio {
    record SoundInfo(SoundEvent soundEvent, float volume, float pitch) {}

    default void curioTick(SlotContext slotContext) {}

    default void curioTick(SlotContext slotContext, ItemStack stack) {
        curioTick(slotContext);
    }

    default void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {}

    default void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {}

    default boolean canEquip(SlotContext slotContext) {
        return true;
    }

    default boolean canUnequip(SlotContext slotContext) {
        return true;
    }

    default Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid) {
        return HashMultimap.create();
    }

    default Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        return getAttributeModifiers(slotContext, uuid);
    }

    default SoundInfo getEquipSound(SlotContext slotContext, ItemStack stack) {
        return new SoundInfo(SoundEvents.ARMOR_EQUIP_GENERIC, 1.0f, 1.0f);
    }

    default boolean canEquipFromUse(SlotContext slotContext) {
        return false;
    }

    default void onEquipFromUse(SlotContext slotContext) {}

    default void onEquipFromUse(SlotContext slotContext, ItemStack stack) {
        onEquipFromUse(slotContext);
    }
}
