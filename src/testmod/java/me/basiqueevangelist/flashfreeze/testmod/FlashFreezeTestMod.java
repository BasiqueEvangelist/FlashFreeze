package me.basiqueevangelist.flashfreeze.testmod;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistryV3;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeEffects;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;

public class FlashFreezeTestMod implements ModInitializer {
    public static final ComponentKey<TestComponent> TEST_COMPONENT = ComponentRegistryV3.INSTANCE.getOrCreate(new Identifier("flashfreeze:test"), TestComponent.class);
    public static final Biome THONK = makeThonk();

    private static Biome makeThonk() {
        var generationSettings = new GenerationSettings.Builder();
        DefaultBiomeFeatures.addLandCarvers(generationSettings);
        DefaultBiomeFeatures.addAmethystGeodes(generationSettings);
        DefaultBiomeFeatures.addDungeons(generationSettings);
        DefaultBiomeFeatures.addMineables(generationSettings);
        DefaultBiomeFeatures.addSprings(generationSettings);
        DefaultBiomeFeatures.addFrozenTopLayer(generationSettings);
        DefaultBiomeFeatures.addDefaultOres(generationSettings);
        DefaultBiomeFeatures.addExtraGoldOre(generationSettings);
        DefaultBiomeFeatures.addDefaultDisks(generationSettings);
        DefaultBiomeFeatures.addBadlandsGrass(generationSettings);
        DefaultBiomeFeatures.addDefaultMushrooms(generationSettings);
        DefaultBiomeFeatures.addBadlandsVegetation(generationSettings);
        return new Biome.Builder()
            .precipitation(Biome.Precipitation.NONE)
            .category(Biome.Category.NONE)
            .temperature(2.0F)
            .downfall(0)
            .effects(new BiomeEffects.Builder()
                .waterColor(4159204)
                .waterFogColor(329011)
                .fogColor(0xffbbff)
                .skyColor(0xaaccdd)
                .build())
            .spawnSettings(new SpawnSettings.Builder().build())
            .generationSettings(generationSettings.build())
            .build();
    }

    @Override
    public void onInitialize() {
        Registry.register(BuiltinRegistries.BIOME, new Identifier("flashfreeze:thonk"), THONK);
    }
}
