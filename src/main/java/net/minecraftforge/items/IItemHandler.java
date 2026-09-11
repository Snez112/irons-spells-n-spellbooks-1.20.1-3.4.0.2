package net.minecraftforge.items;

import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;

/**
 * Fabric-port shim of Forge's IItemHandler.
 */
public interface IItemHandler {

    int getSlots();

    @Nonnull
    ItemStack getStackInSlot(int slot);

    @Nonnull
    ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate);

    @Nonnull
    ItemStack extractItem(int slot, int amount, boolean simulate);

    int getSlotLimit(int slot);

    default int getSlots(Direction side) {
        return getSlots();
    }

    default ItemStack getStackInSlot(Direction side, int slot) {
        return getStackInSlot(slot);
    }
}