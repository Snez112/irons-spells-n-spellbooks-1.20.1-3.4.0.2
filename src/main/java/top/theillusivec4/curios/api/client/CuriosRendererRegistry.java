package top.theillusivec4.curios.api.client;

import net.minecraft.world.item.Item;

import java.util.function.Supplier;

public class CuriosRendererRegistry {
    public static void register(Item item, Supplier<ICurioRenderer> renderer) {}
}
