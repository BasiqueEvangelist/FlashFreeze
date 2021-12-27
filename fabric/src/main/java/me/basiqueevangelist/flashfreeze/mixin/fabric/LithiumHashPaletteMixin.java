package me.basiqueevangelist.flashfreeze.mixin.fabric;

import me.basiqueevangelist.flashfreeze.FlashFreeze;
import me.basiqueevangelist.flashfreeze.UnknownBlockState;
import me.basiqueevangelist.flashfreeze.UnknownReplacer;
import me.jellysquid.mods.lithium.common.world.chunk.LithiumHashPalette;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(LithiumHashPalette.class)
public class LithiumHashPaletteMixin {
    @ModifyArg(method = "writePacket", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/collection/IndexedIterable;getRawId(Ljava/lang/Object;)I"))
    private Object transformStateIfNecessary(Object state) {
        if (state instanceof UnknownReplacer replacer)
            return replacer.toReal();
        else
            return state;
    }

    @ModifyArg(method = "getPacketSize", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/collection/IndexedIterable;getRawId(Ljava/lang/Object;)I"))
    private Object transformStateIfNecessaryPacketSize(Object state) {
        if (state instanceof UnknownReplacer replacer)
            return replacer.toReal();
        else
            return state;
    }
}