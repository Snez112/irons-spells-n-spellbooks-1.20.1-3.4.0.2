package io.redspace.ironsspellbooks.fabric.client;

import io.redspace.ironsspellbooks.IronsSpellbooks;
import net.fabricmc.api.ClientModInitializer;
import net.minecraftforge.fml.DistExecutor;

/**
 * Fabric client entry point (fabric.mod.json "client").
 */
public class FabricIronsSpellbooksClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        DistExecutor.markClient();
        IronsSpellbooks.initClient();

        net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin.register(pluginContext -> {
            for (io.redspace.ironsspellbooks.api.spells.SchoolType schoolType : io.redspace.ironsspellbooks.api.registry.SchoolRegistry.REGISTRY.get().getValues()) {
                net.minecraft.resources.ResourceLocation scrollLoc = io.redspace.ironsspellbooks.render.ScrollModel.getScrollModelLocation(schoolType);
                pluginContext.addModels(scrollLoc);
                pluginContext.addModels(io.redspace.ironsspellbooks.render.AffinityRingRenderer.getAffinityRingModelLocation(schoolType));
            }

            pluginContext.modifyModelAfterBake().register((originalModel, context) -> {
                if (context.id() != null &&
                    context.id().getNamespace().equals(IronsSpellbooks.MODID) &&
                    context.id().getPath().equals("item/scroll")) {
                    return new io.redspace.ironsspellbooks.render.ScrollModel(originalModel, context.loader());
                }
                return originalModel;
            });
        });

        net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> {
            if (client.player != null) {
                io.redspace.ironsspellbooks.player.ClientMagicData.updateSpellSelectionManager();
            }
        });

        net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> {
            io.redspace.ironsspellbooks.player.ClientMagicData.resetClientCastState(null);
        });

        net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.options != null) {
                if (!client.options.keyUse.isDown()) {
                    io.redspace.ironsspellbooks.player.ClientSpellCastHelper.setSuppressRightClicks(false);
                    io.redspace.ironsspellbooks.player.ClientInputEvents.isUseKeyDown = false;
                    io.redspace.ironsspellbooks.player.ClientInputEvents.hasReleasedSinceCasting = true;
                } else {
                    io.redspace.ironsspellbooks.player.ClientInputEvents.isUseKeyDown = true;
                }
            }
            if (client.player != null) {
                checkEquipmentUpdates(client.player);
                net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.event.TickEvent.PlayerTickEvent(
                    client.player,
                    net.minecraftforge.event.TickEvent.Phase.END,
                    net.minecraftforge.fml.LogicalSide.CLIENT
                ));
            }
            net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.event.TickEvent.ClientTickEvent(
                net.minecraftforge.event.TickEvent.Phase.END
            ));
        });
    }

    private static final net.minecraft.world.item.ItemStack[] lastEquipment = new net.minecraft.world.item.ItemStack[net.minecraft.world.entity.EquipmentSlot.values().length];
    private static net.minecraft.world.item.ItemStack lastSpellbook = net.minecraft.world.item.ItemStack.EMPTY;

    private static void checkEquipmentUpdates(net.minecraft.world.entity.player.Player player) {
        boolean changed = false;
        var slots = net.minecraft.world.entity.EquipmentSlot.values();
        for (int i = 0; i < slots.length; i++) {
            var current = player.getItemBySlot(slots[i]);
            if (!net.minecraft.world.item.ItemStack.matches(lastEquipment[i] != null ? lastEquipment[i] : net.minecraft.world.item.ItemStack.EMPTY, current)) {
                lastEquipment[i] = current.copy();
                changed = true;
            }
        }
        var curio = io.redspace.ironsspellbooks.api.util.Utils.getPlayerSpellbookStack(player);
        if (curio == null) curio = net.minecraft.world.item.ItemStack.EMPTY;
        if (!net.minecraft.world.item.ItemStack.matches(lastSpellbook, curio)) {
            lastSpellbook = curio.copy();
            changed = true;
        }
        if (changed || io.redspace.ironsspellbooks.player.ClientMagicData.getSpellSelectionManager() == null) {
            io.redspace.ironsspellbooks.player.ClientMagicData.updateSpellSelectionManager();
        }
    }

    public static boolean isClientLoaded() {
        return DistExecutor.isClient();
    }
}