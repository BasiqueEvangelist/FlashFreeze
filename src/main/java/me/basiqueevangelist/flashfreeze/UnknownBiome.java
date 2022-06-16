package me.basiqueevangelist.flashfreeze;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.BiomeKeys;

public record UnknownBiome(Identifier id) implements UnknownReplacer {
    @Override
    public Object toReal() {
        return FlashFreeze.SERVER.get().getRegistryManager().get(Registry.BIOME_KEY).getEntry(BiomeKeys.THE_VOID).get();
    }
}
