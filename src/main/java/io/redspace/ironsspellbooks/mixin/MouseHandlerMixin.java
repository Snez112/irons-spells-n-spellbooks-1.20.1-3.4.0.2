package io.redspace.ironsspellbooks.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHandler;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MouseHandler.class)
public class MouseHandlerMixin {
    @Inject(method = "onPress", at = @At("TAIL"))
    private void ironsspellbooks$onMousePress(long window, int button, int action, int mods, CallbackInfo ci) {
        if (window == Minecraft.getInstance().getWindow().getWindow()) {
            MinecraftForge.EVENT_BUS.post(new InputEvent.MouseButton.Pre(button, action, mods));
        }
    }

    @Inject(method = "onScroll", at = @At("HEAD"), cancellable = true)
    private void ironsspellbooks$onMouseScroll(long window, double horizontal, double vertical, CallbackInfo ci) {
        if (window == Minecraft.getInstance().getWindow().getWindow()) {
            var event = new InputEvent.MouseScrollingEvent(horizontal, vertical);
            if (MinecraftForge.EVENT_BUS.post(event)) {
                ci.cancel();
            }
        }
    }
}
