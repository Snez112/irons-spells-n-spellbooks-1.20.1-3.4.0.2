package io.redspace.ironsspellbooks.registries;

import io.redspace.ironsspellbooks.IronsSpellbooks;
import io.redspace.ironsspellbooks.command.SpellArgument;
import net.fabricmc.fabric.api.command.v2.ArgumentTypeRegistry;
import net.minecraft.commands.synchronization.SingletonArgumentInfo;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;

public class CommandArgumentRegistry {
    public static void register(IEventBus modEventBus) {
        ArgumentTypeRegistry.registerArgumentType(
                new ResourceLocation(IronsSpellbooks.MODID, "spell"),
                SpellArgument.class,
                SingletonArgumentInfo.contextFree(SpellArgument::spellArgument)
        );
    }
}
