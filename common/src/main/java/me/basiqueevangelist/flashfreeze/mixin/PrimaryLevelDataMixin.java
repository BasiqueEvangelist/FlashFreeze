package me.basiqueevangelist.flashfreeze.mixin;

import com.mojang.serialization.Dynamic;
import com.mojang.serialization.Lifecycle;
import me.basiqueevangelist.flashfreeze.components.ComponentHolder;
import me.basiqueevangelist.flashfreeze.util.FlashFreezePlatform;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.LevelSettings;
import net.minecraft.world.level.levelgen.WorldOptions;
import net.minecraft.world.level.storage.PrimaryLevelData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PrimaryLevelData.class)
public class PrimaryLevelDataMixin {
    @Unique private final ComponentHolder componentHolder = new ComponentHolder();

    @Inject(method = "parse", at = @At("RETURN"))
    private static void readCCAComponents(Dynamic<?> dynamic, LevelSettings info, PrimaryLevelData.SpecialWorldProperty specialProperty, WorldOptions generatorOptions, Lifecycle lifecycle, CallbackInfoReturnable<PrimaryLevelData> cir) {
        if (FlashFreezePlatform.I.isModLoaded("cardinal-components-level")) return;

        ((PrimaryLevelDataMixin)(Object) cir.getReturnValue()).componentHolder.fromTag((CompoundTag) dynamic.getValue());
    }

    @Inject(method = "setTagData", at = @At("RETURN"))
    private void writeCCAComponents(RegistryAccess registryManager, CompoundTag levelTag, CompoundTag playerTag, CallbackInfo ci) {
        if (FlashFreezePlatform.I.isModLoaded("cardinal-components-level")) return;

        componentHolder.toTag(levelTag);
    }
}
