package net.minecraftforge.event.server;

import net.minecraft.server.MinecraftServer;

public class ServerAboutToStartEvent extends ServerEvent {
    public ServerAboutToStartEvent(MinecraftServer server) {
        super(server);
    }
}