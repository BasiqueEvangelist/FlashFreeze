package me.basiqueevangelist.flashfreeze.mixin.forge.capabilities;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.util.Identifier;
import net.minecraftforge.common.capabilities.CapabilityDispatcher;
import net.minecraftforge.common.capabilities.CapabilityProvider;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.HashMap;
import java.util.Map;

@Mixin(CapabilityProvider.class)
public abstract class CapabilityProviderMixin {
    @Shadow private boolean initialized;

    @Shadow private boolean isLazy;

    @Shadow protected abstract @Nullable CapabilityDispatcher getCapabilities();

    @Unique private Map<Identifier, NbtElement> unknownCaps = null;

    @Inject(method = "serializeCaps", at = @At("RETURN"), cancellable = true)
    private void serializeUnknownCaps(CallbackInfoReturnable<NbtCompound> cir) {
        if (!this.isLazy || this.initialized && getCapabilities() == null) {
            if (unknownCaps != null) {
                NbtCompound tag = new NbtCompound();
                for (Map.Entry<Identifier, NbtElement> entry : unknownCaps.entrySet()) {
                    tag.put(entry.getKey().toString(), entry.getValue());
                }
                cir.setReturnValue(tag);
            }
        }
    }

    @Inject(method = "deserializeCaps", at = @At("RETURN"))
    private void deserializeUnknownCaps(NbtCompound tag, CallbackInfo ci) {
        if (!this.isLazy || this.initialized && getCapabilities() == null) {
            if (tag.getSize() > 0) {
                if (unknownCaps == null)
                    unknownCaps = new HashMap<>();

                for (String key : tag.getKeys()) {
                    unknownCaps.put(new Identifier(key), tag.get(key));
                }
            }
        }
    }
}
