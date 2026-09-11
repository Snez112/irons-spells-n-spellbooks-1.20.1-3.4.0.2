package net.minecraftforge.client.gui.overlay;

import net.minecraft.resources.ResourceLocation;

public enum VanillaGuiOverlay {
    HOTBAR("hotbar"),
    EXPERIENCE_BAR("experience_bar"),
    PLAYER_LIST("player_list"),
    CHAT_PANEL("chat_panel");

    private final String id;

    VanillaGuiOverlay(String id) {
        this.id = id;
    }

    public String id() {
        return id;
    }

    public ResourceLocation idLocation() {
        return new ResourceLocation("minecraft", id);
    }
}
