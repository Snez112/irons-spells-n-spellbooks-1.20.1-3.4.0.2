package dev.shadowsoffire.apotheosis.adventure.loot;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;

import java.util.function.Predicate;

public class LootCategory {
    public static final LootCategory SWORD = new LootCategory("sword");
    private final String name;

    public LootCategory(String name) {
        this.name = name;
    }

    public static LootCategory register(LootCategory parent, String name, Predicate<ItemStack> validator, EquipmentSlot[] slots) {
        return new LootCategory(name);
    }

    public static LootCategory forItem(ItemStack stack) {
        return new LootCategory("none");
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof LootCategory other)) return false;
        return name.equals(other.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
