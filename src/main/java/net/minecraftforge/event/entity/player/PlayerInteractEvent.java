package net.minecraftforge.event.entity.player;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.eventbus.api.Cancelable;

@Cancelable
public class PlayerInteractEvent extends PlayerEvent {

    private final InteractionHand hand;
    private final BlockPos pos;
    private InteractionResult cancellationResult = InteractionResult.PASS;

    public PlayerInteractEvent(Player player, InteractionHand hand, BlockPos pos) {
        super(player);
        this.hand = hand != null ? hand : InteractionHand.MAIN_HAND;
        this.pos = pos != null ? pos : BlockPos.ZERO;
    }

    public InteractionHand getHand() {
        return hand;
    }

    public BlockPos getPos() {
        return pos;
    }

    public Level getLevel() {
        return getEntity().level();
    }

    public ItemStack getItemStack() {
        return getEntity().getItemInHand(hand);
    }

    public InteractionResult getCancellationResult() {
        return cancellationResult;
    }

    public void setCancellationResult(InteractionResult result) {
        this.cancellationResult = result;
    }

    @Cancelable
    public static class EntityInteractSpecific extends PlayerInteractEvent {
        private final Entity target;
        private final Vec3 localPos;

        public EntityInteractSpecific(Player player, InteractionHand hand, Entity target, Vec3 localPos) {
            super(player, hand, target.blockPosition());
            this.target = target;
            this.localPos = localPos;
        }

        public Entity getTarget() {
            return target;
        }

        public Vec3 getLocalPos() {
            return localPos;
        }
    }

    @Cancelable
    public static class RightClickItem extends PlayerInteractEvent {
        public RightClickItem(Player player, InteractionHand hand) {
            super(player, hand, player.blockPosition());
        }
    }

    @Cancelable
    public static class RightClickBlock extends PlayerInteractEvent {
        private final BlockHitResult hitVec;

        public RightClickBlock(Player player, InteractionHand hand, BlockPos pos, BlockHitResult hitVec) {
            super(player, hand, pos);
            this.hitVec = hitVec;
        }

        public BlockHitResult getHitVec() {
            return hitVec;
        }

        public Direction getFace() {
            return hitVec != null ? hitVec.getDirection() : Direction.UP;
        }
    }
}