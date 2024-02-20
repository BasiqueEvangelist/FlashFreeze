package me.basiqueevangelist.flashfreeze.mixin;

import me.basiqueevangelist.flashfreeze.components.ComponentHolder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(BlockEntity.class)
public class BlockEntityMixin {
    @Unique private final ComponentHolder componentHolder = new ComponentHolder();

    @Inject(method = "readNbt", at = @At("RETURN"))
    private void readCCAComponents(NbtCompound nbt, CallbackInfo ci) {
        if (FabricLoader.getInstance().isModLoaded("cardinal-components-block")) return;

        componentHolder.fromTag(nbt);
    }

    @Inject(method = "writeNbt", at = @At("RETURN"))
    private void writeCCAComponents(NbtCompound nbt, CallbackInfo ci) {
        if (FabricLoader.getInstance().isModLoaded("cardinal-components-block")) return;

        componentHolder.toTag(nbt);
    }

    @Inject(method = "createFromNbt", at = @At(value = "INVOKE", target = "Lnet/minecraft/registry/Registry;getOrEmpty(Lnet/minecraft/util/Identifier;)Ljava/util/Optional;"), locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
    private static void shhhhhhh(BlockPos pos, BlockState state, NbtCompound nbt, CallbackInfoReturnable<BlockEntity> cir, String string, Identifier identifier) {
        if (!Registries.BLOCK_ENTITY_TYPE.containsId(identifier))
            cir.setReturnValue(null);
    }
}
