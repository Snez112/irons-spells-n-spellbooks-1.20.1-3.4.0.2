package io.redspace.ironsspellbooks.compat.trinkets;

import io.redspace.ironsspellbooks.IronsSpellbooks;
import io.redspace.ironsspellbooks.item.SpellBook;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.world.Container;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.SlotResult;

import com.google.common.collect.Multimap;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Predicate;

public class TrinketsCompat {
    private static Boolean isTrinketsLoaded = null;
    private static Boolean isAccessoriesLoaded = null;

    // Trinkets reflection
    private static Method getTrinketComponentMethod;
    private static Method forEachMethod;
    private static Method slotRefIndexMethod;
    private static Method slotRefInventoryMethod;
    private static Method inventoryGetSlotTypeMethod;
    private static Method slotTypeGetNameMethod;

    // Accessories reflection
    private static Method accessoriesGetOptionallyMethod;
    private static Method accessoriesGetAllEquippedMethod;
    private static Method slotEntryRefStackMethod;
    private static Method slotEntryRefReferenceMethod;
    private static Method slotRefSlotNameMethod;
    private static Method slotRefSlotMethod;
    private static Method slotRefSetStackMethod;

    public static boolean isAccessoriesLoaded() {
        if (isAccessoriesLoaded == null) {
            isAccessoriesLoaded = FabricLoader.getInstance().isModLoaded("accessories");
            if (isAccessoriesLoaded) {
                try {
                    Class<?> capClass = Class.forName("io.wispforest.accessories.api.AccessoriesCapability");
                    accessoriesGetOptionallyMethod = capClass.getMethod("getOptionally", LivingEntity.class);
                    accessoriesGetAllEquippedMethod = capClass.getMethod("getAllEquipped");

                    Class<?> slotEntryRefClass = Class.forName("io.wispforest.accessories.api.slot.SlotEntryReference");
                    slotEntryRefStackMethod = slotEntryRefClass.getMethod("stack");
                    slotEntryRefReferenceMethod = slotEntryRefClass.getMethod("reference");

                    Class<?> slotRefClass = Class.forName("io.wispforest.accessories.api.slot.SlotReference");
                    slotRefSlotNameMethod = slotRefClass.getMethod("slotName");
                    slotRefSlotMethod = slotRefClass.getMethod("slot");
                    slotRefSetStackMethod = slotRefClass.getMethod("setStack", ItemStack.class);
                    IronsSpellbooks.LOGGER.info("Accessories native integration successfully initialized.");
                } catch (Exception e) {
                    IronsSpellbooks.LOGGER.warn("Failed to initialize Accessories reflection integration: {}", e.getMessage());
                    isAccessoriesLoaded = false;
                }
            }
        }
        return isAccessoriesLoaded;
    }

    public static boolean isTrinketsLoaded() {
        if (isTrinketsLoaded == null) {
            isTrinketsLoaded = FabricLoader.getInstance().isModLoaded("trinkets");
            if (isTrinketsLoaded) {
                try {
                    Class<?> trinketsApiClass = Class.forName("dev.emi.trinkets.api.TrinketsApi");
                    getTrinketComponentMethod = trinketsApiClass.getMethod("getTrinketComponent", LivingEntity.class);

                    Class<?> trinketComponentClass = Class.forName("dev.emi.trinkets.api.TrinketComponent");
                    forEachMethod = trinketComponentClass.getMethod("forEach", BiConsumer.class);

                    Class<?> slotReferenceClass = Class.forName("dev.emi.trinkets.api.SlotReference");
                    slotRefIndexMethod = slotReferenceClass.getMethod("index");
                    slotRefInventoryMethod = slotReferenceClass.getMethod("inventory");

                    Class<?> trinketInventoryClass = Class.forName("dev.emi.trinkets.api.TrinketInventory");
                    inventoryGetSlotTypeMethod = trinketInventoryClass.getMethod("getSlotType");

                    Class<?> slotTypeClass = Class.forName("dev.emi.trinkets.api.SlotType");
                    try {
                        slotTypeGetNameMethod = slotTypeClass.getMethod("getName");
                    } catch (NoSuchMethodException e) {
                        slotTypeGetNameMethod = slotTypeClass.getMethod("getId");
                    }
                    IronsSpellbooks.LOGGER.info("Trinkets integration successfully initialized.");
                } catch (Exception e) {
                    IronsSpellbooks.LOGGER.warn("Failed to initialize Trinkets reflection integration: {}", e.getMessage());
                    isTrinketsLoaded = false;
                }
            }
        }
        return isTrinketsLoaded;
    }

    public static boolean isLoaded() {
        return isAccessoriesLoaded() || isTrinketsLoaded();
    }

    public static Optional<SlotResult> findFirstCurio(LivingEntity livingEntity, Predicate<ItemStack> filter) {
        if (!isLoaded() || livingEntity == null) return Optional.empty();

        // 1. Try Accessories native capability
        if (isAccessoriesLoaded()) {
            try {
                Optional<?> capOpt = (Optional<?>) accessoriesGetOptionallyMethod.invoke(null, livingEntity);
                if (capOpt.isPresent()) {
                    List<?> entries = (List<?>) accessoriesGetAllEquippedMethod.invoke(capOpt.get());
                    for (Object entry : entries) {
                        ItemStack stack = (ItemStack) slotEntryRefStackMethod.invoke(entry);
                        if (!stack.isEmpty() && filter.test(stack)) {
                            Object ref = slotEntryRefReferenceMethod.invoke(entry);
                            String slotName = (String) slotRefSlotNameMethod.invoke(ref);
                            int slot = (int) slotRefSlotMethod.invoke(ref);
                            return Optional.of(new SlotResult(new SlotContext(slotName, livingEntity, slot, false, true), stack));
                        }
                    }
                }
            } catch (Exception e) {
                IronsSpellbooks.LOGGER.debug("Error querying Accessories in findFirstCurio: {}", e.getMessage());
            }
        }

        // 2. Try Trinkets
        if (isTrinketsLoaded()) {
            try {
                Optional<?> compOpt = (Optional<?>) getTrinketComponentMethod.invoke(null, livingEntity);
                if (compOpt.isPresent()) {
                    final SlotResult[] result = new SlotResult[1];
                    BiConsumer<Object, ItemStack> consumer = (slotRef, stack) -> {
                        if (result[0] != null || stack.isEmpty()) return;
                        if (filter.test(stack)) {
                            String slotName = getSlotName(slotRef);
                            int index = getIndex(slotRef);
                            result[0] = new SlotResult(new SlotContext(slotName, livingEntity, index, false, true), stack);
                        }
                    };
                    forEachMethod.invoke(compOpt.get(), consumer);
                    if (result[0] != null) return Optional.of(result[0]);
                }
            } catch (Exception e) {
                IronsSpellbooks.LOGGER.debug("Error querying Trinkets in findFirstCurio: {}", e.getMessage());
            }
        }

        return Optional.empty();
    }

    public static List<SlotResult> findCurios(LivingEntity livingEntity, Predicate<ItemStack> filter) {
        if (!isLoaded() || livingEntity == null) return Collections.emptyList();
        List<SlotResult> results = new ArrayList<>();

        // 1. Try Accessories native capability
        if (isAccessoriesLoaded()) {
            try {
                Optional<?> capOpt = (Optional<?>) accessoriesGetOptionallyMethod.invoke(null, livingEntity);
                if (capOpt.isPresent()) {
                    List<?> entries = (List<?>) accessoriesGetAllEquippedMethod.invoke(capOpt.get());
                    for (Object entry : entries) {
                        ItemStack stack = (ItemStack) slotEntryRefStackMethod.invoke(entry);
                        if (!stack.isEmpty() && filter.test(stack)) {
                            Object ref = slotEntryRefReferenceMethod.invoke(entry);
                            String slotName = (String) slotRefSlotNameMethod.invoke(ref);
                            int slot = (int) slotRefSlotMethod.invoke(ref);
                            results.add(new SlotResult(new SlotContext(slotName, livingEntity, slot, false, true), stack));
                        }
                    }
                }
            } catch (Exception e) {
                IronsSpellbooks.LOGGER.debug("Error querying Accessories in findCurios: {}", e.getMessage());
            }
        }

        // 2. Try Trinkets if results empty
        if (results.isEmpty() && isTrinketsLoaded()) {
            try {
                Optional<?> compOpt = (Optional<?>) getTrinketComponentMethod.invoke(null, livingEntity);
                if (compOpt.isPresent()) {
                    BiConsumer<Object, ItemStack> consumer = (slotRef, stack) -> {
                        if (stack.isEmpty()) return;
                        if (filter.test(stack)) {
                            String slotName = getSlotName(slotRef);
                            int index = getIndex(slotRef);
                            results.add(new SlotResult(new SlotContext(slotName, livingEntity, index, false, true), stack));
                        }
                    };
                    forEachMethod.invoke(compOpt.get(), consumer);
                }
            } catch (Exception e) {
                IronsSpellbooks.LOGGER.debug("Error querying Trinkets in findCurios: {}", e.getMessage());
            }
        }

        return results;
    }

    public static Optional<SlotResult> findCurio(LivingEntity livingEntity, String identifier, int targetIndex) {
        if (!isLoaded() || livingEntity == null) return Optional.empty();

        // 1. Try Accessories native capability
        if (isAccessoriesLoaded()) {
            try {
                Optional<?> capOpt = (Optional<?>) accessoriesGetOptionallyMethod.invoke(null, livingEntity);
                if (capOpt.isPresent()) {
                    List<?> entries = (List<?>) accessoriesGetAllEquippedMethod.invoke(capOpt.get());
                    for (Object entry : entries) {
                        ItemStack stack = (ItemStack) slotEntryRefStackMethod.invoke(entry);
                        if (!stack.isEmpty()) {
                            Object ref = slotEntryRefReferenceMethod.invoke(entry);
                            String slotName = (String) slotRefSlotNameMethod.invoke(ref);
                            int slot = (int) slotRefSlotMethod.invoke(ref);

                            boolean matchesSlot = slotName.equalsIgnoreCase(identifier)
                                    || (identifier.contains("spellbook") && (slotName.contains("spellbook") || stack.getItem() instanceof SpellBook))
                                    || (identifier.contains("ring") && slotName.contains("ring"))
                                    || (identifier.contains("necklace") && slotName.contains("necklace"));

                            if (matchesSlot && (targetIndex < 0 || slot == targetIndex)) {
                                return Optional.of(new SlotResult(new SlotContext(slotName, livingEntity, slot, false, true), stack));
                            }
                        }
                    }
                }
            } catch (Exception e) {
                IronsSpellbooks.LOGGER.debug("Error querying Accessories in findCurio: {}", e.getMessage());
            }
        }

        // 2. Try Trinkets
        if (isTrinketsLoaded()) {
            try {
                Optional<?> compOpt = (Optional<?>) getTrinketComponentMethod.invoke(null, livingEntity);
                if (compOpt.isPresent()) {
                    final SlotResult[] result = new SlotResult[1];
                    BiConsumer<Object, ItemStack> consumer = (slotRef, stack) -> {
                        if (result[0] != null || stack.isEmpty()) return;
                        String slotName = getSlotName(slotRef);
                        int index = getIndex(slotRef);

                        boolean matchesSlot = slotName.equalsIgnoreCase(identifier)
                                || (identifier.contains("spellbook") && (slotName.contains("spellbook") || stack.getItem() instanceof SpellBook))
                                || (identifier.contains("ring") && slotName.contains("ring"))
                                || (identifier.contains("necklace") && slotName.contains("necklace"));

                        if (matchesSlot && (targetIndex < 0 || index == targetIndex)) {
                            result[0] = new SlotResult(new SlotContext(slotName, livingEntity, index, false, true), stack);
                        }
                    };
                    forEachMethod.invoke(compOpt.get(), consumer);
                    if (result[0] != null) return Optional.of(result[0]);
                }
            } catch (Exception e) {
                IronsSpellbooks.LOGGER.debug("Error querying Trinkets in findCurio: {}", e.getMessage());
            }
        }

        return Optional.empty();
    }

    public static void setEquippedCurio(LivingEntity livingEntity, String identifier, int targetIndex, ItemStack newStack) {
        if (!isLoaded() || livingEntity == null) return;

        // 1. Try Accessories native capability
        if (isAccessoriesLoaded()) {
            try {
                Optional<?> capOpt = (Optional<?>) accessoriesGetOptionallyMethod.invoke(null, livingEntity);
                if (capOpt.isPresent()) {
                    List<?> entries = (List<?>) accessoriesGetAllEquippedMethod.invoke(capOpt.get());
                    for (Object entry : entries) {
                        Object ref = slotEntryRefReferenceMethod.invoke(entry);
                        String slotName = (String) slotRefSlotNameMethod.invoke(ref);
                        int slot = (int) slotRefSlotMethod.invoke(ref);

                        ItemStack stack = (ItemStack) slotEntryRefStackMethod.invoke(entry);
                        boolean matchesSlot = slotName.equalsIgnoreCase(identifier)
                                || (identifier.contains("spellbook") && (slotName.contains("spellbook") || stack.getItem() instanceof SpellBook || newStack.getItem() instanceof SpellBook));

                        if (matchesSlot && (targetIndex < 0 || slot == targetIndex)) {
                            slotRefSetStackMethod.invoke(ref, newStack);
                            return;
                        }
                    }
                }
            } catch (Exception e) {
                IronsSpellbooks.LOGGER.debug("Error setting equipped in Accessories: {}", e.getMessage());
            }
        }

        // 2. Try Trinkets
        if (isTrinketsLoaded()) {
            try {
                Optional<?> compOpt = (Optional<?>) getTrinketComponentMethod.invoke(null, livingEntity);
                if (compOpt.isPresent()) {
                    final boolean[] done = new boolean[1];
                    BiConsumer<Object, ItemStack> consumer = (slotRef, stack) -> {
                        if (done[0]) return;
                        String slotName = getSlotName(slotRef);
                        int index = getIndex(slotRef);

                        boolean matchesSlot = slotName.equalsIgnoreCase(identifier)
                                || (identifier.contains("spellbook") && (slotName.contains("spellbook") || stack.getItem() instanceof SpellBook || newStack.getItem() instanceof SpellBook));

                        if (matchesSlot && (targetIndex < 0 || index == targetIndex)) {
                            try {
                                Object inventory = slotRefInventoryMethod.invoke(slotRef);
                                if (inventory instanceof Container container) {
                                    container.setItem(index, newStack);
                                    done[0] = true;
                                }
                            } catch (Exception ignored) {}
                        }
                    };
                    forEachMethod.invoke(compOpt.get(), consumer);
                }
            } catch (Exception e) {
                IronsSpellbooks.LOGGER.debug("Error setting equipped in Trinkets: {}", e.getMessage());
            }
        }
    }

    private static String getSlotName(Object slotRef) {
        try {
            Object inventory = slotRefInventoryMethod.invoke(slotRef);
            Object slotType = inventoryGetSlotTypeMethod.invoke(inventory);
            return (String) slotTypeGetNameMethod.invoke(slotType);
        } catch (Exception e) {
            return "unknown";
        }
    }

    private static int getIndex(Object slotRef) {
        try {
            return (int) slotRefIndexMethod.invoke(slotRef);
        } catch (Exception e) {
            return 0;
        }
    }

    private static final Map<LivingEntity, Map<String, Multimap<Attribute, AttributeModifier>>> appliedCurioModifiers = new WeakHashMap<>();

    public static synchronized void updateCurioAttributes(LivingEntity livingEntity) {
        if (!isLoaded() || livingEntity == null || livingEntity.getAttributes() == null) return;

        Map<String, Multimap<Attribute, AttributeModifier>> lastSlots = appliedCurioModifiers.computeIfAbsent(livingEntity, k -> new HashMap<>());
        Map<String, Multimap<Attribute, AttributeModifier>> currentSlots = new HashMap<>();

        List<SlotResult> equipped = findCurios(livingEntity, s -> !s.isEmpty() && s.getItem() instanceof ICurioItem);
        for (SlotResult slotResult : equipped) {
            ItemStack stack = slotResult.stack();
            if (stack.getItem() instanceof ICurioItem curioItem) {
                String slotKey = slotResult.slotContext().identifier() + ":" + slotResult.slotContext().index();
                UUID slotUuid = UUID.nameUUIDFromBytes(("curio:" + slotKey).getBytes(StandardCharsets.UTF_8));
                Multimap<Attribute, AttributeModifier> modifiers = curioItem.getAttributeModifiers(slotResult.slotContext(), slotUuid, stack);
                if (modifiers != null && !modifiers.isEmpty()) {
                    currentSlots.put(slotKey, modifiers);
                }
            }
        }

        // Remove old modifiers that are no longer equipped or changed
        Iterator<Map.Entry<String, Multimap<Attribute, AttributeModifier>>> it = lastSlots.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Multimap<Attribute, AttributeModifier>> entry = it.next();
            String slotKey = entry.getKey();
            Multimap<Attribute, AttributeModifier> oldMods = entry.getValue();
            Multimap<Attribute, AttributeModifier> newMods = currentSlots.get(slotKey);

            if (newMods == null || !newMods.equals(oldMods)) {
                livingEntity.getAttributes().removeAttributeModifiers(oldMods);
                it.remove();
            }
        }

        // Add newly equipped or changed modifiers
        for (Map.Entry<String, Multimap<Attribute, AttributeModifier>> entry : currentSlots.entrySet()) {
            String slotKey = entry.getKey();
            Multimap<Attribute, AttributeModifier> newMods = entry.getValue();

            if (!lastSlots.containsKey(slotKey)) {
                livingEntity.getAttributes().addTransientAttributeModifiers(newMods);
                lastSlots.put(slotKey, newMods);
            }
        }
    }
}
