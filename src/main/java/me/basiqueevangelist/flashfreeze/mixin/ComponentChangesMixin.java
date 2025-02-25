package me.basiqueevangelist.flashfreeze.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import com.mojang.datafixers.util.Unit;
import it.unimi.dsi.fastutil.objects.Reference2ObjectMap;
import me.basiqueevangelist.flashfreeze.FailedComponentWrapper;
import me.basiqueevangelist.flashfreeze.access.ComponentChangesTypeAccess;
import me.basiqueevangelist.flashfreeze.item.FlashFreezeDataComponents;
import me.basiqueevangelist.flashfreeze.item.UnknownDataComponents;
import net.minecraft.component.ComponentChanges;
import net.minecraft.component.ComponentType;
import net.minecraft.nbt.NbtElement;
import net.minecraft.registry.Registries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Mixin(ComponentChanges.class)
public class ComponentChangesMixin {
    @Inject(method = "method_57843", at = @At("HEAD"))
    private static void hashmapify(Map<ComponentChanges.Type, ?> changes, CallbackInfoReturnable<ComponentChanges> cir, @Local(argsOnly = true) LocalRef<Map<ComponentChanges.Type, ?>> changesRef) {
        changesRef.set(new HashMap<>(changesRef.get()));
    }

    @Inject(method = "method_57843", at = @At(value = "INVOKE", target = "Ljava/util/Map;entrySet()Ljava/util/Set;"))
    private static void decode(Map<ComponentChanges.Type, ?> changes, CallbackInfoReturnable<ComponentChanges> cir, @Local Reference2ObjectMap<ComponentType<?>, Optional<?>> out) {
        for (var entry : changes.entrySet()) {
            var componentTypeId = ((ComponentChangesTypeAccess)(Object) entry.getKey()).flashfreeze$getComponentTypeId();
            NbtElement value;

            if (componentTypeId == null) {
                componentTypeId = Registries.DATA_COMPONENT_TYPE.getId(entry.getKey().type());

                if (entry.getValue() instanceof FailedComponentWrapper fcw) {
                    value = fcw.original();
                } else {
                    continue;
                }
            } else {
                value = (NbtElement) entry.getValue();
            }

            UnknownDataComponents unknownComponents = (UnknownDataComponents) out.computeIfAbsent(FlashFreezeDataComponents.UNKNOWN_DATA_COMPONENTS, unused -> Optional.of(new UnknownDataComponents(new HashMap<>()))).orElseThrow();

            if (entry.getKey().removed())
                unknownComponents.components().put(componentTypeId, Optional.empty());
            else
                unknownComponents.components().put(componentTypeId, Optional.ofNullable(value));
        }

        changes.entrySet().removeIf(entry ->
            ((ComponentChangesTypeAccess)(Object) entry.getKey()).flashfreeze$getComponentTypeId() != null
            || entry.getValue() instanceof FailedComponentWrapper);
    }

    @Inject(method = "method_57844", at = @At(value = "INVOKE", target = "Lit/unimi/dsi/fastutil/objects/Reference2ObjectMaps;fastIterable(Lit/unimi/dsi/fastutil/objects/Reference2ObjectMap;)Lit/unimi/dsi/fastutil/objects/ObjectIterable;"))
    private static void encode(ComponentChanges changes, CallbackInfoReturnable<Map<ComponentChanges.Type, ?>> cir, @Local Reference2ObjectMap<ComponentChanges.Type, Object> out) {
        var unknownData = changes.get(FlashFreezeDataComponents.UNKNOWN_DATA_COMPONENTS);

        if (unknownData == null || unknownData.isEmpty()) return;

        for (var component : unknownData.get().components().entrySet()) {
            ComponentChanges.Type type = new ComponentChanges.Type(null, component.getValue().isEmpty());
            ((ComponentChangesTypeAccess)(Object) type).flashfreeze$setComponentTypeId(component.getKey());

            out.put(type, component.getValue().map(x -> (Object) x).orElse(Unit.INSTANCE));
        }
    }
}
