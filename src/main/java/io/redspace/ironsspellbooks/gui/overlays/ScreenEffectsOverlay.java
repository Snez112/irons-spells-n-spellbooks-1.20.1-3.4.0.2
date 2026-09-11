package io.redspace.ironsspellbooks.gui.overlays;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import io.redspace.ironsspellbooks.IronsSpellbooks;
import io.redspace.ironsspellbooks.registries.MobEffectRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class ScreenEffectsOverlay implements IGuiOverlay {
    public static final ScreenEffectsOverlay instance = new ScreenEffectsOverlay();

    public final static ResourceLocation MAGIC_AURA_TEXTURE = new ResourceLocation(IronsSpellbooks.MODID, "textures/gui/overlays/enchanted_ward_vignette.png");
    public final static ResourceLocation HEARTSTOP_TEXTURE = new ResourceLocation(IronsSpellbooks.MODID, "textures/gui/overlays/heartstop.png");

    @Override
    public void render(ForgeGui gui, GuiGraphics guiHelper, float partialTick, int screenWidth, int screenHeight) {
        Player player = Minecraft.getInstance().player;
        if (player == null) {
            return;
        }
        if (player.hasEffect(MobEffectRegistry.HEARTSTOP.get())) {
            renderOverlay(guiHelper, HEARTSTOP_TEXTURE, 0.0f, 0.8f, 0.8f, screenWidth, screenHeight);
        }
    }

    private static void renderOverlay(GuiGraphics guiHelper, ResourceLocation texture, float r, float g, float b, int screenWidth, int screenHeight) {
        guiHelper.flush();
        RenderSystem.disableDepthTest();
        RenderSystem.depthMask(false);
        RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.ZERO, GlStateManager.DestFactor.ONE_MINUS_SRC_COLOR, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        guiHelper.setColor(r, g, b, 1.0F);
        guiHelper.blit(texture, 0, 0, -90, 0.0F, 0.0F, screenWidth, screenHeight, screenWidth, screenHeight);
        guiHelper.flush();
        RenderSystem.depthMask(true);
        RenderSystem.enableDepthTest();
        guiHelper.setColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.defaultBlendFunc();
    }
}
