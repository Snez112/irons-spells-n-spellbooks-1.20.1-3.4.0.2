package net.minecraftforge.data.event;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.Event;

import java.util.concurrent.CompletableFuture;

public class GatherDataEvent extends Event {
    private final DataGenerator generator;
    private final CompletableFuture<HolderLookup.Provider> lookupProvider;
    private final ExistingFileHelper existingFileHelper;
    private final boolean includeServer;
    private final boolean includeClient;

    private final PackOutput packOutput;

    public GatherDataEvent(DataGenerator generator, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper existingFileHelper, boolean includeServer, boolean includeClient) {
        this(generator, new PackOutput(java.nio.file.Path.of("generated")), lookupProvider, existingFileHelper, includeServer, includeClient);
    }

    public GatherDataEvent(DataGenerator generator, PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper existingFileHelper, boolean includeServer, boolean includeClient) {
        this.generator = generator;
        this.packOutput = packOutput != null ? packOutput : new PackOutput(java.nio.file.Path.of("generated"));
        this.lookupProvider = lookupProvider;
        this.existingFileHelper = existingFileHelper;
        this.includeServer = includeServer;
        this.includeClient = includeClient;
    }

    public DataGenerator getGenerator() {
        return generator;
    }

    public PackOutput getPackOutput() {
        return packOutput;
    }

    public CompletableFuture<HolderLookup.Provider> getLookupProvider() {
        return lookupProvider;
    }

    public ExistingFileHelper getExistingFileHelper() {
        return existingFileHelper;
    }

    public boolean includeServer() {
        return includeServer;
    }

    public boolean includeClient() {
        return includeClient;
    }
}
