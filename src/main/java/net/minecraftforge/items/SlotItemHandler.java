package net.minecraftforge.items;

import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;

/**
 * Fabric-port shim of Forge's SlotItemHandler: an inventory Slot backed by an IItemHandler.
 */
public class SlotItemHandler extends Slot {

    private static final Container EMPTY_CONTAINER = new SimpleContainer(0);

    private final IItemHandler itemHandler;
    private final int index;

    public SlotItemHandler(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
        super(EMPTY_CONTAINER, index, xPosition, yPosition);
        this.itemHandler = itemHandler;
        this.index = index;
    }

    @Override
    public boolean mayPlace(@Nonnull ItemStack stack) {
        if (stack.isEmpty()) {
            return false;
        }
        return itemHandler.insertItem(index, stack, true).getCount() < stack.getCount();
    }

    @Override
    @Nonnull
    public ItemStack getItem() {
        return itemHandler.getStackInSlot(index);
    }

    @Override
    public void set(@Nonnull ItemStack stack) {
        if (itemHandler instanceof IItemHandlerModifiable modifiable) {
            modifiable.setStackInSlot(index, stack);
        }
        setChanged();
    }

    @Override
    public void onQuickCraft(@Nonnull ItemStack oldStack, @Nonnull ItemStack newStack) {
    }

    @Override
    public int getMaxStackSize() {
        return itemHandler.getSlotLimit(index);
    }

    @Override
    public int getMaxStackSize(@Nonnull ItemStack stack) {
        return Math.min(stack.getMaxStackSize(), itemHandler.getSlotLimit(index));
    }

    @Override
    public boolean mayPickup(Player playerIn) {
        return !itemHandler.extractItem(index, 1, true).isEmpty();
    }

    @Override
    @Nonnull
    public ItemStack remove(int amount) {
        return itemHandler.extractItem(index, amount, false);
    }

    public IItemHandler getItemHandler() {
        return itemHandler;
    }
}