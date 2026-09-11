package io.redspace.ironsspellbooks.render;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

/**
 * Serves as abstraction for dedicated server, which cannot comprehend this magick.
 */
public class StaffArmPose {
    @OnlyIn(Dist.CLIENT)
    public static HumanoidModel.ArmPose STAFF_ARM_POS = HumanoidModel.ArmPose.ITEM;

    public static void initializeClientHelper(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            @Override
            public HumanoidModel.@Nullable ArmPose getArmPose(LivingEntity entityLiving, InteractionHand hand, ItemStack itemStack) {
                return STAFF_ARM_POS;
            }
        });
    }
}
