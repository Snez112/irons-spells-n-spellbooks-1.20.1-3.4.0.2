package se.mickelus.tetra.gui.stats.getter;

import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class StatGetterAttribute implements IStatGetter {
    protected final Attribute attribute;
    protected double offset = 0;
    protected boolean absolute = false;

    public StatGetterAttribute(Attribute attribute) {
        this.attribute = attribute;
    }

    public StatGetterAttribute(Attribute attribute, boolean absolute) {
        this.attribute = attribute;
        this.absolute = absolute;
    }

    public StatGetterAttribute withOffset(double offset) {
        this.offset = offset;
        return this;
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
