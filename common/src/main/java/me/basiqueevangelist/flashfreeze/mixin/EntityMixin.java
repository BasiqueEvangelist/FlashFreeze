package me.basiqueevangelist.flashfreeze.mixin;

import me.basiqueevangelist.flashfreeze.components.ComponentHolder;
import me.basiqueevangelist.flashfreeze.util.FFPlatform;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public class EntityMixin {
    @Unique private final ComponentHolder componentHolder = new ComponentHolder();

    @Inject(method = "readNbt", at = @At("RETURN"))
    private void readCCAComponents(NbtCompound nbt, CallbackInfo ci) {
        if (FFPlatform.isFabricModLoaded("cardinal-components-entity")) return;

        componentHolder.fromTag(nbt);
    }

    @Inject(method = "writeNbt", at = @At("RETURN"))
    private void writeCCAComponents(NbtCompound nbt, CallbackInfoReturnable<NbtCompound> cir) {
        if (FFPlatform.isFabricModLoaded("cardinal-components-entity")) return;

        componentHolder.toTag(nbt);
    }
}
