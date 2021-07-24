package me.basiqueevangelist.flashfreeze.mixin.forge;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.util.Identifier;
import net.minecraftforge.common.capabilities.CapabilityDispatcher;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.HashMap;
import java.util.Map;

@Mixin(value = CapabilityDispatcher.class, remap = false)
public class CapabilityDispatcherMixin {
    @Shadow private String[] names;

    @Unique private final Map<Identifier, NbtElement> unknownCapabilities = new HashMap<>();

    @Inject(method = "deserializeNBT", at = @At(value = "INVOKE", target = "Lnet/minecraftforge/common/util/INBTSerializable;deserializeNBT(Lnet/minecraft/nbt/NbtElement;)V"), locals = LocalCapture.CAPTURE_FAILHARD)
    private void removeReadCapabilityTag(NbtCompound tag, CallbackInfo ci, int x) {
        tag.remove(names[x]);
    }

    @Inject(method = "deserializeNBT", at = @At("RETURN"))
    private void readUnreadCapabilityTags(NbtCompound tag, CallbackInfo ci) {
        for (String key : tag.getKeys()) {
            unknownCapabilities.put(new Identifier(key), tag.get(key));
        }
    }

    @Inject(method = "serializeNBT", at = @At("RETURN"))
    private void writeUnknownCapabilities(CallbackInfoReturnable<NbtCompound> cir) {
        NbtCompound tag = cir.getReturnValue();
        for (Map.Entry<Identifier, NbtElement> entry : unknownCapabilities.entrySet()) {
            tag.put(entry.getKey().toString(), entry.getValue());
        }
    }
}
