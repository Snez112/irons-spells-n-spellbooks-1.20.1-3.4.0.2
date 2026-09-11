package top.theillusivec4.caelus.api;

import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;

public class CaelusApi {
    private static final CaelusApi INSTANCE = new CaelusApi();
    private final Attribute flightAttribute = new RangedAttribute("attribute.name.caelus.flight", 0.0D, 0.0D, 1.0D).setSyncable(true);

    public static CaelusApi getInstance() {
        return INSTANCE;
    }

    public Attribute getFlightAttribute() {
        return flightAttribute;
    }
}
