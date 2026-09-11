package se.mickelus.tetra.gui.stats.getter;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public interface ILabelGetter {
    String getLabel(double value, double diffValue, boolean flipped);
}
