package top.theillusivec4.curios.api.event;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingEvent;

public class CurioChangeEvent extends LivingEvent {
    private final String identifier;
    private final int index;
    private final ItemStack from;
    private final ItemStack to;

    public CurioChangeEvent(LivingEntity entity, String identifier, int index, ItemStack from, ItemStack to) {
        super(entity);
        this.identifier = identifier;
        this.index = index;
        this.from = from;
        this.to = to;
    }

    public String getIdentifier() { return identifier; }
    public int getIndex() { return index; }
    public ItemStack getFrom() { return from; }
    public ItemStack getTo() { return to; }
}
