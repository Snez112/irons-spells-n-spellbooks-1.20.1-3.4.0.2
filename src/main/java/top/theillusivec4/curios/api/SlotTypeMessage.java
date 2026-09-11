package top.theillusivec4.curios.api;

public class SlotTypeMessage {
    public static final String REGISTER_TYPE = "register_type";
    public static class Builder {
        public Builder(String identifier) {}
        public Builder size(int size) { return this; }
        public Builder priority(int priority) { return this; }
        public Builder hide() { return this; }
        public Builder icon(net.minecraft.resources.ResourceLocation icon) { return this; }
        public SlotTypeMessage build() { return new SlotTypeMessage(); }
    }
}
