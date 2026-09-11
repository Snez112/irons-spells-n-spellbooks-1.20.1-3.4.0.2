package top.theillusivec4.curios.api;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.LazyOptional;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;

import java.util.*;
import java.util.function.Predicate;

public class CuriosApi {
    private static final CuriosHelper HELPER = new CuriosHelper();

    public static CuriosHelper getCuriosHelper() {
        return HELPER;
    }

    public static LazyOptional<ICuriosItemHandler> getCuriosInventory(LivingEntity entity) {
        return LazyOptional.empty();
    }

    public static class CuriosHelper {
        public Optional<SlotResult> findFirstCurio(LivingEntity livingEntity, Item item) {
            return findFirstCurio(livingEntity, s -> s.is(item));
        }

        public Optional<SlotResult> findFirstCurio(LivingEntity livingEntity, Predicate<ItemStack> filter) {
            return Optional.empty();
        }

        public Optional<SlotResult> findCurio(LivingEntity livingEntity, String identifier, int index) {
            return Optional.empty();
        }

        public List<SlotResult> findCurios(LivingEntity livingEntity, Predicate<ItemStack> filter) {
            return Collections.emptyList();
        }

        public List<SlotResult> findCurios(LivingEntity livingEntity, Item item) {
            return Collections.emptyList();
        }

        public List<SlotResult> findCurios(LivingEntity livingEntity, String... identifiers) {
            return Collections.emptyList();
        }

        public void setEquippedCurio(LivingEntity livingEntity, String identifier, int index, ItemStack stack) {}

        public Set<String> getCurioTags(Item item) {
            return Collections.emptySet();
        }
    }
}
