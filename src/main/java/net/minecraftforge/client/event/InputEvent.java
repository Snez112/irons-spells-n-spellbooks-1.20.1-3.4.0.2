package net.minecraftforge.client.event;

import net.minecraft.client.KeyMapping;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;

/**
 * Fabric-port shim of Forge's InputEvent.
 */
public class InputEvent extends Event {

    private final int key;
    private final int scancode;
    private final int action;
    private final int mods;

    public InputEvent(int key, int scancode, int action, int mods) {
        this.key = key;
        this.scancode = scancode;
        this.action = action;
        this.mods = mods;
    }

    public int getKey() {
        return key;
    }

    public int getScanCode() {
        return scancode;
    }

    public int getAction() {
        return action;
    }

    public int getModifiers() {
        return mods;
    }

    public static class Key extends InputEvent {
        public Key(int key, int scancode, int action, int mods) {
            super(key, scancode, action, mods);
        }
    }

    @Cancelable
    public static class MouseScrollingEvent extends Event {
        private final double scrollDeltaX;
        private final double scrollDeltaY;

        public MouseScrollingEvent(double scrollDeltaX, double scrollDeltaY) {
            this.scrollDeltaX = scrollDeltaX;
            this.scrollDeltaY = scrollDeltaY;
        }

        public double getScrollDelta() {
            return scrollDeltaY;
        }

        public double getScrollDeltaX() {
            return scrollDeltaX;
        }

        public double getScrollDeltaY() {
            return scrollDeltaY;
        }
    }

    @Cancelable
    public static class InteractionKeyMappingTriggered extends Event {
        private final KeyMapping keyMapping;
        private final boolean useItem;
        private final boolean attack;
        private boolean swingHand = true;

        public InteractionKeyMappingTriggered(KeyMapping keyMapping, boolean useItem, boolean attack) {
            this.keyMapping = keyMapping;
            this.useItem = useItem;
            this.attack = attack;
        }

        public KeyMapping getKeyMapping() {
            return keyMapping;
        }

        public boolean isUseItem() {
            return useItem;
        }

        public boolean isAttack() {
            return attack;
        }

        public boolean isSwingHand() {
            return swingHand;
        }

        public void setSwingHand(boolean swingHand) {
            this.swingHand = swingHand;
        }
    }

    public static class MouseButton extends Event {
        private final int button;
        private final int action;
        private final int mods;

        public MouseButton(int button, int action, int mods) {
            this.button = button;
            this.action = action;
            this.mods = mods;
        }

        public int getButton() {
            return button;
        }

        public int getAction() {
            return action;
        }

        public int getModifiers() {
            return mods;
        }

        @Cancelable
        public static class Pre extends MouseButton {
            public Pre(int button, int action, int mods) {
                super(button, action, mods);
            }
        }

        public static class Post extends MouseButton {
            public Post(int button, int action, int mods) {
                super(button, action, mods);
            }
        }
    }

    public static class Scroll extends Event {
    }
}