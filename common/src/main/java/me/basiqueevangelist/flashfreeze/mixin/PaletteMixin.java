package me.basiqueevangelist.flashfreeze.mixin;

import me.basiqueevangelist.flashfreeze.UnknownReplacer;
import net.minecraft.world.level.chunk.HashMapPalette;
import net.minecraft.world.level.chunk.LinearPalette;
import net.minecraft.world.level.chunk.SingleValuePalette;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(value = {SingleValuePalette.class, LinearPalette.class, HashMapPalette.class}, priority = 400)
public class PaletteMixin {
    @ModifyArg(method = "write", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/IdMap;getId(Ljava/lang/Object;)I"))
    private Object transformStateIfNecessary(Object state) {
        if (state instanceof UnknownReplacer replacer)
            return replacer.toReal();
        else
            return state;
    }

    @ModifyArg(method = "getSerializedSize", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/IdMap;getId(Ljava/lang/Object;)I"))
    private Object transformStateIfNecessaryPacketSize(Object state) {
        if (state instanceof UnknownReplacer replacer)
            return replacer.toReal();
        else
            return state;
    }
}
