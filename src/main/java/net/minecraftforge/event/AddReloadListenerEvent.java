package net.minecraftforge.event;

import net.minecraft.server.ReloadableServerResources;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraftforge.eventbus.api.Event;

/**
 * Fabric-port shim of Forge's AddReloadListenerEvent.
 */
public class AddReloadListenerEvent extends Event {

    private final ReloadableServerResources serverResources;

    public AddReloadListenerEvent(ReloadableServerResources serverResources) {
        this.serverResources = serverResources;
    }

    public AddReloadListenerEvent() {
        this(null);
    }

    public ReloadableServerResources getServerResources() {
        return serverResources;
    }

    public void addListener(PreparableReloadListener listener) {
    }
}