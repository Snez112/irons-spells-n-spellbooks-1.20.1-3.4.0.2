package top.theillusivec4.curios.api;

import io.redspace.ironsspellbooks.compat.trinkets.TrinketsCompat;
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
            return TrinketsCompat.findFirstCurio(livingEntity, filter);
        }

        public Optional<SlotResult> findCurio(LivingEntity livingEntity, String identifier, int index) {
            return TrinketsCompat.findCurio(livingEntity, identifier, index);
        }

        public List<SlotResult> findCurios(LivingEntity livingEntity, Predicate<ItemStack> filter) {
            return TrinketsCompat.findCurios(livingEntity, filter);
        }

        public List<SlotResult> findCurios(LivingEntity livingEntity, Item item) {
            return findCurios(livingEntity, s -> s.is(item));
        }

        public List<SlotResult> findCurios(LivingEntity livingEntity, String... identifiers) {
            if (identifiers == null || identifiers.length == 0) return Collections.emptyList();
            List<SlotResult> list = new ArrayList<>();
            for (String id : identifiers) {
                findCurio(livingEntity, id, 0).ifPresent(list::add);
            }
            return list;
        }

        public void setEquippedCurio(LivingEntity livingEntity, String identifier, int index, ItemStack stack) {
            TrinketsCompat.setEquippedCurio(livingEntity, identifier, index, stack);
        }

        public Set<String> getCurioTags(Item item) {
            return Collections.emptySet();
        }
    }
}
