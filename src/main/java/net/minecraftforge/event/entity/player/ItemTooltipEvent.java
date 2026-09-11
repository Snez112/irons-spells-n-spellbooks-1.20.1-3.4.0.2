package net.minecraftforge.event.entity.player;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraftforge.eventbus.api.Event;

import java.util.List;

/**
 * Fabric-port shim of Forge's ItemTooltipEvent.
 */
public class ItemTooltipEvent extends Event {

    private final ItemStack itemStack;
    private final Player player;
    private final List<Component> list;
    private final TooltipFlag flags;

    public ItemTooltipEvent(ItemStack itemStack, Player player, List<Component> list, TooltipFlag flags) {
        this.itemStack = itemStack;
        this.player = player;
        this.list = list;
        this.flags = flags;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public List<Component> getToolTip() {
        return list;
    }

    public TooltipFlag getFlags() {
        return flags;
    }

    public Player getEntity() {
        return player;
    }

    public Player getPlayer() {
        return player;
    }
}