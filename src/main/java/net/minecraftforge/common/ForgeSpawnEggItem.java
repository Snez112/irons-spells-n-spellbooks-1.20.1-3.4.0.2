package net.minecraftforge.common;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;

import java.util.function.Supplier;

public class ForgeSpawnEggItem extends SpawnEggItem {

    private final Supplier<? extends EntityType<? extends Mob>> typeSupplier;

    public ForgeSpawnEggItem(Supplier<? extends EntityType<? extends Mob>> type, int primaryColor, int secondaryColor, Item.Properties builder) {
        super(EntityType.PIG, primaryColor, secondaryColor, builder);
        this.typeSupplier = type;
    }

    @Override
    public EntityType<?> getType(CompoundTag tag) {
        if (tag != null && tag.contains("EntityTag", 10)) {
            CompoundTag compoundTag = tag.getCompound("EntityTag");
            if (compoundTag.contains("id", 8)) {
                return EntityType.byString(compoundTag.getString("id")).orElseGet(typeSupplier::get);
            }
        }
        return typeSupplier.get();
    }
}
