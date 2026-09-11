package net.minecraftforge.eventbus.api;

/**
 * Fabric-port shim of Forge's IEventBus.
 */
public interface IEventBus {
    /**
     * Register a listener via a method/lambda reference. The event type is inferred.
     */
    <T extends Event> void addListener(EventListener<T> listener);

    /**
     * Register a listener with an explicit priority.
     */
    default <T extends Event> void addListener(EventListener<T> listener, EventPriority priority) {
        addListener(listener);
    }

    /**
     * Register a listener for the given event type.
     */
    default <T extends Event> void addListener(Class<? extends Event> eventType, EventListener<T> listener) {
        addListener(listener);
    }

    /**
     * Register a listener that receives events whose generic object type is assignable to {@code objectType}.
     */
    <T extends Event> void addGenericListener(Class<?> objectType, EventListener<T> listener);

    /**
     * Posts an event to all registered listeners. Returns true if canceled.
     */
    boolean post(Event event);

    /**
     * Registers all @SubscribeEvent methods of the given instance.
     */
    void register(Object listener);

    /**
     * Registers all static @SubscribeEvent methods of the given class.
     */
    void register(Class<?> listenerClass);

    void unregister(Object listener);

    void unregister(Class<?> listenerClass);

    boolean hasListeners(Class<? extends Event> eventType);
}