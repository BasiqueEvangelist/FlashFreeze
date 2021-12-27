package me.basiqueevangelist.flashfreeze.mixin.fabric.capabilities;

import me.basiqueevangelist.flashfreeze.fabric.capabilities.CapabilityHolder;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockEntity.class)
public class BlockEntityMixin {
    @Unique private final CapabilityHolder capabilityHolder = new CapabilityHolder();

    @Inject(method = "readNbt", at = @At("RETURN"))
    private void readCapabilities(NbtCompound nbt, CallbackInfo ci) {
        capabilityHolder.fromTag(nbt);
    }

    @Inject(method = "writeNbt", at = @At("RETURN"))
    private void writeCapabilities(NbtCompound nbt, CallbackInfo ci) {
        capabilityHolder.toTag(nbt);
    }
}
