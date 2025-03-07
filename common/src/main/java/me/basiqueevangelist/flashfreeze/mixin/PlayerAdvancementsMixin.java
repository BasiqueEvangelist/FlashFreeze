package me.basiqueevangelist.flashfreeze.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.PlayerAdvancements;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.HashMap;
import java.util.Map;

@Mixin(PlayerAdvancements.class)
public class PlayerAdvancementsMixin {
    @Unique private @Nullable Map<ResourceLocation, AdvancementProgress> flashfreeze$unknownAdvancements = null;

    @Redirect(method = {"lambda$applyFrom$0", "method_53639"}, at = @At(value = "INVOKE", target = "Lorg/slf4j/Logger;warn(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;)V"))
    private void eatErrorAndSave(Logger instance, String s, Object o1, Object o2,
                                 @Local(argsOnly = true) ResourceLocation advId,
                                 @Local(argsOnly = true) AdvancementProgress progress) {
        if (flashfreeze$unknownAdvancements == null) flashfreeze$unknownAdvancements = new HashMap<>();

        flashfreeze$unknownAdvancements.put(advId, progress);
    }

    @ModifyReturnValue(method = "asData", at = @At("RETURN"))
    private PlayerAdvancements.Data addUnknown(PlayerAdvancements.Data original) {
        if (flashfreeze$unknownAdvancements == null) return original;

        original.map().putAll(flashfreeze$unknownAdvancements);

        return original;
    }
}
