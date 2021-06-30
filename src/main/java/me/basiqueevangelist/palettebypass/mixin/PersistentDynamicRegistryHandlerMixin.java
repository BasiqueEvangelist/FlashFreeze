package me.basiqueevangelist.palettebypass.mixin;

import me.basiqueevangelist.palettebypass.FabricDynamicRegistryData;
import net.fabricmc.fabric.impl.registry.sync.PersistentDynamicRegistryHandler;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.MutableRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.nio.file.Path;

@Mixin(PersistentDynamicRegistryHandler.class)
public class PersistentDynamicRegistryHandlerMixin {
    @Inject(method = "writeCompoundTag", at = @At("HEAD"), remap = false)
    private static void stealRegistryData(NbtCompound compoundTag, Path path, CallbackInfo ci) {
        FabricDynamicRegistryData.setRegistryData(compoundTag);
    }

    @Redirect(method = "remapRegistry", at = @At(value = "INVOKE", target = "Lnet/minecraft/nbt/NbtCompound;getInt(Ljava/lang/String;)I"), slice = @Slice(from = @At(value = "INVOKE", target = "Lnet/minecraft/nbt/NbtCompound;contains(Ljava/lang/String;)Z")))
    private static int fixDumbFAPIBug(NbtCompound registryTag, String key, Identifier registryId, MutableRegistry<?> registry, NbtCompound existingTag) {
        return existingTag.getInt(key);
    }
}
