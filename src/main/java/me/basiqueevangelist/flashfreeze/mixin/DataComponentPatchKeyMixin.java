package me.basiqueevangelist.flashfreeze.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import me.basiqueevangelist.flashfreeze.FailedComponentWrapper;
import me.basiqueevangelist.flashfreeze.access.DataComponentPatchKeyAccess;
import me.basiqueevangelist.flashfreeze.util.FlashFreezeCodecs;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.NbtOps;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DataComponentPatch.PatchKey.class)
public class DataComponentPatchKeyMixin implements DataComponentPatchKeyAccess {
    @Shadow @Final private boolean removed;
    @Unique
    private ResourceLocation componentTypeId;

    @Override
    public ResourceLocation flashfreeze$getComponentTypeId() {
        return componentTypeId;
    }

    @Override
    public void flashfreeze$setComponentTypeId(ResourceLocation componentTypeId) {
        this.componentTypeId = componentTypeId;
    }

    @Inject(method = "method_57858", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/Registry;get(Lnet/minecraft/resources/ResourceLocation;)Ljava/lang/Object;"), cancellable = true)
    private static void decode(String id, CallbackInfoReturnable<DataResult<DataComponentPatch.PatchKey>> cir, @Local ResourceLocation componentTypeId, @Local boolean isRemoved) {
        if (!BuiltInRegistries.DATA_COMPONENT_TYPE.containsKey(componentTypeId)) {
            var type = new DataComponentPatch.PatchKey(null, isRemoved);
            ((DataComponentPatchKeyMixin)(Object) type).componentTypeId = componentTypeId;
            cir.setReturnValue(DataResult.success(type));
        }
    }

    @WrapOperation(method = "method_57859", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/Registry;getKey(Ljava/lang/Object;)Lnet/minecraft/resources/ResourceLocation;"))
    private static @Nullable ResourceLocation encode(Registry<DataComponentType<?>> instance, /*ComponentType<?>*/ Object componentType, Operation<ResourceLocation> original, DataComponentPatch.PatchKey changesType) {
        var componentTypeId = ((DataComponentPatchKeyAccess)(Object) changesType).flashfreeze$getComponentTypeId();
        if (componentTypeId != null) {
            return componentTypeId;
        } else {
            return original.call(instance, componentType);
        }
    }

    @Inject(method = "valueCodec", at = @At("HEAD"), cancellable = true)
    private void resetValueCodec(CallbackInfoReturnable<Codec<?>> cir) {
        if (componentTypeId != null)
            cir.setReturnValue(this.removed ? Codec.EMPTY.codec() : FlashFreezeCodecs.NBT_ELEMENT);
    }

    @SuppressWarnings("unchecked")
    @ModifyReturnValue(method = "valueCodec", at = @At("RETURN"))
    private Codec<?> addProtection(Codec<?> original) {
        return new Codec<Object>() {
            @Override
            public <T> DataResult<Pair<Object, T>> decode(DynamicOps<T> ops, T input) {
                var result = original.decode(ops, input);

                if (result.isError()) {
                    return DataResult.success(Pair.of(new FailedComponentWrapper(ops.convertTo(NbtOps.INSTANCE, input)), ops.empty()));
                } else {
                    return (DataResult<Pair<Object, T>>)(Object) result;
                }
            }

            @Override
            public <T> DataResult<T> encode(Object input, DynamicOps<T> ops, T prefix) {
                return ((Codec<Object> )original).encode(input, ops, prefix);
            }
        };
    }
}
