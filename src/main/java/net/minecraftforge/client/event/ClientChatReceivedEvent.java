package net.minecraftforge.client.event;

import net.minecraft.network.chat.Component;
import net.minecraftforge.eventbus.api.Event;

import java.util.UUID;

public class ClientChatReceivedEvent extends Event {

    private final Component message;
    private final UUID sender;

    public ClientChatReceivedEvent(Component message, UUID sender) {
        this.message = message;
        this.sender = sender;
    }

    public Component getMessage() {
        return message;
    }

    public UUID getSender() {
        return sender;
    }
}
