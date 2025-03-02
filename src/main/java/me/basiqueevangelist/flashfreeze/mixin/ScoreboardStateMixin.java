package me.basiqueevangelist.flashfreeze.mixin;

import me.basiqueevangelist.flashfreeze.components.ComponentHolder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.scores.ScoreboardSaveData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ScoreboardSaveData.class)
public class ScoreboardStateMixin {
    @Unique private final ComponentHolder componentHolder = new ComponentHolder();

    @Inject(method = "load", at = @At("RETURN"))
    private void readCCAComponents(CompoundTag nbt, HolderLookup.Provider registries, CallbackInfoReturnable<ScoreboardSaveData> cir) {
        if (FabricLoader.getInstance().isModLoaded("cardinal-components-scoreboard")) return;

        componentHolder.fromTag(nbt);
    }

    @Inject(method = "save", at = @At("RETURN"))
    private void writeCCAComponents(CompoundTag nbt, HolderLookup.Provider registryLookup, CallbackInfoReturnable<CompoundTag> cir) {
        if (FabricLoader.getInstance().isModLoaded("cardinal-components-scoreboard")) return;

        componentHolder.toTag(nbt);
    }
}
