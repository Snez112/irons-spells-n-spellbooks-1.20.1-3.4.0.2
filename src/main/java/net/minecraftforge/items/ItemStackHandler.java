package net.minecraftforge.items;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.INBTSerializable;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * Fabric-port shim of Forge's ItemStackHandler: an in-memory inventory.
 */
public class ItemStackHandler implements IItemHandlerModifiable, INBTSerializable<CompoundTag> {

    protected final List<ItemStack> stacks;

    public ItemStackHandler() {
        this(1);
    }

    public ItemStackHandler(int size) {
        this.stacks = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            stacks.add(ItemStack.EMPTY);
        }
    }

    public void setSize(int size) {
        while (stacks.size() < size) {
            stacks.add(ItemStack.EMPTY);
        }
        while (stacks.size() > size) {
            stacks.remove(stacks.size() - 1);
        }
    }

    @Override
    public int getSlots() {
        return stacks.size();
    }

    @Override
    @Nonnull
    public ItemStack getStackInSlot(int slot) {
        return slot >= 0 && slot < stacks.size() ? stacks.get(slot) : ItemStack.EMPTY;
    }

    @Override
    @Nonnull
    public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
        if (slot < 0 || slot >= stacks.size() || stack.isEmpty()) {
            return stack;
        }
        ItemStack existing = stacks.get(slot);
        if (existing.isEmpty()) {
            if (!simulate) {
                stacks.set(slot, stack.copy());
                onContentsChanged(slot);
            }
            return ItemStack.EMPTY;
        }
        if (ItemStack.isSameItemSameTags(existing, stack) && existing.getCount() < existing.getMaxStackSize()) {
            int room = existing.getMaxStackSize() - existing.getCount();
            int taken = Math.min(room, stack.getCount());
            if (!simulate) {
                existing.grow(taken);
                onContentsChanged(slot);
            }
            if (taken == stack.getCount()) {
                return ItemStack.EMPTY;
            }
            stack = stack.copy();
            stack.shrink(taken);
            return stack;
        }
        return stack;
    }

    @Override
    @Nonnull
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        if (slot < 0 || slot >= stacks.size() || amount <= 0) {
            return ItemStack.EMPTY;
        }
        ItemStack existing = stacks.get(slot);
        if (existing.isEmpty()) {
            return ItemStack.EMPTY;
        }
        int taken = Math.min(amount, existing.getCount());
        ItemStack result = existing.copy();
        result.setCount(taken);
        if (!simulate) {
            existing.shrink(taken);
            if (existing.isEmpty()) {
                stacks.set(slot, ItemStack.EMPTY);
            }
            onContentsChanged(slot);
        }
        return result;
    }

    @Override
    public int getSlotLimit(int slot) {
        return 64;
    }

    @Override
    public void setStackInSlot(int slot, @Nonnull ItemStack stack) {
        if (slot >= 0 && slot < stacks.size()) {
            stacks.set(slot, stack);
            onContentsChanged(slot);
        }
    }

    protected void onContentsChanged(int slot) {
    }

    protected void onLoad() {
    }

    @Override
    public CompoundTag serializeNBT() {
        ListTag nbtTagList = new ListTag();
        for (int i = 0; i < stacks.size(); i++) {
            if (!stacks.get(i).isEmpty()) {
                CompoundTag itemTag = new CompoundTag();
                itemTag.putInt("Slot", i);
                stacks.get(i).save(itemTag);
                nbtTagList.add(itemTag);
            }
        }
        CompoundTag nbt = new CompoundTag();
        nbt.put("Items", nbtTagList);
        nbt.putInt("Size", stacks.size());
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        setSize(nbt.contains("Size", Tag.TAG_INT) ? nbt.getInt("Size") : stacks.size());
        ListTag tagList = nbt.getList("Items", Tag.TAG_COMPOUND);
        for (int i = 0; i < tagList.size(); i++) {
            CompoundTag itemTags = tagList.getCompound(i);
            int slot = itemTags.getInt("Slot");
            if (slot >= 0 && slot < stacks.size()) {
                stacks.set(slot, ItemStack.of(itemTags));
            }
        }
        onLoad();
    }
}