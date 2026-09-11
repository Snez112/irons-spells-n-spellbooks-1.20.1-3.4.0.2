package top.theillusivec4.curios.api.event;

import com.google.common.collect.Multimap;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.Event;
import top.theillusivec4.curios.api.SlotContext;

import java.util.UUID;

public class CurioAttributeModifierEvent extends Event {
    private final ItemStack stack;
    private final SlotContext slotContext;
    private final UUID uuid;
    private final Multimap<Attribute, AttributeModifier> modifiers;

    public CurioAttributeModifierEvent(ItemStack stack, SlotContext slotContext, UUID uuid, Multimap<Attribute, AttributeModifier> modifiers) {
        this.stack = stack;
        this.slotContext = slotContext;
        this.uuid = uuid;
        this.modifiers = modifiers;
    }

    public ItemStack getItemStack() { return stack; }
    public SlotContext getSlotContext() { return slotContext; }
    public UUID getUuid() { return uuid; }
    public Multimap<Attribute, AttributeModifier> getModifiers() { return modifiers; }
    public void addModifier(Attribute attribute, AttributeModifier modifier) { modifiers.put(attribute, modifier); }
    public void removeModifier(Attribute attribute, AttributeModifier modifier) { modifiers.remove(attribute, modifier); }
}
