package me.basiqueevangelist.flashfreeze.mixin.fabric;

import net.fabricmc.fabric.impl.registry.sync.PersistentDynamicRegistryHandler;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.MutableRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(PersistentDynamicRegistryHandler.class)
public class PersistentDynamicRegistryHandlerMixin {
    @Redirect(method = "remapRegistry", at = @At(value = "INVOKE", target = "Lnet/minecraft/nbt/NbtCompound;getInt(Ljava/lang/String;)I"), slice = @Slice(from = @At(value = "INVOKE", target = "Lnet/minecraft/nbt/NbtCompound;contains(Ljava/lang/String;)Z")))
    private static int fixDumbFAPIBug(NbtCompound registryTag, String key, Identifier registryId, MutableRegistry<?> registry, NbtCompound existingTag) {
        return existingTag.getInt(key);
    }
}
