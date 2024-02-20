package me.basiqueevangelist.flashfreeze.testmod.mixin;

import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.util.VanillaBiomeParameters;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(VanillaBiomeParameters.class)
public class VanillaBiomeParametersMixin {
    @Shadow @Final private RegistryKey<Biome>[][] uncommonBiomes;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void addTestBiome(CallbackInfo ci) {
        uncommonBiomes[0][1] = RegistryKey.of(RegistryKeys.BIOME, new Identifier("flashfreeze", "thonk"));
    }
}
