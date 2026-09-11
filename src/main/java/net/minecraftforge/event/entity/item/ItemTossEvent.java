package net.minecraftforge.event.entity.item;

import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.EntityEvent;

import javax.annotation.Nullable;

public class ItemTossEvent extends EntityEvent {

    private final ItemEntity entityItem;
    private final Player player;

    public ItemTossEvent(Player player, @Nullable ItemEntity entityItem) {
        super(entityItem);
        this.player = player;
        this.entityItem = entityItem;
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public ItemEntity getEntity() {
        return entityItem;
    }

    public ItemEntity getItem() {
        return entityItem;
    }

    public ItemEntity getEntityItem() {
        return entityItem;
    }
}