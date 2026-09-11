package net.minecraftforge.common.extensions;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.network.IContainerFactory;

/**
 * Fabric-port shim for Forge's IForgeMenuType using Fabric's ExtendedScreenHandlerType.
 */
public class IForgeMenuType {

    public static <T extends AbstractContainerMenu> MenuType<T> create(IContainerFactory<T> factory) {
        return new ExtendedScreenHandlerType<>(factory::create);
    }
}
