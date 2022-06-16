package me.basiqueevangelist.flashfreeze.mixin;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import me.basiqueevangelist.flashfreeze.UnknownBiome;
import me.basiqueevangelist.flashfreeze.UnknownBlockState;
import me.basiqueevangelist.flashfreeze.access.ChunkAccess;
import me.basiqueevangelist.flashfreeze.chunk.FakeWorldChunk;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.NbtString;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.IndexedIterable;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.ChunkSerializer;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.PalettedContainer;
import net.minecraft.world.chunk.ProtoChunk;
import net.minecraft.world.poi.PointOfInterestStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ChunkSerializer.class)
public class ChunkSerializerMixin {
    private static Registry<Biome> ff$savedBiomeRegistry;

    @Redirect(method = "method_39797", at = @At(value = "INVOKE", target = "Lnet/minecraft/nbt/NbtCompound;getBoolean(Ljava/lang/String;)Z"))
    private static boolean dontLoadIfUnknown(NbtCompound tag, String name) {
        if (tag.contains("id", NbtElement.STRING_TYPE)) {
            if (!Registry.BLOCK_ENTITY_TYPE.containsId(new Identifier(tag.getString("id"))))
                return true;
        }

        return tag.getBoolean(name);
    }

    @Inject(method = "serialize", at = @At("HEAD"), cancellable = true)
    private static void skipIfFake(ServerWorld world, Chunk chunk, CallbackInfoReturnable<NbtCompound> cir) {
        if (chunk instanceof FakeWorldChunk)
            cir.setReturnValue(((FakeWorldChunk) chunk).getUpdatedTag());
    }

    @Inject(method = "serialize", at = @At("RETURN"))
    private static void writeCCAComponents(ServerWorld world, Chunk chunk, CallbackInfoReturnable<NbtCompound> cir) {
        if (FabricLoader.getInstance().isModLoaded("cardinal-components-chunk")) return;

        NbtCompound targetTag = cir.getReturnValue();
        ((ChunkAccess) chunk).flashfreeze$getComponentHolder().toTag(targetTag);
    }

    @Inject(method = "deserialize", at = @At("RETURN"))
    private static void readCCAComponents(ServerWorld world, PointOfInterestStorage poiStorage, ChunkPos chunkPos, NbtCompound nbt, CallbackInfoReturnable<ProtoChunk> cir) {
        if (FabricLoader.getInstance().isModLoaded("cardinal-components-chunk")) return;

        ((ChunkAccess) cir.getReturnValue()).flashfreeze$getComponentHolder().fromTag(nbt);
    }

    @ModifyArg(method = "<clinit>", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/chunk/PalettedContainer;method_44343(Lnet/minecraft/util/collection/IndexedIterable;Lcom/mojang/serialization/Codec;Lnet/minecraft/world/chunk/PalettedContainer$PaletteProvider;Ljava/lang/Object;)Lcom/mojang/serialization/Codec;"))
    private static Codec<Object> switchBlockStateCodec(Codec<BlockState> old) {
        return new Codec<>() {
            @Override
            @SuppressWarnings({"unchecked", "rawtypes"})
            public <T> DataResult<Pair<Object, T>> decode(DynamicOps<T> ops, T input) {
                if (ops instanceof NbtOps && input instanceof NbtCompound tag) {
                    if (tag.contains("Name", NbtElement.STRING_TYPE)) {
                        if (!Registry.BLOCK.containsId(new Identifier(tag.getString("Name")))) {
                            return DataResult.success(Pair.of(new UnknownBlockState(tag), ops.empty()));
                        }
                    }
                }
                return (DataResult) old.decode(ops, input);
            }

            @SuppressWarnings("unchecked")
            @Override
            public <T> DataResult<T> encode(Object input, DynamicOps<T> ops, T prefix) {
                if (ops instanceof NbtOps && input instanceof UnknownBlockState ubs)
                    return DataResult.success((T) ((ubs.toTag(prefix instanceof NbtCompound ? (NbtCompound) prefix : new NbtCompound()))));

                return old.encode((BlockState) input, ops, prefix);
            }
        };
    }

    @Inject(method = "createCodec", at = @At("HEAD"))
    private static void saveRegistry(Registry<Biome> biomeRegistry, CallbackInfoReturnable<Codec<PalettedContainer<RegistryEntry<Biome>>>> cir) {
        ff$savedBiomeRegistry = biomeRegistry;
    }

    @Inject(method = "createCodec", at = @At("RETURN"))
    private static void dropRegistry(Registry<Biome> biomeRegistry, CallbackInfoReturnable<Codec<PalettedContainer<RegistryEntry<Biome>>>> cir) {
        ff$savedBiomeRegistry = null;
    }

    @ModifyArg(method = "createCodec", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/chunk/PalettedContainer;method_44347(Lnet/minecraft/util/collection/IndexedIterable;Lcom/mojang/serialization/Codec;Lnet/minecraft/world/chunk/PalettedContainer$PaletteProvider;Ljava/lang/Object;)Lcom/mojang/serialization/Codec;"))
    private static Codec<Object> switchBiomeCodec(IndexedIterable<Biome> idList, Codec<RegistryEntry<Biome>> old, PalettedContainer.PaletteProvider provider, Object object) {
        Registry<Biome> biomes = ff$savedBiomeRegistry;
        return new Codec<>() {
            @Override
            @SuppressWarnings({"unchecked", "rawtypes"})
            public <T> DataResult<Pair<Object, T>> decode(DynamicOps<T> ops, T input) {
                if (ops instanceof NbtOps && input instanceof NbtString tag) {
                    if (!biomes.containsId(new Identifier(tag.asString()))) {
                        return DataResult.success(Pair.of(new UnknownBiome(new Identifier(tag.asString())), ops.empty()));
                    }
                }
                return (DataResult) old.decode(ops, input);
            }

            @SuppressWarnings("unchecked")
            @Override
            public <T> DataResult<T> encode(Object input, DynamicOps<T> ops, T prefix) {
                if (ops instanceof NbtOps && input instanceof UnknownBiome ubs)
                    return DataResult.success((T) (NbtString.of(ubs.id().toString())));

                return old.encode((RegistryEntry<Biome>) input, ops, prefix);
            }
        };
    }
}
