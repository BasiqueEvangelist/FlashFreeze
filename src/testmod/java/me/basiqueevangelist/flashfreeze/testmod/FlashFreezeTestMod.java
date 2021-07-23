package me.basiqueevangelist.flashfreeze.testmod;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistryV3;
import net.fabricmc.api.ModInitializer;
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

public class FlashFreezeTestMod implements ModInitializer {
    public static final ComponentKey<TestComponent> TEST_COMPONENT = ComponentRegistryV3.INSTANCE.getOrCreate(new Identifier("flashfreeze:test"), TestComponent.class);

    @Override
    public void onInitialize() {
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
        Registry.register(BuiltinRegistries.BIOME, new Identifier("flashfreeze:thonk"), b);
        OverworldBiomes.addContinentalBiome(RegistryKey.of(Registry.BIOME_KEY, new Identifier("flashfreeze:thonk")), OverworldClimate.DRY, 20D);
    }
}
