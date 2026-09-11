package top.theillusivec4.curios.api.type.capability;

import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotResult;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public interface ICuriosItemHandler {
    default Optional<SlotResult> findFirstCurio(Predicate<ItemStack> filter) {
        return Optional.empty();
    }

    default List<SlotResult> findCurios(Predicate<ItemStack> filter) {
        return Collections.emptyList();
    }

    default Optional<SlotResult> findCurio(String identifier, int index) {
        return Optional.empty();
    }

    default void setEquippedCurio(String identifier, int index, ItemStack stack) {}
}
