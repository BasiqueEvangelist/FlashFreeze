package me.basiqueevangelist.flashfreeze.mixin;

import me.basiqueevangelist.flashfreeze.FlashFreeze;
import me.basiqueevangelist.flashfreeze.UnknownBlockState;
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
            return FlashFreeze.getForUnknown((UnknownBlockState) state);
        else
            return state;
    }
}
