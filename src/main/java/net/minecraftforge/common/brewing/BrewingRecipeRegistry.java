package net.minecraftforge.common.brewing;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionBrewing;

/**
 * Fabric-port shim delegating to vanilla PotionBrewing.
 */
public class BrewingRecipeRegistry {

    public static boolean isValidIngredient(ItemStack stack) {
        return PotionBrewing.isIngredient(stack);
    }

    public static boolean hasOutput(ItemStack input, ItemStack ingredient) {
        return PotionBrewing.hasMix(input, ingredient);
    }

    public static ItemStack getOutput(ItemStack input, ItemStack ingredient) {
        return PotionBrewing.mix(ingredient, input);
    }
}
