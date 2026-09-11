package net.minecraftforge.event;

import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackType;
import net.minecraftforge.eventbus.api.Event;

/**
 * Fabric-port shim of Forge's AddPackFindersEvent (used for the legacy Dead King resource pack).
 */
public class AddPackFindersEvent extends Event {

    private final boolean clientPack;

    public AddPackFindersEvent(boolean clientPack) {
        this.clientPack = clientPack;
    }

    public PackType getPackType() {
        return clientPack ? PackType.CLIENT_RESOURCES : PackType.SERVER_DATA;
    }
}