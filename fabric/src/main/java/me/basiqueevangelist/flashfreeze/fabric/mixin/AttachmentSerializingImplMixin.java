package me.basiqueevangelist.flashfreeze.fabric.mixin;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.sugar.Local;
import me.basiqueevangelist.flashfreeze.fabric.MixinHooks;
import net.fabricmc.fabric.impl.attachment.AttachmentSerializingImpl;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@SuppressWarnings("UnstableApiUsage")
@Mixin(AttachmentSerializingImpl.class)
public class AttachmentSerializingImplMixin {
    // TODO!!!!

//    @Redirect(method = "deserializeAttachmentData", at = @At(value = "INVOKE", target = "Lorg/slf4j/Logger;warn(Ljava/lang/String;Ljava/lang/Object;)V"))
//    private static void eatLogAndRemember(Logger instance, String s, Object o, @Local String key) {
//        MixinHooks.unknownAttachmentIds.get().add(key);
//    }
}
