package net.minecraftforge.fml.event.lifecycle;

import net.minecraftforge.eventbus.api.Event;

import java.util.List;
import java.util.stream.Stream;

public class InterModProcessEvent extends Event {

    public static class IMCItem {
        private final Object payload;

        public IMCItem(Object payload) {
            this.payload = payload;
        }

        public Supplier messageSupplier() {
            return () -> payload;
        }
    }

    private final List<IMCItem> items = List.of();

    public Stream<IMCItem> getIMCStream() {
        return items.stream();
    }

    public interface Supplier {
        Object get();
    }
}