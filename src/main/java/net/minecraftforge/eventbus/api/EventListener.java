package net.minecraftforge.eventbus.api;

/**
 * Functional listener contract matching Forge's EventListener.
 */
@FunctionalInterface
public interface EventListener<T extends Event> {
    void invoke(T event);
}