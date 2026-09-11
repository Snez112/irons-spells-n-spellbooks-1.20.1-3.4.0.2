package se.mickelus.tetra.gui.stats.getter;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import se.mickelus.tetra.effect.ItemEffect;

public class StatGetterEffectLevel implements IStatGetter {
    private final ItemEffect effect;
    private final double multiplier;

    public StatGetterEffectLevel(ItemEffect effect, double multiplier) {
        this.effect = effect;
        this.multiplier = multiplier;
    }

    @Override
    public double getValue(Player player, ItemStack itemStack) {
        return 0;
    }

    @Override
    public double getValue(Player player, ItemStack itemStack, String slot) {
        return 0;
    }

    @Override
    public double getValue(Player player, ItemStack itemStack, String slot, String improvement) {
        return 0;
    }

    @Override
    public boolean shouldShow(Player player, ItemStack currentStack, ItemStack previewStack) {
        return false;
    }
}
