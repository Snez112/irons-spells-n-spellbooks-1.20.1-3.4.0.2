package net.minecraftforge.items;

import net.minecraft.world.item.ItemStack;
import javax.annotation.Nonnull;

public interface IItemHandlerModifiable extends IItemHandler {
    void setStackInSlot(int slot, @Nonnull ItemStack stack);
}
