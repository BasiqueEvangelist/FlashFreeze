package me.basiqueevangelist.flashfreeze.mixin.fabric;

import me.basiqueevangelist.flashfreeze.fabric.capabilities.CapabilityHolder;
import net.minecraft.block.BlockState;
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

    @Inject(method = "fromTag", at = @At("RETURN"))
    private void readCapabilities(BlockState state, NbtCompound tag, CallbackInfo ci) {
        capabilityHolder.fromTag(tag);
    }

    @Inject(method = "writeNbt", at = @At("RETURN"))
    private void writeCapabilities(NbtCompound nbt, CallbackInfoReturnable<NbtCompound> cir) {
        capabilityHolder.toTag(nbt);
    }
}
