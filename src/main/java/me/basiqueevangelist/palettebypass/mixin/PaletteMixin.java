package me.basiqueevangelist.palettebypass.mixin;

import me.basiqueevangelist.palettebypass.PaletteBypass;
import me.basiqueevangelist.palettebypass.UnknownBlockState;
import me.jellysquid.mods.lithium.common.world.chunk.LithiumHashPalette;
import net.minecraft.world.chunk.ArrayPalette;
import net.minecraft.world.chunk.BiMapPalette;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin({ArrayPalette.class, BiMapPalette.class, LithiumHashPalette.class})
public class PaletteMixin {
    @ModifyArg(method = "toPacket", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/collection/IdList;getRawId(Ljava/lang/Object;)I"))
    private Object transformStateIfNecessary(Object state) {
        if (state instanceof UnknownBlockState)
            return PaletteBypass.getForUnknown((UnknownBlockState) state);
        else
            return state;
    }
}
