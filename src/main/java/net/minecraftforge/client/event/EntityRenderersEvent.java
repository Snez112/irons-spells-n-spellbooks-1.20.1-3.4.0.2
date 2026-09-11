package net.minecraftforge.client.event;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.Event;

import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Fabric-port shim of Forge's EntityRenderersEvent.
 */
public class EntityRenderersEvent extends Event {

    public static class RegisterRenderers extends Event {
        @SuppressWarnings("unchecked")
        public <T extends Entity, R extends EntityRenderer> void registerEntityRenderer(EntityType<T> type, Function<EntityRendererProvider.Context, R> factory) {
            net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry.register(type, factory::apply);
        }

        @SuppressWarnings("unchecked")
        public <T extends net.minecraft.world.level.block.entity.BlockEntity, R> void registerBlockEntityRenderer(BlockEntityType<T> type, Function<BlockEntityRendererProvider.Context, R> factory) {
            net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry.register(type, ctx -> (net.minecraft.client.renderer.blockentity.BlockEntityRenderer<T>) factory.apply(ctx));
        }
    }

    public static class RegisterLayerDefinitions extends Event {

        public void registerLayerDefinition(net.minecraft.client.model.geom.ModelLayerLocation modelLayerLocation, Supplier<net.minecraft.client.model.geom.builders.LayerDefinition> supplier) {
            net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry.registerModelLayer(modelLayerLocation, supplier::get);
        }

        public void registerLayerDefinition(ResourceLocation entityModelRenderer, Supplier<net.minecraft.client.model.geom.builders.LayerDefinition> supplier) {
        }

        public void registerLayerDefinition(Class<?> type, Supplier<net.minecraft.client.model.geom.builders.LayerDefinition> supplier) {
        }
    }

    public static class AddLayers extends Event {

        @SuppressWarnings("unchecked")
        public <T extends LivingEntity, R extends LivingEntityRenderer<T, ? extends EntityModel<T>>> R getRenderer(EntityType<? extends T> entityType) {
            return null;
        }

        public LivingEntityRenderer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> getSkin(String skinName) {
            return null;
        }
    }
}