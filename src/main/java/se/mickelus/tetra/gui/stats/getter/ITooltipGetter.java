package se.mickelus.tetra.gui.stats.getter;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public interface ITooltipGetter {
    List<String> getTooltip(Player player, ItemStack itemStack);
}
