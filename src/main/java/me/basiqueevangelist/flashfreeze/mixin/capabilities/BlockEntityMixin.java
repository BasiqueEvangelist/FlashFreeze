package me.basiqueevangelist.flashfreeze.mixin.capabilities;

import me.basiqueevangelist.flashfreeze.capabilities.CapabilityHolder;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockEntity.class)
public class BlockEntityMixin {
    @Unique private final CapabilityHolder capabilityHolder = new CapabilityHolder();

    @Inject(method = "loadAdditional", at = @At("RETURN"))
    private void readCapabilities(CompoundTag nbt, HolderLookup.Provider registryLookup, CallbackInfo ci) {
        capabilityHolder.fromTag(nbt);
    }

    @Inject(method = "saveAdditional", at = @At("RETURN"))
    private void writeCapabilities(CompoundTag nbt, HolderLookup.Provider registryLookup, CallbackInfo ci) {
        capabilityHolder.toTag(nbt);
    }
}
