package net.minecraftforge.client.event;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import net.minecraftforge.eventbus.api.Event;

import java.util.ArrayList;
import java.util.List;

/**
 * Fabric-port shim of Forge's RegisterGuiOverlaysEvent.
 */
public class RegisterGuiOverlaysEvent extends Event {

    public record Entry(String reference, String type, String id, IGuiOverlay overlay) {}

    private final List<Entry> registrations = new ArrayList<>();
    private static ForgeGui forgeGuiInstance;

    public void registerAbove(String reference, String id, IGuiOverlay overlay) {
        registrations.add(new Entry(reference, "above", id, overlay));
        registerHudCallback(overlay);
    }

    public void registerBelow(String reference, String id, IGuiOverlay overlay) {
        registrations.add(new Entry(reference, "below", id, overlay));
        registerHudCallback(overlay);
    }

    public void register(String reference, String id, IGuiOverlay overlay) {
        registerAbove(reference, id, overlay);
    }

    private void registerHudCallback(IGuiOverlay overlay) {
        HudRenderCallback.EVENT.register((guiGraphics, tickDelta) -> {
            Minecraft mc = Minecraft.getInstance();
            if (mc.player != null && !mc.options.hideGui) {
                if (forgeGuiInstance == null) {
                    forgeGuiInstance = new ForgeGui(mc);
                }
                int screenWidth = mc.getWindow().getGuiScaledWidth();
                int screenHeight = mc.getWindow().getGuiScaledHeight();
                overlay.render(forgeGuiInstance, guiGraphics, tickDelta, screenWidth, screenHeight);
            }
        });
    }

    public List<Entry> getRegistrations() {
        return registrations;
    }
}