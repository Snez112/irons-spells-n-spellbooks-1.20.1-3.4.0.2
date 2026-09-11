package se.mickelus.tetra.items.modular;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import se.mickelus.tetra.effect.ItemEffect;

public class ModularItem extends Item {
    public ModularItem(Properties properties) {
        super(properties);
    }

    public int getEffectLevel(ItemStack stack, ItemEffect effect) {
        return 0;
    }

    public double getEffectEfficiency(ItemStack stack, ItemEffect effect) {
        return 0.0;
    }
}
