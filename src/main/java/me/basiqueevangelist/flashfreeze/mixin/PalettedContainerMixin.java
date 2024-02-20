package me.basiqueevangelist.flashfreeze.mixin;

import me.basiqueevangelist.flashfreeze.UnknownReplacer;
import me.basiqueevangelist.flashfreeze.access.PalettedContainerAccess;
import net.minecraft.world.chunk.PalettedContainer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Consumer;
import java.util.function.Predicate;

@Mixin(value = PalettedContainer.class, priority = 900)
public abstract class PalettedContainerMixin implements PalettedContainerAccess {
    @Shadow protected abstract Object get(int index);

    @Shadow @Final private PalettedContainer.PaletteProvider paletteProvider;

    private boolean flashfreeze$malding = false;

    @Inject(method = "swap(ILjava/lang/Object;)Ljava/lang/Object;", at = @At("RETURN"), cancellable = true)
    private void transformStateIfNeeded(int index, Object value, CallbackInfoReturnable<Object> cir) {
        if (cir.getReturnValue() instanceof UnknownReplacer replacer) {
            cir.setReturnValue(replacer.toReal());
        }
    }

    @Inject(method = "get(I)Ljava/lang/Object;", at = @At("RETURN"), cancellable = true)
    private void transformStateIfNeeded(int idx, CallbackInfoReturnable<Object> cir) {
        if (flashfreeze$malding) return;

        if (cir.getReturnValue() instanceof UnknownReplacer replacer) {
            cir.setReturnValue(replacer.toReal());
        }
    }

    @ModifyVariable(method = "count", at = @At("HEAD"), argsOnly = true)
    private PalettedContainer.Counter<Object> swapOutConsumer(PalettedContainer.Counter<Object> consumer) {
        return (obj, count) -> {
            if (obj instanceof UnknownReplacer replacer)
                obj = replacer.toReal();
            consumer.accept(obj, count);
        };
    }

    @ModifyVariable(method = "forEachValue", at = @At("HEAD"), argsOnly = true)
    private Consumer<Object> swapOutConsumer(Consumer<Object> consumer) {
        return (obj) -> {
            if (obj instanceof UnknownReplacer replacer)
                obj = replacer.toReal();
            consumer.accept(obj);
        };
    }

    @ModifyVariable(method = "hasAny", at = @At("HEAD"), argsOnly = true)
    private Predicate<Object> swapOutPredicate(Predicate<Object> original) {
        return (obj) -> {
            if (obj instanceof UnknownReplacer replacer)
                obj = replacer.toReal();
            return original.test(obj);
        };
    }

    @Override
    public UnknownReplacer getUnknown(int x, int y, int z) {
        flashfreeze$malding = true;
        Object o = get(paletteProvider.computeIndex(x, y, z));
        flashfreeze$malding = false;

        if (o instanceof UnknownReplacer replacer)
            return replacer;
        return null;
    }
}
