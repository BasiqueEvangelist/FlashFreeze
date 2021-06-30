package me.basiqueevangelist.palettebypass.mixin;

import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import me.basiqueevangelist.palettebypass.access.BiomeArrayAccess;
import net.minecraft.util.collection.IndexedIterable;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.SimpleRegistry;
import net.minecraft.world.HeightLimitView;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.biome.BuiltinBiomes;
import net.minecraft.world.biome.source.BiomeArray;
import net.minecraft.world.biome.source.BiomeSource;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(BiomeArray.class)
public class BiomeArrayMixin implements BiomeArrayAccess {
    @Shadow @Final private Biome[] data;
    @Shadow @Final private IndexedIterable<Biome> biomes;
    @Shadow @Final private static int HORIZONTAL_BIT_MASK;
    @Shadow @Final private int field_28126;
    @Shadow @Final private int field_28127;
    @Shadow @Final private static int HORIZONTAL_SECTION_COUNT;
    @Unique @Nullable private Int2IntMap fakeBiomes = null;
    @Unique private int arrayPos;

    @Inject(method = "<init>(Lnet/minecraft/util/collection/IndexedIterable;Lnet/minecraft/world/HeightLimitView;Lnet/minecraft/util/math/ChunkPos;Lnet/minecraft/world/biome/source/BiomeSource;[I)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/collection/IndexedIterable;get(I)Ljava/lang/Object;"), locals = LocalCapture.CAPTURE_FAILHARD)
    private void getArrayPos(IndexedIterable<?> biomes, HeightLimitView world, ChunkPos chunkPos, BiomeSource biomeSource, int[] is, CallbackInfo ci, int i, int j, int k, int l) {
        arrayPos = l;
    }

    @Redirect(method = "<init>(Lnet/minecraft/util/collection/IndexedIterable;Lnet/minecraft/world/HeightLimitView;Lnet/minecraft/util/math/ChunkPos;Lnet/minecraft/world/biome/source/BiomeSource;[I)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/collection/IndexedIterable;get(I)Ljava/lang/Object;"))
    private Object tryGetBiome(IndexedIterable<Biome> registry, int index) {
        Biome b = registry.get(index);
        if (b == null) {
            if (fakeBiomes == null) fakeBiomes = new Int2IntOpenHashMap();
            fakeBiomes.put(arrayPos, index);
            return registry instanceof SimpleRegistry<Biome> ? ((SimpleRegistry<Biome>) registry).get(BiomeKeys.THE_VOID) : BuiltinBiomes.THE_VOID;
        }
        return b;
    }

    @Inject(method = "toIntArray", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/collection/IndexedIterable;getRawId(Ljava/lang/Object;)I"), locals = LocalCapture.CAPTURE_FAILHARD)
    private void getArrayPos(CallbackInfoReturnable<int[]> cir, int[] is, int i) {
        arrayPos = i;
    }

    @Redirect(method = "toIntArray", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/collection/IndexedIterable;getRawId(Ljava/lang/Object;)I"))
    private int replaceIfNeeded(IndexedIterable<Object> registry, Object entry) {
        if (fakeBiomes != null) {
            int fakeId = fakeBiomes.getOrDefault(arrayPos, -1);
            if (fakeId != -1)
                return fakeId;
        }
        return registry.getRawId(entry);
    }

    @Override
    public int[] toPlayerIntArray() {
        int[] bytes = new int[data.length];

        for(int i = 0; i < this.data.length; ++i) {
            bytes[i] = biomes.getRawId(this.data[i]);
        }

        return bytes;
    }

    @Override
    public int getUnknownIndexAt(int x, int y, int z) {
        int i = x & HORIZONTAL_BIT_MASK;
        int j = MathHelper.clamp(y - field_28126, 0, field_28127);
        int k = z & HORIZONTAL_BIT_MASK;

        return fakeBiomes == null ? -1 : fakeBiomes.getOrDefault(j << HORIZONTAL_SECTION_COUNT + HORIZONTAL_SECTION_COUNT | k << HORIZONTAL_SECTION_COUNT | i, -1);
    }
}
