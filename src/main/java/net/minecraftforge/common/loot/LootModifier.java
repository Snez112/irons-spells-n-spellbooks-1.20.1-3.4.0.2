package net.minecraftforge.common.loot;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.function.Predicate;

public abstract class LootModifier implements IGlobalLootModifier {

    protected final LootItemCondition[] conditions;
    private final Predicate<LootContext> combinedConditions;

    public static final Codec<LootItemCondition[]> LOOT_CONDITIONS_CODEC = Codec.unit(new LootItemCondition[0]);

    protected LootModifier(LootItemCondition[] conditionsIn) {
        this.conditions = conditionsIn;
        this.combinedConditions = conditionsIn != null && conditionsIn.length > 0
                ? context -> {
                    for (LootItemCondition cond : conditionsIn) {
                        if (!cond.test(context)) return false;
                    }
                    return true;
                }
                : context -> true;
    }

    protected static <T extends LootModifier> com.mojang.datafixers.Products.P1<RecordCodecBuilder.Mu<T>, LootItemCondition[]> codecStart(RecordCodecBuilder.Instance<T> instance) {
        return instance.group(LOOT_CONDITIONS_CODEC.optionalFieldOf("conditions", new LootItemCondition[0]).forGetter(m -> m.conditions));
    }

    @Override
    public @NotNull ObjectArrayList<ItemStack> apply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        return combinedConditions.test(context) ? doApply(generatedLoot, context) : generatedLoot;
    }

    protected abstract @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context);
}
