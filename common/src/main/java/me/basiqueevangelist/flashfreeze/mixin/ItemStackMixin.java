package me.basiqueevangelist.flashfreeze.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import me.basiqueevangelist.flashfreeze.FlashFreeze;
import me.basiqueevangelist.flashfreeze.item.FlashFreezeDataComponents;
import me.basiqueevangelist.flashfreeze.util.AlternativeCodec;
import me.basiqueevangelist.flashfreeze.util.SerializedItemStack;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {
    @Mutable
    @Shadow @Final public static MapCodec<ItemStack> MAP_CODEC;

    @Shadow @Final public static Codec<ItemStack> CODEC;

    @ModifyExpressionValue(method = "<clinit>", at = @At(value = "INVOKE", target = "Lcom/mojang/serialization/Codec;lazyInitialized(Ljava/util/function/Supplier;)Lcom/mojang/serialization/Codec;"))
    private static Codec<ItemStack> wrapCodec(Codec<ItemStack> original) {
        Codec<ItemStack> unknownStackCodec = SerializedItemStack.CODEC
            .validate(SerializedItemStack::validateUnknown)
            .xmap(x -> {
                ItemStack stack = new ItemStack(FlashFreeze.UNKNOWN_ITEM.get(), x.count());
                stack.applyComponentsAndValidate(x.components());
                stack.set(FlashFreezeDataComponents.ORIGINAL_ITEM_ID, x.id());
                return stack;
            }, SerializedItemStack::from);

        return new AlternativeCodec<>(unknownStackCodec, original);
    }

    @ModifyReturnValue(method = {"lambda$static$6", "method_55066"}, at = @At("RETURN"))
    private static Codec<ItemStack> wrapCodec2(Codec<ItemStack> original) {
        Codec<ItemStack> unknownStackCodec = SerializedItemStack.UNCOUNTED_CODEC
            .validate(SerializedItemStack::validateUnknown)
            .xmap(x -> {
                ItemStack stack = new ItemStack(FlashFreeze.UNKNOWN_ITEM.get(), x.count());
                stack.applyComponentsAndValidate(x.components());
                stack.set(FlashFreezeDataComponents.ORIGINAL_ITEM_ID, x.id());
                return stack;
            }, SerializedItemStack::from);

        return new AlternativeCodec<>(unknownStackCodec, original);
    }

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void switchOutMapCodec(CallbackInfo ci) {
        MAP_CODEC = MapCodec.assumeMapUnsafe(CODEC);
    }
}
