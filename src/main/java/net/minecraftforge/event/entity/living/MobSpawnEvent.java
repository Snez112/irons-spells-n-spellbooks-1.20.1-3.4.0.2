package net.minecraftforge.event.entity.living;

import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraftforge.eventbus.api.Cancelable;

public class MobSpawnEvent extends LivingEvent {

    public MobSpawnEvent(Mob mob) {
        super(mob);
    }

    @Override
    public Mob getEntity() {
        return (Mob) super.getEntity();
    }

    @Cancelable
    public static class FinalizeSpawn extends MobSpawnEvent {
        private final ServerLevelAccessor level;
        private final DifficultyInstance difficulty;
        private final MobSpawnType spawnType;
        private final SpawnGroupData spawnData;

        public FinalizeSpawn(Mob mob, ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, SpawnGroupData spawnData) {
            super(mob);
            this.level = level;
            this.difficulty = difficulty;
            this.spawnType = spawnType;
            this.spawnData = spawnData;
        }

        public ServerLevelAccessor getLevel() {
            return level;
        }

        public DifficultyInstance getDifficulty() {
            return difficulty;
        }

        public MobSpawnType getSpawnType() {
            return spawnType;
        }

        public SpawnGroupData getSpawnData() {
            return spawnData;
        }
    }
}
