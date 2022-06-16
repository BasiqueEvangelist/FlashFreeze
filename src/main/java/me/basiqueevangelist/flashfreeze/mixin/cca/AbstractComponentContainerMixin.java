package me.basiqueevangelist.flashfreeze.mixin.cca;

import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import dev.onyxstudios.cca.internal.base.AbstractComponentContainer;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@SuppressWarnings("UnstableApiUsage")
@Mixin(AbstractComponentContainer.class)
public class AbstractComponentContainerMixin {
    @Unique private final Map<Identifier, NbtCompound> unknownComponents = new HashMap<>();

    @Inject(method = "fromTag", at = @At("HEAD"), remap = false)
    private void readUnknownComponentsFromList(NbtCompound tag, CallbackInfo ci) {
        unknownComponents.clear();

        if (tag.contains("cardinal_components", NbtElement.LIST_TYPE)) {
            NbtList components = tag.getList("cardinal_components", NbtElement.COMPOUND_TYPE);
            for (int i = 0; i < components.size(); i++) {
                NbtCompound componentTag = components.getCompound(i);
                Identifier componentId = new Identifier(componentTag.getString("componentId"));
                if (ComponentRegistry.get(componentId) == null) {
                    NbtCompound newComponentTag = componentTag.copy();
                    newComponentTag.remove("componentId");
                    unknownComponents.put(componentId, newComponentTag);
                }
            }
        }
    }

    @Inject(method = "fromTag", at = @At(value = "INVOKE", target = "Lorg/apache/logging/log4j/Logger;warn(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;)V"), locals = LocalCapture.CAPTURE_FAILHARD, remap = false)
    private void readUnknownComponents(NbtCompound tag, CallbackInfo ci, NbtCompound componentMap, Iterator<?> someIterator, String missedKeyId) {
        unknownComponents.put(new Identifier(missedKeyId), componentMap.getCompound(missedKeyId));
    }

    @Inject(method = "toTag", at = @At(value = "RETURN"), remap = false)
    private void addUnknownComponents(NbtCompound tag, CallbackInfoReturnable<NbtCompound> cir) {
        if (unknownComponents.isEmpty()) return;

        if (!tag.contains("cardinal_components", NbtElement.COMPOUND_TYPE)) tag.put("cardinal_components", new NbtCompound());

        NbtCompound componentsTag = tag.getCompound("cardinal_components");
        for (Map.Entry<Identifier, NbtCompound> entry : unknownComponents.entrySet()) {
            componentsTag.put(entry.getKey().toString(), entry.getValue());
        }
    }
}
