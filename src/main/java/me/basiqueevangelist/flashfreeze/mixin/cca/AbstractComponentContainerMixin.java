package me.basiqueevangelist.flashfreeze.mixin.cca;

import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import dev.onyxstudios.cca.internal.base.AbstractComponentContainer;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@SuppressWarnings("UnstableApiUsage")
@Pseudo
@Mixin(AbstractComponentContainer.class)
public class AbstractComponentContainerMixin {
    @Unique private final Map<String, NbtCompound> unknownComponents = new HashMap<>();

    @Inject(method = "fromTag", at = @At("HEAD"), remap = false)
    private void readUnknownComponentsFromList(NbtCompound tag, CallbackInfo ci) {
        unknownComponents.clear();

        if (tag.contains("cardinal_components", NbtElement.LIST_TYPE)) {
            NbtList components = tag.getList("cardinal_components", NbtElement.COMPOUND_TYPE);
            for (int i = 0; i < components.size(); i++) {
                NbtCompound componentTag = components.getCompound(i);
                String componentId = componentTag.getString("componentId");
                Identifier parsedId = Identifier.tryParse(componentId);
                if (parsedId == null || ComponentRegistry.get(parsedId) == null) {
                    NbtCompound newComponentTag = componentTag.copy();
                    newComponentTag.remove("componentId");
                    unknownComponents.put(componentId, newComponentTag);
                }
            }
        }
    }

    @Redirect(method = "fromTag", at = @At(value = "INVOKE", target = "Ldev/onyxstudios/cca/internal/base/ComponentsInternals;logDeserializationWarnings(Ljava/util/Collection;)V"))
    private void shhhhhhhhhh(Collection<String> cause) {

    }

    @Inject(method = "fromTag", at = @At(value = "INVOKE", target = "Ldev/onyxstudios/cca/internal/base/ComponentsInternals;logDeserializationWarnings(Ljava/util/Collection;)V"), locals = LocalCapture.CAPTURE_FAILHARD, remap = false)
    private void readUnknownComponents(NbtCompound tag, CallbackInfo ci, NbtCompound componentMap) {
        for (var key : componentMap.getKeys()) {
            unknownComponents.put(key, componentMap.getCompound(key));
        }
    }

    @Inject(method = "toTag", at = @At(value = "RETURN"), remap = false)
    private void addUnknownComponents(NbtCompound tag, CallbackInfoReturnable<NbtCompound> cir) {
        if (unknownComponents.isEmpty()) return;

        if (!tag.contains("cardinal_components", NbtElement.COMPOUND_TYPE)) tag.put("cardinal_components", new NbtCompound());

        NbtCompound componentsTag = tag.getCompound("cardinal_components");
        for (Map.Entry<String, NbtCompound> entry : unknownComponents.entrySet()) {
            componentsTag.put(entry.getKey(), entry.getValue());
        }
    }
}
