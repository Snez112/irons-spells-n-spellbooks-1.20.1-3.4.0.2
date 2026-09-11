package io.redspace.ironsspellbooks;

import com.mojang.logging.LogUtils;
import io.redspace.ironsspellbooks.api.magic.MagicHelper;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import io.redspace.ironsspellbooks.capabilities.magic.MagicEvents;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.config.ClientConfigs;
import io.redspace.ironsspellbooks.config.ServerConfigs;
import io.redspace.ironsspellbooks.gui.arcane_anvil.ArcaneAnvilScreen;
import io.redspace.ironsspellbooks.gui.inscription_table.InscriptionTableScreen;
import io.redspace.ironsspellbooks.gui.scroll_forge.ScrollForgeScreen;
import io.redspace.ironsspellbooks.api.util.CameraShakeManager;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.effect.*;
import io.redspace.ironsspellbooks.effect.guiding_bolt.GuidingBoltManager;
import io.redspace.ironsspellbooks.entity.mobs.dead_king_boss.DeadKingMusicManager;
import io.redspace.ironsspellbooks.entity.mobs.wizards.WizardAIEvents;
import io.redspace.ironsspellbooks.entity.spells.EarthquakeAoe;
import io.redspace.ironsspellbooks.loot.LootDebugEvents;
import io.redspace.ironsspellbooks.player.*;
import io.redspace.ironsspellbooks.registries.*;
import io.redspace.ironsspellbooks.setup.*;
import io.redspace.ironsspellbooks.worldgen.VillageAddition;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.*;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import java.util.stream.Collectors;

/**
 * Migrated to Fabric: fabric.mod.json entry points call {@link #init()}.
 */
public class IronsSpellbooks {
    public static final String MODID = "irons_spellbooks";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static MagicManager MAGIC_MANAGER;
    public static MinecraftServer MCS;
    public static ServerLevel OVERWORLD;

    private static final IronsSpellbooks INSTANCE = new IronsSpellbooks();

    private IronsSpellbooks() {
    }

    public static void init() {
        ModSetup.setup();

        MAGIC_MANAGER = new MagicManager();
        MagicHelper.MAGIC_MANAGER = MAGIC_MANAGER;

        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(ModSetup::init);
        modEventBus.addListener(OverlayRegistry::onRegisterOverlays);

        SoundRegistry.register(modEventBus);
        ParticleRegistry.register(modEventBus);
        AttributeRegistry.register(modEventBus);
        MobEffectRegistry.register(modEventBus);
        PotionRegistry.register(modEventBus);
        SchoolRegistry.register(modEventBus);
        SpellRegistry.register(modEventBus);
        BlockRegistry.register(modEventBus);
        ItemRegistry.register(modEventBus);
        MenuRegistry.register(modEventBus);
        EntityRegistry.register(modEventBus);
        LootRegistry.register(modEventBus);
        FeatureRegistry.register(modEventBus);
        CommandArgumentRegistry.register(modEventBus);
        StructureProcessorRegistry.register(modEventBus);
        StructureElementRegistry.register(modEventBus);
        CreativeTabRegistry.register(modEventBus);
        CommandRegistry.register(modEventBus);

        modEventBus.addListener(INSTANCE::clientSetup);

        // Config specs are read from config/*.json on Fabric
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ClientConfigs.SPEC, String.format("%s-client.toml", MODID));
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, ServerConfigs.SPEC, String.format("%s-server.toml", MODID));

        modEventBus.addListener(INSTANCE::enqueueIMC);
        modEventBus.addListener(INSTANCE::processIMC);

        FabricEventBridge.register();

        MinecraftForge.EVENT_BUS.register(CommonSetup.class);
        MinecraftForge.EVENT_BUS.register(ServerPlayerEvents.class);
        MinecraftForge.EVENT_BUS.register(DamageSources.class);
        MinecraftForge.EVENT_BUS.register(OakskinEffect.class);
        MinecraftForge.EVENT_BUS.register(EchoingStrikesEffect.class);
        MinecraftForge.EVENT_BUS.register(BlightEffect.class);
        MinecraftForge.EVENT_BUS.register(GuidingBoltManager.class);
        MinecraftForge.EVENT_BUS.register(GluttonyEffect.class);
        MinecraftForge.EVENT_BUS.register(ThunderstormEffect.class);
        MinecraftForge.EVENT_BUS.register(WizardAIEvents.class);
        MinecraftForge.EVENT_BUS.register(VillageAddition.class);
        MinecraftForge.EVENT_BUS.register(AdditionalWanderingTrades.class);
        MinecraftForge.EVENT_BUS.register(CameraShakeManager.class);
        MinecraftForge.EVENT_BUS.register(LootDebugEvents.class);
        MinecraftForge.EVENT_BUS.register(DataHandling.class);
        MinecraftForge.EVENT_BUS.register(CommandRegistry.class);

        var attributeEvent = new net.minecraftforge.event.entity.EntityAttributeCreationEvent();
        CommonSetup.onAttributeCreate(attributeEvent);
        modEventBus.post(attributeEvent);

        modEventBus.post(new InterModEnqueueEvent());
        modEventBus.post(new FMLCommonSetupEvent());
    }

    public static void initClient() {
        MinecraftForge.EVENT_BUS.register(ClientSetup.class);
        MinecraftForge.EVENT_BUS.register(ClientPlayerEvents.class);
        MinecraftForge.EVENT_BUS.register(ClientInputEvents.class);
        MinecraftForge.EVENT_BUS.register(KeyMappings.class);
        MinecraftForge.EVENT_BUS.register(PlayerAnimationTrigger.class);
        MinecraftForge.EVENT_BUS.register(DeadKingMusicManager.class);
        MinecraftForge.EVENT_BUS.register(EarthquakeAoe.class);
        MinecraftForge.EVENT_BUS.register(OverlayRegistry.class);

        var layerDefEvent = new net.minecraftforge.client.event.EntityRenderersEvent.RegisterLayerDefinitions();
        ClientSetup.onRegisterLayers(layerDefEvent);

        var renderersEvent = new net.minecraftforge.client.event.EntityRenderersEvent.RegisterRenderers();
        ClientSetup.rendererRegister(renderersEvent);
        ClientSetup.replaceRenderers(renderersEvent);

        var particleEvent = new net.minecraftforge.client.event.RegisterParticleProvidersEvent();
        ClientSetup.registerParticles(particleEvent);

        var keyMappingEvent = new net.minecraftforge.client.event.RegisterKeyMappingsEvent();
        KeyMappings.onRegisterKeybinds(keyMappingEvent);

        var overlayEvent = new net.minecraftforge.client.event.RegisterGuiOverlaysEvent();
        OverlayRegistry.onRegisterOverlays(overlayEvent);

        var clientSetupEvent = new FMLClientSetupEvent();
        ClientSetup.clientSetup(clientSetupEvent);

        var bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.post(layerDefEvent);
        bus.post(renderersEvent);
        bus.post(particleEvent);
        bus.post(keyMappingEvent);
        bus.post(overlayEvent);
        bus.post(clientSetupEvent);
    }

    private void clientSetup(final FMLClientSetupEvent e) {
        net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.INSCRIPTION_TABLE_BLOCK.get(), RenderType.cutout());
        net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.ARMOR_PILE_BLOCK.get(), RenderType.translucent());
    }

    private void enqueueIMC(final InterModEnqueueEvent event) {
        // no-op on Fabric: Curios slots are declared via data/curios/slots/*.json
    }

    private void processIMC(final InterModProcessEvent event) {
        LOGGER.info("Got IMC {}", event.getIMCStream().map(imc -> imc.messageSupplier().get()).collect(Collectors.toList()));
    }

    public static ResourceLocation id(@NotNull String path) {
        return new ResourceLocation(MODID, path);
    }
}