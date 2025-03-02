package me.basiqueevangelist.flashfreeze.mixin.capabilities;

import me.basiqueevangelist.flashfreeze.capabilities.CapabilityHolder;
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
    @Unique private final CapabilityHolder capabilityHolder = new CapabilityHolder();

    @Inject(method = "load", at = @At("RETURN"))
    private void readCapabilities(CompoundTag nbt, CallbackInfo ci) {
        capabilityHolder.fromTag(nbt);
    }

    @Inject(method = "save", at = @At("RETURN"))
    private void writeCapabilities(CompoundTag nbt, CallbackInfoReturnable<CompoundTag> cir) {
        capabilityHolder.toTag(nbt);
    }
}
