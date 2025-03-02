package me.basiqueevangelist.flashfreeze.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.mojang.serialization.Codec;
import me.basiqueevangelist.flashfreeze.FlashFreeze;
import me.basiqueevangelist.flashfreeze.util.AlternativeCodec;
import me.basiqueevangelist.flashfreeze.util.SerializedItemStack;
import net.minecraft.world.item.ItemStack;
import me.basiqueevangelist.flashfreeze.item.FlashFreezeDataComponents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {
    @ModifyReturnValue(method = "method_57352", at = @At("RETURN"))
    private static Codec<ItemStack> wrapCodec(Codec<ItemStack> original) {
        Codec<ItemStack> unknownStackCodec = SerializedItemStack.CODEC
            .validate(SerializedItemStack::validateUnknown)
            .xmap(x -> {
                ItemStack stack = new ItemStack(FlashFreeze.UNKNOWN_ITEM, x.count());
                stack.applyComponentsAndValidate(x.components());
                stack.set(FlashFreezeDataComponents.ORIGINAL_ITEM_ID, x.id());
                return stack;
            }, SerializedItemStack::from);

        return new AlternativeCodec<>(unknownStackCodec, original);
    }

    @ModifyReturnValue(method = "method_55066", at = @At("RETURN"))
    private static Codec<ItemStack> wrapCodec2(Codec<ItemStack> original) {
        Codec<ItemStack> unknownStackCodec = SerializedItemStack.UNCOUNTED_CODEC
            .validate(SerializedItemStack::validateUnknown)
            .xmap(x -> {
                ItemStack stack = new ItemStack(FlashFreeze.UNKNOWN_ITEM, x.count());
                stack.applyComponentsAndValidate(x.components());
                stack.set(FlashFreezeDataComponents.ORIGINAL_ITEM_ID, x.id());
                return stack;
            }, SerializedItemStack::from);

        return new AlternativeCodec<>(unknownStackCodec, original);
    }
}
