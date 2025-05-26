package me.basiqueevangelist.flashfreeze.mixin;

import me.basiqueevangelist.flashfreeze.UnknownReplacer;
import net.minecraft.core.Holder;
import net.minecraft.core.MappedRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MappedRegistry.class)
public class MappedRegistryMixin<T> {
    @Inject(method = "createIntrusiveHolder", at = @At("HEAD"), cancellable = true)
    private void noIntrusiveIfUnknown(T value, CallbackInfoReturnable<Holder.Reference<T>> cir) {
        if (value instanceof UnknownReplacer)
            cir.setReturnValue(null);
    }
}
