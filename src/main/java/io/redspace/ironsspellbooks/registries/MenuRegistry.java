package io.redspace.ironsspellbooks.registries;

import io.redspace.ironsspellbooks.IronsSpellbooks;
import io.redspace.ironsspellbooks.gui.arcane_anvil.ArcaneAnvilMenu;
import io.redspace.ironsspellbooks.gui.inscription_table.InscriptionTableMenu;
import io.redspace.ironsspellbooks.gui.scroll_forge.ScrollForgeMenu;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MenuRegistry {
    private static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, IronsSpellbooks.MODID);

    public static void register(IEventBus eventBus){
        MENUS.register(eventBus);
    }
    private static <T extends AbstractContainerMenu> RegistryObject<MenuType<T>> registerRegularMenuType(MenuType.MenuSupplier<T> factory, String name) {
        return MENUS.register(name, () -> new MenuType<>(factory, net.minecraft.world.flag.FeatureFlags.VANILLA_SET));
    }

    private static <T extends AbstractContainerMenu> RegistryObject<MenuType<T>> registerExtendedMenuType(IContainerFactory<T> factory, String name) {
        return MENUS.register(name, () -> new net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType<>(factory::create));
    }

    public static final RegistryObject<MenuType<InscriptionTableMenu>> INSCRIPTION_TABLE_MENU = registerRegularMenuType((syncId, inv) -> new InscriptionTableMenu(syncId, inv, (net.minecraft.network.FriendlyByteBuf) null), "inscription_table_menu");
    public static final RegistryObject<MenuType<ScrollForgeMenu>> SCROLL_FORGE_MENU = registerExtendedMenuType(ScrollForgeMenu::new, "scroll_forge_menu");
    public static final RegistryObject<MenuType<ArcaneAnvilMenu>> ARCANE_ANVIL_MENU = registerRegularMenuType((syncId, inv) -> new ArcaneAnvilMenu(syncId, inv, (net.minecraft.network.FriendlyByteBuf) null), "arcane_anvil_menu");

}
