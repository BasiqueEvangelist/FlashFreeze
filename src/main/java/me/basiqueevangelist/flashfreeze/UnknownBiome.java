package me.basiqueevangelist.flashfreeze;

import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;

public record UnknownBiome(Identifier id) implements UnknownReplacer {
    @Override
    public RegistryEntry<Biome> toReal() {
        return FlashFreeze.SERVER.get().getRegistryManager().get(RegistryKeys.BIOME).getEntry(BiomeKeys.THE_VOID).get();
    }
}
