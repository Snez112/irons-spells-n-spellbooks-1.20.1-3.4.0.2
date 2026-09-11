package net.minecraftforge.client.event;

import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.Event;

import java.util.Map;

/**
 * Fabric-port shim of Forge's ModelEvent.
 */
public class ModelEvent extends Event {

    public static class RegisterAdditional extends Event {
        private final java.util.Set<ResourceLocation> models = new java.util.HashSet<>();

        public void register(ResourceLocation model) {
            models.add(model);
        }
    }

    public static class ModifyBakingResult extends Event {
        private final Map<ModelResourceLocation, BakedModel> models;
        private final ModelBakery bakery;

        public ModifyBakingResult(Map<ModelResourceLocation, BakedModel> models, ModelBakery bakery) {
            this.models = models;
            this.bakery = bakery;
        }

        public Map<ModelResourceLocation, BakedModel> getModels() {
            return models;
        }

        public ModelBakery getModelBakery() {
            return bakery;
        }
    }
}