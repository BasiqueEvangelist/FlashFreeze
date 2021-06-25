package me.basiqueevangelist.palettebypass.mixin;

import me.basiqueevangelist.palettebypass.PaletteBypass;
import me.basiqueevangelist.palettebypass.UnknownBlockState;
import net.minecraft.block.BlockState;
import net.minecraft.world.chunk.ArrayPalette;
import net.minecraft.world.chunk.BiMapPalette;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.function.Predicate;

@Mixin({ArrayPalette.class, BiMapPalette.class})
public class PaletteMixin {
    @Redirect(method = "accepts", at = @At(value = "INVOKE", target = "Ljava/util/function/Predicate;test(Ljava/lang/Object;)Z"))
    private boolean transformStateIfNecessary(Predicate<BlockState> predicate, Object state) {
        if (state instanceof UnknownBlockState)
            return predicate.test(PaletteBypass.getForUnknown((UnknownBlockState) state));
        else
            return predicate.test((BlockState) state);
    }

    @ModifyArg(method = "toPacket", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/collection/IdList;getRawId(Ljava/lang/Object;)I"))
    private Object transformStateIfNecessary(Object state) {
        if (state instanceof UnknownBlockState)
            return PaletteBypass.getForUnknown((UnknownBlockState) state);
        else
            return state;
    }
}
