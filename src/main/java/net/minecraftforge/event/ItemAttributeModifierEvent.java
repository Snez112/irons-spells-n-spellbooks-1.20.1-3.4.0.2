package net.minecraftforge.event;

import com.google.common.collect.Multimap;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;

import java.util.Collection;

/**
 * Fabric-port shim of Forge's ItemAttributeModifierEvent.
 */
@Cancelable
public class ItemAttributeModifierEvent extends Event {

    private final ItemStack itemStack;
    private final EquipmentSlot slotType;
    private final Multimap<Attribute, AttributeModifier> modifiers;

    public ItemAttributeModifierEvent(ItemStack itemStack, EquipmentSlot slotType, Multimap<Attribute, AttributeModifier> modifiers) {
        this.itemStack = itemStack;
        this.slotType = slotType;
        this.modifiers = modifiers;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public EquipmentSlot getSlotType() {
        return slotType;
    }

    public Multimap<Attribute, AttributeModifier> getModifiers() {
        return modifiers;
    }

    public boolean addModifier(Attribute attribute, AttributeModifier modifier) {
        return modifiers.put(attribute, modifier);
    }

    public boolean removeModifier(Attribute attribute, AttributeModifier modifier) {
        return modifiers.remove(attribute, modifier);
    }

    public Collection<AttributeModifier> removeAttribute(Attribute attribute) {
        return modifiers.removeAll(attribute);
    }

    public void clearModifiers() {
        modifiers.clear();
    }
}