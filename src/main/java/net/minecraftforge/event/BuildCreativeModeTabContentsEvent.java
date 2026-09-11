package net.minecraftforge.event;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.eventbus.api.Event;

import java.util.ArrayList;
import java.util.List;

/**
 * Fabric-port shim of Forge's BuildCreativeModeTabContentsEvent.
 */
public class BuildCreativeModeTabContentsEvent extends Event {

    private final CreativeModeTab tab;
    private final List<ItemStack> entries = new ArrayList<>();

    public BuildCreativeModeTabContentsEvent(CreativeModeTab tab) {
        this.tab = tab;
    }

    public CreativeModeTab getTab() {
        return tab;
    }

    public void accept(ItemLike item) {
        entries.add(new ItemStack(item));
    }

    public void accept(ItemStack stack) {
        entries.add(stack);
    }

    public List<ItemStack> getEntries() {
        return entries;
    }
}
