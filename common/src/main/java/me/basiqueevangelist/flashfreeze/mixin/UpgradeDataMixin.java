package me.basiqueevangelist.flashfreeze.mixin;

import me.basiqueevangelist.flashfreeze.UnknownReplacer;
import net.minecraft.world.chunk.PalettedContainer;
import net.minecraft.world.chunk.UpgradeData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(UpgradeData.class)
public class UpgradeDataMixin {
    @Redirect(method = "upgradeCenter", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/chunk/PalettedContainer;get(I)Ljava/lang/Object;"))
    private Object transformUnknownIfNeeded(PalettedContainer<Object> palettedContainer, int index) {
        Object o = ((PalettedContainerAccessor) palettedContainer).callGet(index);
        if (o instanceof UnknownReplacer replacer)
            return replacer.toReal();
        return o;
    }
}
