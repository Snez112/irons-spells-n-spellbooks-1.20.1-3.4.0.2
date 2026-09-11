package io.redspace.ironsspellbooks.mixin;

import io.redspace.ironsspellbooks.registries.MobEffectRegistry;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public class PlayerMixin {

    @Inject(method = "canEat", at = @At(value = "RETURN"), cancellable = true)
    void canEatForGluttony(boolean pCanAlwaysEat, CallbackInfoReturnable<Boolean> cir) {
        if (((Player) (Object) this).hasEffect(MobEffectRegistry.GLUTTONY.get())) {
            cir.setReturnValue(true);
        }
    }

    @Inject(method = "addAdditionalSaveData", at = @At("HEAD"))
    private void addAdditionalSaveData(net.minecraft.nbt.CompoundTag tag, org.spongepowered.asm.mixin.injection.callback.CallbackInfo ci) {
        try {
            net.minecraft.nbt.CompoundTag magicTag = new net.minecraft.nbt.CompoundTag();
            io.redspace.ironsspellbooks.api.magic.MagicData.getPlayerMagicData((Player) (Object) this).saveNBTData(magicTag);
            tag.put("irons_spellbooks_magic_data", magicTag);
        } catch (Exception e) {
            io.redspace.ironsspellbooks.IronsSpellbooks.LOGGER.error("Failed saving player magic data", e);
        }
    }

    @Inject(method = "readAdditionalSaveData", at = @At("HEAD"))
    private void readAdditionalSaveData(net.minecraft.nbt.CompoundTag tag, org.spongepowered.asm.mixin.injection.callback.CallbackInfo ci) {
        try {
            if (tag.contains("irons_spellbooks_magic_data")) {
                io.redspace.ironsspellbooks.api.magic.MagicData.getPlayerMagicData((Player) (Object) this).loadNBTData(tag.getCompound("irons_spellbooks_magic_data"));
            } else if (tag.contains("ForgeCaps")) {
                net.minecraft.nbt.CompoundTag forgeCaps = tag.getCompound("ForgeCaps");
                if (forgeCaps.contains("irons_spellbooks:player_magic")) {
                    io.redspace.ironsspellbooks.api.magic.MagicData.getPlayerMagicData((Player) (Object) this).loadNBTData(forgeCaps.getCompound("irons_spellbooks:player_magic"));
                }
            }
        } catch (Exception e) {
            io.redspace.ironsspellbooks.IronsSpellbooks.LOGGER.error("Failed reading player magic data", e);
        }
    }
}
