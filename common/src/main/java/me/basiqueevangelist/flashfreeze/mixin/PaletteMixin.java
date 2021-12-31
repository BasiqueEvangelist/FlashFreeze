package me.basiqueevangelist.flashfreeze.mixin;

import me.basiqueevangelist.flashfreeze.UnknownReplacer;
import net.minecraft.world.chunk.ArrayPalette;
import net.minecraft.world.chunk.BiMapPalette;
import net.minecraft.world.chunk.SingularPalette;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(value = {SingularPalette.class, ArrayPalette.class, BiMapPalette.class}, priority = 400)
public class PaletteMixin {
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
