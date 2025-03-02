package me.basiqueevangelist.flashfreeze.mixin;

import me.basiqueevangelist.flashfreeze.components.ComponentHolder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public class EntityMixin {
    @Unique private final ComponentHolder componentHolder = new ComponentHolder();

    @Inject(method = "load", at = @At("RETURN"))
    private void readCCAComponents(CompoundTag nbt, CallbackInfo ci) {
        if (FabricLoader.getInstance().isModLoaded("cardinal-components-entity")) return;

        componentHolder.fromTag(nbt);
    }

    @Inject(method = "save", at = @At("RETURN"))
    private void writeCCAComponents(CompoundTag nbt, CallbackInfoReturnable<CompoundTag> cir) {
        if (FabricLoader.getInstance().isModLoaded("cardinal-components-entity")) return;

        componentHolder.toTag(nbt);
    }
}
