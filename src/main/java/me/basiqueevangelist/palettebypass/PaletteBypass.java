package me.basiqueevangelist.palettebypass;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.OverworldBiomes;
import net.fabricmc.fabric.api.biome.v1.OverworldClimate;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeEffects;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.gen.surfacebuilder.ConfiguredSurfaceBuilders;
import org.apache.logging.log4j.LogManager;

public class PaletteBypass implements ModInitializer {
    @Override
    public void onInitialize() {
        LogManager.getLogger("PaletteBypass").info("Bypassing palettes since 2021");

//        Biome b = new Biome.Builder()
//            .precipitation(Biome.Precipitation.NONE)
//            .category(Biome.Category.NONE)
//            .depth(0.1F)
//            .scale(0.2F)
//            .temperature(2.0F)
//            .downfall(0)
//            .effects(new BiomeEffects.Builder()
//                .waterColor(4159204)
//                .waterFogColor(329011)
//                .fogColor(0xffbbff)
//                .skyColor(0xaaccdd)
//                .build())
//            .spawnSettings(new SpawnSettings.Builder().build())
//            .generationSettings(new GenerationSettings.Builder()
//                .surfaceBuilder(ConfiguredSurfaceBuilders.BADLANDS)
//                .build())
//            .build();
//        Registry.register(BuiltinRegistries.BIOME, new Identifier("palettebypass:thonk"), b);
//        OverworldBiomes.addContinentalBiome(RegistryKey.of(Registry.BIOME_KEY, new Identifier("palettebypass:thonk")), OverworldClimate.DRY, 20D);
    }

    public static BlockState getForUnknown(UnknownBlockState state) {
        // Returns bedrock for now.
        return Blocks.BEDROCK.getDefaultState();
    }

    public static ItemStack makeFakeStack(NbtCompound tag) {
        ItemStack stack = new ItemStack(Items.NETHER_STAR);
        stack.getOrCreateTag().put("OriginalData", tag);
        stack.setCustomName(new LiteralText("Unknown item: " + tag.getString("id")));
        return stack;
    }
}
