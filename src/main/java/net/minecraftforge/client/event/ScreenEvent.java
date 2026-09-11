package net.minecraftforge.client.event;

import net.minecraftforge.eventbus.api.Event;

/**
 * Fabric-port shim of Forge's ScreenEvent.
 */
public class ScreenEvent extends Event {

    private final Object screen;

    public ScreenEvent(Object screen) {
        this.screen = screen;
    }

    public Object getScreen() {
        return screen;
    }

    public static class Opening extends ScreenEvent {
        public Opening(Object screen) {
            super(screen);
        }
    }

    public static class Closing extends ScreenEvent {
        public Closing(Object screen) {
            super(screen);
        }
    }
}