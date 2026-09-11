package net.minecraftforge.eventbus.api;

/**
 * Base class for all (shim) Forge events. Simplified Forge semantics.
 */
public class Event {
    private boolean isCanceled = false;
    private boolean initialized = false;

    public final boolean isCanceled() {
        return isCanceled;
    }

    public final void setCanceled(boolean canceled) {
        isCanceled = canceled;
    }

    public boolean isCancelable() {
        return false;
    }

    protected final void onCancel() {
    }

    /**
     * Returns the generic (object) type carried by this event, if any.
     * Used by generic listeners registered via {@code addGenericListener}.
     */
    protected Object getGenericType() {
        return null;
    }
}