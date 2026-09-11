package net.minecraftforge.event;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.eventbus.api.Cancelable;

/**
 * Fabric-port shim of Forge's PlayLevelSoundEvent.
 */
@Cancelable
public class PlayLevelSoundEvent extends net.minecraftforge.eventbus.api.Event {

    private final Entity sourceEntity;
    private final BlockState sourceBlockState;
    private final SoundEvent sound;
    private final float volume;
    private final float pitch;

    public PlayLevelSoundEvent(Entity sourceEntity, BlockState sourceBlockState, SoundEvent sound, float volume, float pitch) {
        this.sourceEntity = sourceEntity;
        this.sourceBlockState = sourceBlockState;
        this.sound = sound;
        this.volume = volume;
        this.pitch = pitch;
    }

    public SoundEvent getSound() {
        return sound;
    }

    public float getVolume() {
        return volume;
    }

    public float getPitch() {
        return pitch;
    }

    public Entity getSourceEntity() {
        return sourceEntity;
    }

    public BlockState getSourceBlockState() {
        return sourceBlockState;
    }
}