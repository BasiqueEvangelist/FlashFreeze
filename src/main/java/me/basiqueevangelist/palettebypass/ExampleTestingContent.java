package me.basiqueevangelist.palettebypass;

import net.fabricmc.fabric.api.biome.v1.OverworldBiomes;
import net.fabricmc.fabric.api.biome.v1.OverworldClimate;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeEffects;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.gen.surfacebuilder.ConfiguredSurfaceBuilders;

public final class ExampleTestingContent {
    private ExampleTestingContent() {

    }

    @SuppressWarnings("deprecation")
    public static void registerBiome() {
        Biome b = new Biome.Builder()
            .precipitation(Biome.Precipitation.NONE)
            .category(Biome.Category.NONE)
            .depth(0.1F)
            .scale(0.2F)
            .temperature(2.0F)
            .downfall(0)
            .effects(new BiomeEffects.Builder()
                .waterColor(4159204)
                .waterFogColor(329011)
                .fogColor(0xffbbff)
                .skyColor(0xaaccdd)
                .build())
            .spawnSettings(new SpawnSettings.Builder().build())
            .generationSettings(new GenerationSettings.Builder()
                .surfaceBuilder(ConfiguredSurfaceBuilders.BADLANDS)
                .build())
            .build();
        Registry.register(BuiltinRegistries.BIOME, new Identifier("palettebypass:thonk"), b);
        OverworldBiomes.addContinentalBiome(RegistryKey.of(Registry.BIOME_KEY, new Identifier("palettebypass:thonk")), OverworldClimate.DRY, 20D);
    }
}
