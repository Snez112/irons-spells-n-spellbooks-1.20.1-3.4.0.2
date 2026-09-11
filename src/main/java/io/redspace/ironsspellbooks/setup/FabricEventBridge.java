package io.redspace.ironsspellbooks.setup;

import io.redspace.ironsspellbooks.IronsSpellbooks;
import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityWorldChangeEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.event.server.ServerAboutToStartEvent;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.event.server.ServerStoppedEvent;
import net.minecraftforge.fml.LogicalSide;

public class FabricEventBridge {

    public static void register() {
        // Server lifecycle
        ServerLifecycleEvents.SERVER_STARTING.register(server -> {
            MinecraftForge.EVENT_BUS.post(new ServerAboutToStartEvent(server));
        });

        ServerLifecycleEvents.SERVER_STARTED.register(server -> {
            MinecraftForge.EVENT_BUS.post(new ServerStartedEvent(server));
            if (server.overworld() != null) {
                MinecraftForge.EVENT_BUS.post(new LevelEvent.Load(server.overworld()));
            }
        });

        ServerLifecycleEvents.SERVER_STOPPED.register(server -> {
            MinecraftForge.EVENT_BUS.post(new ServerStoppedEvent(server));
        });

        // Server & Level ticks
        ServerTickEvents.START_SERVER_TICK.register(server -> {
            MinecraftForge.EVENT_BUS.post(new TickEvent.ServerTickEvent(TickEvent.Phase.START, server));
        });

        ServerTickEvents.END_SERVER_TICK.register(server -> {
            MinecraftForge.EVENT_BUS.post(new TickEvent.ServerTickEvent(TickEvent.Phase.END, server));
        });

        ServerTickEvents.START_WORLD_TICK.register(world -> {
            MinecraftForge.EVENT_BUS.post(new TickEvent.LevelTickEvent(world, TickEvent.Phase.START));
        });

        ServerTickEvents.END_WORLD_TICK.register(world -> {
            MinecraftForge.EVENT_BUS.post(new TickEvent.LevelTickEvent(world, TickEvent.Phase.END));
        });

        // Player connection & lifecycle
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            MinecraftForge.EVENT_BUS.post(new PlayerEvent.PlayerLoggedInEvent(handler.getPlayer()));
        });

        ServerPlayConnectionEvents.DISCONNECT.register((handler, server) -> {
            MinecraftForge.EVENT_BUS.post(new PlayerEvent.PlayerLoggedOutEvent(handler.getPlayer()));
        });

        ServerPlayerEvents.COPY_FROM.register((oldPlayer, newPlayer, alive) -> {
            MinecraftForge.EVENT_BUS.post(new PlayerEvent.Clone(newPlayer, oldPlayer, !alive));
        });

        ServerPlayerEvents.AFTER_RESPAWN.register((oldPlayer, newPlayer, alive) -> {
            MinecraftForge.EVENT_BUS.post(new PlayerEvent.PlayerRespawnEvent(newPlayer, !alive));
        });

        ServerEntityWorldChangeEvents.AFTER_PLAYER_CHANGE_WORLD.register((player, origin, destination) -> {
            MinecraftForge.EVENT_BUS.post(new PlayerEvent.PlayerChangedDimensionEvent(player, origin.dimension(), destination.dimension()));
        });

        // Player interactions
        UseItemCallback.EVENT.register((player, world, hand) -> {
            if (world.isClientSide) {
                if (io.redspace.ironsspellbooks.player.ClientSpellCastHelper.shouldSuppressRightClicks()) {
                    return InteractionResultHolder.fail(player.getItemInHand(hand));
                }
            } else if (player instanceof net.minecraft.server.level.ServerPlayer serverPlayer) {
                var pmd = io.redspace.ironsspellbooks.api.magic.MagicData.getPlayerMagicData(serverPlayer);
                if (pmd.isCasting()) {
                    return InteractionResultHolder.fail(player.getItemInHand(hand));
                }
            }
            PlayerInteractEvent.RightClickItem event = new PlayerInteractEvent.RightClickItem(player, hand);
            if (MinecraftForge.EVENT_BUS.post(event)) {
                return InteractionResultHolder.fail(player.getItemInHand(hand));
            }
            return InteractionResultHolder.pass(ItemStack.EMPTY);
        });

        UseEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            PlayerInteractEvent.EntityInteractSpecific event = new PlayerInteractEvent.EntityInteractSpecific(player, hand, entity, hitResult != null ? hitResult.getLocation() : entity.position());
            if (MinecraftForge.EVENT_BUS.post(event)) {
                return event.getCancellationResult() != null ? event.getCancellationResult() : InteractionResult.FAIL;
            }
            return InteractionResult.PASS;
        });
    }
}
