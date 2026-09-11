package se.mickelus.tetra.gui.stats.getter;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.Collections;
import java.util.List;

public class TooltipGetterDecimal implements ITooltipGetter {
    private final String key;
    private final IStatGetter statGetter;

    public TooltipGetterDecimal(String key, IStatGetter statGetter) {
        this.key = key;
        this.statGetter = statGetter;
    }

    public TooltipGetterDecimal(String key, IStatGetter statGetter, double offset) {
        this.key = key;
        this.statGetter = statGetter;
    }

    @Override
    public List<String> getTooltip(Player player, ItemStack itemStack) {
        return Collections.emptyList();
    }
}
