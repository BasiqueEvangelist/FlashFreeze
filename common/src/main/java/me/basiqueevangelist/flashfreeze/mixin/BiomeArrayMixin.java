package me.basiqueevangelist.flashfreeze.mixin;

import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import me.basiqueevangelist.flashfreeze.access.BiomeArrayAccess;
import net.minecraft.util.collection.IndexedIterable;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.SimpleRegistry;
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
    @Shadow @Final private IndexedIterable<Biome> field_25831;
    @Shadow @Final private static int HORIZONTAL_BIT_MASK;
    @Shadow @Final private static int HORIZONTAL_SECTION_COUNT;
    @Shadow @Final public static int VERTICAL_BIT_MASK;
    @Unique @Nullable private Int2IntMap fakeBiomes = null;
    @Unique private int arrayPos;

    @Inject(method = "<init>(Lnet/minecraft/util/collection/IndexedIterable;Lnet/minecraft/util/math/ChunkPos;Lnet/minecraft/world/biome/source/BiomeSource;[I)V", at = @At(value = "FF_CONCERN_INVOKE", target = "Lnet/minecraft/util/collection/IndexedIterable;get(I)Ljava/lang/Object;"), locals = LocalCapture.CAPTURE_FAILHARD)
    private void getArrayPos(IndexedIterable<Biome> arg, ChunkPos arg2, BiomeSource arg3, int is[], CallbackInfo ci, int i, int j, int k) {
        arrayPos = k;
    }

    @Redirect(method = "<init>(Lnet/minecraft/util/collection/IndexedIterable;Lnet/minecraft/util/math/ChunkPos;Lnet/minecraft/world/biome/source/BiomeSource;[I)V", at = @At(value = "FF_CONCERN_INVOKE", target = "Lnet/minecraft/util/collection/IndexedIterable;get(I)Ljava/lang/Object;"))
    private Object tryGetBiome(IndexedIterable<Biome> registry, int index) {
        Biome b = registry.get(index);
        if (b == null) {
            if (fakeBiomes == null) fakeBiomes = new Int2IntOpenHashMap();
            fakeBiomes.put(arrayPos, index);
            return registry instanceof SimpleRegistry ? ((SimpleRegistry<Biome>) registry).get(BiomeKeys.THE_VOID) : BuiltinBiomes.THE_VOID;
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
            bytes[i] = field_25831.getRawId(this.data[i]);
        }

        return bytes;
    }

    @Override
    public int getUnknownIndexAt(int x, int y, int z) {
        int i = x & HORIZONTAL_BIT_MASK;
        int j = MathHelper.clamp(y, 0, VERTICAL_BIT_MASK);
        int k = z & HORIZONTAL_BIT_MASK;

        return fakeBiomes == null ? -1 : fakeBiomes.getOrDefault(j << HORIZONTAL_SECTION_COUNT + HORIZONTAL_SECTION_COUNT | k << HORIZONTAL_SECTION_COUNT | i, -1);
    }
}
