package me.basiqueevangelist.palettebypass.mixin;

import me.basiqueevangelist.palettebypass.PaletteBypass;
import me.basiqueevangelist.palettebypass.PalettedContainerAccess;
import me.basiqueevangelist.palettebypass.UnknownBlockState;
import net.minecraft.world.chunk.Palette;
import net.minecraft.world.chunk.PalettedContainer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PalettedContainer.class)
public abstract class PalettedContainerMixin implements PalettedContainerAccess {
    @Shadow protected abstract Object get(int index);

    @Shadow
    private static int toIndex(int x, int y, int z) {
        throw new RuntimeException(":thonk:");
    }

    @Inject(method = "setAndGetOldValue", at = @At("RETURN"), cancellable = true)
    private void transformStateIfNeeded(int index, Object value, CallbackInfoReturnable<Object> cir) {
        if (cir.getReturnValue() instanceof UnknownBlockState) {
            cir.setReturnValue(PaletteBypass.getForUnknown((UnknownBlockState) cir.getReturnValue()));
        }
    }

    @Inject(method = "get(III)Ljava/lang/Object;", at = @At("RETURN"), cancellable = true)
    private void transformStateIfNeeded(int x, int y, int z, CallbackInfoReturnable<Object> cir) {
        if (cir.getReturnValue() instanceof UnknownBlockState) {
            cir.setReturnValue(PaletteBypass.getForUnknown((UnknownBlockState) cir.getReturnValue()));
        }
    }

    @Redirect(method = "method_21733", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/chunk/Palette;getByIndex(I)Ljava/lang/Object;"), remap = false)
    private Object transformStateIfNeeded(Palette<Object> palette, int index) {
        Object o = palette.getByIndex(index);
        if (o instanceof UnknownBlockState)
            return PaletteBypass.getForUnknown((UnknownBlockState) o);
        return o;
    }

    @Override
    public UnknownBlockState getUnknown(int x, int y, int z) {
        Object o = get(toIndex(x, y, z));
        if (o instanceof UnknownBlockState)
            return (UnknownBlockState) o;
        return null;
    }
}
