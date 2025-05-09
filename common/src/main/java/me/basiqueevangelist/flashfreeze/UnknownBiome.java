package me.basiqueevangelist.flashfreeze;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;

public record UnknownBiome(ResourceLocation id) implements UnknownReplacer {
    @Override
    public Holder<Biome> toReal() {
        return FlashFreeze.SERVER.get().registryAccess().getOrThrow(Biomes.THE_VOID);
    }
}
