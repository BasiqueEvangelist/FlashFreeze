package me.basiqueevangelist.flashfreeze.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.util.collection.IndexedIterable;
import net.minecraft.world.chunk.PalettedContainer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = "net/minecraft/world/chunk/PalettedContainer$PaletteProvider$1")
public class BlockStatePaletteProviderMixin {
    @Inject(method = "createDataProvider", at = @At("RETURN"), cancellable = true)
    private void maldAboutIt(IndexedIterable<BlockState> idList, int bits, CallbackInfoReturnable<PalettedContainer.DataProvider<BlockState>> cir) {
        if (cir.getReturnValue().factory() == PalettedContainer.PaletteProvider.ID_LIST) {
            // Not a chance, buster
            cir.setReturnValue(new PalettedContainer.DataProvider<>(PalettedContainer.PaletteProvider.BI_MAP, bits));
        }
    }
}
