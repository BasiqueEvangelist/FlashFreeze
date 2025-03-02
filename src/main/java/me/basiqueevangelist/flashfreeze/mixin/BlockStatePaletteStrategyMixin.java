package me.basiqueevangelist.flashfreeze.mixin;

import net.minecraft.core.IdMap;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.PalettedContainer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = "net/minecraft/world/level/chunk/PalettedContainer$Strategy$1")
public class BlockStatePaletteStrategyMixin {
    @Inject(method = "getConfiguration", at = @At("RETURN"), cancellable = true)
    private void maldAboutIt(IdMap<BlockState> idList, int bits, CallbackInfoReturnable<PalettedContainer.Configuration<BlockState>> cir) {
        if (cir.getReturnValue().factory() == PalettedContainer.Strategy.GLOBAL_PALETTE_FACTORY) {
            // Not a chance, buster
            cir.setReturnValue(new PalettedContainer.Configuration<>(PalettedContainer.Strategy.HASHMAP_PALETTE_FACTORY, bits));
        }
    }
}
