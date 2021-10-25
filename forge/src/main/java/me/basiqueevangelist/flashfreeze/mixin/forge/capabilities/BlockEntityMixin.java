package me.basiqueevangelist.flashfreeze.mixin.forge.capabilities;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraftforge.common.capabilities.CapabilityDispatcher;
import net.minecraftforge.common.capabilities.CapabilityProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Collections;

@Mixin(BlockEntity.class)
public abstract class BlockEntityMixin extends CapabilityProvider<BlockEntityMixin> {
    @Unique
    private static final CapabilityDispatcher SOME_DISPATCHER = new CapabilityDispatcher(Collections.emptyMap(), Collections.emptyList());

    protected BlockEntityMixin(Class<BlockEntityMixin> baseClass) {
        super(baseClass);
    }

    @Redirect(method = "writeIdentifyingData", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/entity/BlockEntity;getCapabilities()Lnet/minecraftforge/common/capabilities/CapabilityDispatcher;"))
    private CapabilityDispatcher disableCursedCapabilityWriting(BlockEntity be) {
        return null;
    }

    @Inject(method = "writeIdentifyingData", at = @At("RETURN"))
    private void writeCapabilities(NbtCompound nbt, CallbackInfoReturnable<NbtCompound> cir) {
        NbtCompound capabilityTag = serializeCaps();

        if (capabilityTag != null) {
            nbt.put("ForgeCaps", capabilityTag);
        }
    }

    @Redirect(method = "readNbt", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/entity/BlockEntity;getCapabilities()Lnet/minecraftforge/common/capabilities/CapabilityDispatcher;"))
    private CapabilityDispatcher disableCursedCapabilityReading(BlockEntity be) {
        return SOME_DISPATCHER;
    }
}
