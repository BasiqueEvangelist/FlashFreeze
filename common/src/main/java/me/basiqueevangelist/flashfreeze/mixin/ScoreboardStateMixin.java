package me.basiqueevangelist.flashfreeze.mixin;

import me.basiqueevangelist.flashfreeze.components.ComponentHolder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.scoreboard.ScoreboardState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ScoreboardState.class)
public class ScoreboardStateMixin {
    @Unique private final ComponentHolder componentHolder = new ComponentHolder();

    @Inject(method = "fromTag", at = @At("RETURN"))
    private void readCCAComponents(NbtCompound tag, CallbackInfo ci) {
        if (FabricLoader.getInstance().isModLoaded("cardinal-components-scoreboard")) return;

        componentHolder.fromTag(tag);
    }

    @Inject(method = "writeNbt", at = @At("RETURN"))
    private void writeCCAComponents(NbtCompound nbt, CallbackInfoReturnable<NbtCompound> cir) {
        if (FabricLoader.getInstance().isModLoaded("cardinal-components-scoreboard")) return;

        componentHolder.toTag(nbt);
    }
}
