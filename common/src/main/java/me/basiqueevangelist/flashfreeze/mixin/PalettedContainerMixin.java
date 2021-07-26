package me.basiqueevangelist.flashfreeze.mixin;

import me.basiqueevangelist.flashfreeze.FlashFreeze;
import me.basiqueevangelist.flashfreeze.UnknownBlockState;
import me.basiqueevangelist.flashfreeze.access.PalettedContainerAccess;
import net.minecraft.world.chunk.PalettedContainer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Predicate;

@Mixin(value = PalettedContainer.class, priority = 900)
public abstract class PalettedContainerMixin implements PalettedContainerAccess {
    @Shadow protected abstract Object get(int index);

    @Shadow
    private static int toIndex(int x, int y, int z) {
        throw new RuntimeException(":thonk:");
    }

    @Inject(method = "setAndGetOldValue", at = @At("RETURN"), cancellable = true)
    private void transformStateIfNeeded(int index, Object value, CallbackInfoReturnable<Object> cir) {
        if (cir.getReturnValue() instanceof UnknownBlockState) {
            cir.setReturnValue(FlashFreeze.getForUnknown((UnknownBlockState) cir.getReturnValue()));
        }
    }

    @Inject(method = "get(III)Ljava/lang/Object;", at = @At("RETURN"), cancellable = true)
    private void transformStateIfNeeded(int x, int y, int z, CallbackInfoReturnable<Object> cir) {
        if (cir.getReturnValue() instanceof UnknownBlockState) {
            cir.setReturnValue(FlashFreeze.getForUnknown((UnknownBlockState) cir.getReturnValue()));
        }
    }

    @ModifyVariable(method = "count", at = @At("HEAD"), argsOnly = true)
    private PalettedContainer.CountConsumer<Object> swapOutConsumer(PalettedContainer.CountConsumer<Object> consumer) {
        return (obj, count) -> {
            if (obj instanceof UnknownBlockState)
                obj = FlashFreeze.getForUnknown((UnknownBlockState) obj);
            consumer.accept(obj, count);
        };
    }

    @ModifyVariable(method = "hasAny", at = @At("HEAD"), argsOnly = true)
    private Predicate<Object> swapOutPredicate(Predicate<Object> original) {
        return (obj) -> {
            if (obj instanceof UnknownBlockState)
                obj = FlashFreeze.getForUnknown((UnknownBlockState) obj);
            return original.test(obj);
        };
    }

    @Override
    public UnknownBlockState getUnknown(int x, int y, int z) {
        Object o = get(toIndex(x, y, z));
        if (o instanceof UnknownBlockState)
            return (UnknownBlockState) o;
        return null;
    }
}
