package me.basiqueevangelist.flashfreeze.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import me.basiqueevangelist.flashfreeze.components.ComponentHolder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockEntity.class)
public class BlockEntityMixin {
    @Unique private final ComponentHolder componentHolder = new ComponentHolder();

    @Inject(method = "loadAdditional", at = @At("RETURN"))
    private void readCCAComponents(CompoundTag nbt, HolderLookup.Provider registryLookup, CallbackInfo ci) {
        if (FabricLoader.getInstance().isModLoaded("cardinal-components-block")) return;

        componentHolder.fromTag(nbt);
    }

    @Inject(method = "saveAdditional", at = @At("RETURN"))
    private void writeCCAComponents(CompoundTag nbt, HolderLookup.Provider registryLookup, CallbackInfo ci) {
        if (FabricLoader.getInstance().isModLoaded("cardinal-components-block")) return;

        componentHolder.toTag(nbt);
    }

    @Inject(method = "loadStatic", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/Registry;getOptional(Lnet/minecraft/resources/ResourceLocation;)Ljava/util/Optional;"), cancellable = true)
    private static void shhhhhhh(BlockPos pos, BlockState state, CompoundTag nbt, HolderLookup.Provider registryLookup, CallbackInfoReturnable<BlockEntity> cir, @Local ResourceLocation identifier) {
        if (!BuiltInRegistries.BLOCK_ENTITY_TYPE.containsKey(identifier))
            cir.setReturnValue(null);
    }
}
