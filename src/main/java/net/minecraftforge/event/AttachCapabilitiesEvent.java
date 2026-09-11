package net.minecraftforge.event;

import io.redspace.ironsspellbooks.capabilities.magic.PlayerMagicProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.eventbus.api.Event;

import java.util.ArrayList;
import java.util.List;

/**
 * Fabric-port shim of Forge's AttachCapabilitiesEvent.
 */
public class AttachCapabilitiesEvent<T> extends Event {

    private final T object;
    private final List<ResourceLocation> keys = new ArrayList<>();
    private final List<ICapabilityProvider> providers = new ArrayList<>();

    public AttachCapabilitiesEvent(T object) {
        this.object = object;
    }

    public T getObject() {
        return object;
    }

    @Override
    protected Object getGenericType() {
        return object;
    }

    public void addCapability(ResourceLocation key, ICapabilityProvider provider) {
        keys.add(key);
        providers.add(provider);
        if (object != null) {
            CapabilityManager.attach(object, PlayerMagicProvider.PLAYER_MAGIC, provider.getCapability(PlayerMagicProvider.PLAYER_MAGIC));
        }
    }

    public List<ResourceLocation> getCapabilities() {
        return List.copyOf(keys);
    }
}