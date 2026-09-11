package net.minecraftforge.event.village;

import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraftforge.eventbus.api.Event;

import java.util.ArrayList;
import java.util.List;

public class WandererTradesEvent extends Event {
    private final List<VillagerTrades.ItemListing> genericTrades = new ArrayList<>();
    private final List<VillagerTrades.ItemListing> rareTrades = new ArrayList<>();

    public List<VillagerTrades.ItemListing> getGenericTrades() {
        return genericTrades;
    }

    public List<VillagerTrades.ItemListing> getRareTrades() {
        return rareTrades;
    }
}