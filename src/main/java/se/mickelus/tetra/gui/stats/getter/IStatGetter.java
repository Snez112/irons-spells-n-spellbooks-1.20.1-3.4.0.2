package se.mickelus.tetra.gui.stats.getter;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public interface IStatGetter {
    double getValue(Player player, ItemStack itemStack);
    double getValue(Player player, ItemStack itemStack, String slot);
    double getValue(Player player, ItemStack itemStack, String slot, String improvement);
    boolean shouldShow(Player player, ItemStack currentStack, ItemStack previewStack);
}
