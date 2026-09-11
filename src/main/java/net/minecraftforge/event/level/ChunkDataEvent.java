package net.minecraftforge.event.level;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraftforge.eventbus.api.Event;

public class ChunkDataEvent extends Event {

    private final ServerLevel level;
    private final ChunkAccess chunk;
    private final ChunkPos chunkPos;
    private final CompoundTag data;

    public ChunkDataEvent(ServerLevel level, ChunkAccess chunk, CompoundTag data) {
        this.level = level;
        this.chunk = chunk;
        this.chunkPos = chunk != null ? chunk.getPos() : new ChunkPos(0, 0);
        this.data = data != null ? data : new CompoundTag();
    }

    public ChunkDataEvent(ServerLevel level, ChunkAccess chunk) {
        this(level, chunk, new CompoundTag());
    }

    public ServerLevel getLevel() {
        return level;
    }

    public ChunkAccess getChunk() {
        return chunk;
    }

    public ChunkPos getChunkPos() {
        return chunkPos;
    }

    public CompoundTag getData() {
        return data;
    }

    public static class Load extends ChunkDataEvent {
        public Load(ServerLevel level, ChunkAccess chunk, CompoundTag data) {
            super(level, chunk, data);
        }
    }

    public static class Save extends ChunkDataEvent {
        public Save(ServerLevel level, ChunkAccess chunk, CompoundTag data) {
            super(level, chunk, data);
        }
    }
}