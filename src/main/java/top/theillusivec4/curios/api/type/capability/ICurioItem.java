package top.theillusivec4.curios.api.type.capability;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;

import java.util.List;
import java.util.UUID;

public interface ICurioItem extends ICurio {
    default Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        return HashMultimap.create();
    }

    default boolean hasCurio(ItemStack stack) {
        return true;
    }

    default boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return false;
    }

    default List<Component> getSlotsTooltip(List<Component> tooltips, ItemStack stack) {
        return tooltips;
    }
}
