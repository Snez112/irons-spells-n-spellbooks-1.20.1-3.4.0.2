package net.minecraftforge.common;

import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ForgeMod {
    public static final Supplier<Attribute> ENTITY_GRAVITY = () -> new RangedAttribute("forge.entity_gravity", 0.08D, -8.0D, 8.0D).setSyncable(true);
    public static final Supplier<Attribute> ENTITY_REACH = () -> new RangedAttribute("forge.entity_reach", 5.0D, 0.0D, 1024.0D).setSyncable(true);
    public static final Supplier<Attribute> BLOCK_REACH = () -> new RangedAttribute("forge.block_reach", 4.5D, 0.0D, 1024.0D).setSyncable(true);
    public static final Supplier<Attribute> STEP_HEIGHT_ADDITION = () -> new RangedAttribute("forge.step_height_addition", 0.0D, 0.0D, 10.0D).setSyncable(true);
}
