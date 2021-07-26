package me.basiqueevangelist.flashfreeze.mixin;

import me.basiqueevangelist.flashfreeze.components.ComponentHolder;
import me.basiqueevangelist.flashfreeze.util.Platform;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockEntity.class)
public class BlockEntityMixin {
    @Unique private final ComponentHolder componentHolder = new ComponentHolder();

    @Inject(method = "fromTag", at = @At("RETURN"))
    private void readCCAComponents(BlockState state, NbtCompound tag, CallbackInfo ci) {
        if (Platform.isFabricModLoaded("cardinal-components-block")) return;

        componentHolder.fromTag(tag);
    }

    @Inject(method = "writeNbt", at = @At("RETURN"))
    private void writeCCAComponents(NbtCompound nbt, CallbackInfoReturnable<NbtCompound> cir) {
        if (Platform.isFabricModLoaded("cardinal-components-block")) return;

        componentHolder.toTag(nbt);
    }
}
