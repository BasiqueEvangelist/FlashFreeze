package me.basiqueevangelist.flashfreeze.neoforge.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.attachment.AttachmentHolder;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.HashMap;
import java.util.Map;

@Mixin(AttachmentHolder.class)
public class AttachmentHolderMixin {
    @Unique @Nullable Map<ResourceLocation, Tag> flashfreeze$unknownAttachments = null;

    @Redirect(method = "deserializeAttachments", at = @At(value = "INVOKE", target = "Lorg/slf4j/Logger;error(Ljava/lang/String;Ljava/lang/Object;)V", ordinal = 1))
    private void eatErrorAndSave(Logger instance, String s, Object o, HolderLookup.Provider provider, CompoundTag tag, @Local ResourceLocation keyLocation, @Local String key) {
        if (flashfreeze$unknownAttachments == null) flashfreeze$unknownAttachments = new HashMap<>();

        flashfreeze$unknownAttachments.put(keyLocation, tag.get(key));
    }

    @ModifyReturnValue(method = "serializeAttachments", at = @At("RETURN"))
    private CompoundTag addUnknown(CompoundTag original) {
        if (flashfreeze$unknownAttachments == null) return original;

        if (original == null) original = new CompoundTag();

        for (var entry : flashfreeze$unknownAttachments.entrySet()) {
            original.put(entry.getKey().toString(), entry.getValue());
        }

        return original;
    }
}
