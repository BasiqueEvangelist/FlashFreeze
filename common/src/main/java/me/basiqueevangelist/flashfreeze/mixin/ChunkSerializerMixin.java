package me.basiqueevangelist.flashfreeze.mixin;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import me.basiqueevangelist.flashfreeze.UnknownBiome;
import me.basiqueevangelist.flashfreeze.UnknownBlockState;
import me.basiqueevangelist.flashfreeze.access.ChunkAccess;
import me.basiqueevangelist.flashfreeze.util.FlashFreezePlatform;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ProtoChunk;
import net.minecraft.world.level.chunk.storage.ChunkSerializer;
import net.minecraft.world.level.chunk.storage.RegionStorageInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ChunkSerializer.class)
public class ChunkSerializerMixin {
    @Redirect(method = {"lambda$postLoadChunk$10", "method_39797"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/nbt/CompoundTag;getBoolean(Ljava/lang/String;)Z"))
    private static boolean dontLoadIfUnknown(CompoundTag tag, String name) {
        if (tag.contains("id", Tag.TAG_STRING)) {
            String id = tag.getString("id");
            if (!id.equals("DUMMY") && !BuiltInRegistries.BLOCK_ENTITY_TYPE.containsKey(ResourceLocation.parse(id)))
                return true;
        }

        return tag.getBoolean(name);
    }

    @Inject(method = "write", at = @At("RETURN"))
    private static void writeCCAComponents(ServerLevel world, net.minecraft.world.level.chunk.ChunkAccess chunk, CallbackInfoReturnable<CompoundTag> cir) {
        if (FlashFreezePlatform.I.isModLoaded("cardinal-components-chunk")) return;

        CompoundTag targetTag = cir.getReturnValue();
        ((ChunkAccess) chunk).flashfreeze$getComponentHolder().toTag(targetTag);
    }

    @Inject(method = "read", at = @At("RETURN"))
    private static void readCCAComponents(ServerLevel world, PoiManager poiStorage, RegionStorageInfo key, ChunkPos chunkPos, CompoundTag nbt, CallbackInfoReturnable<ProtoChunk> cir) {
        if (FlashFreezePlatform.I.isModLoaded("cardinal-components-chunk")) return;

        ((ChunkAccess) cir.getReturnValue()).flashfreeze$getComponentHolder().fromTag(nbt);
    }

    @ModifyArg(method = "<clinit>", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/chunk/PalettedContainer;codecRW(Lnet/minecraft/core/IdMap;Lcom/mojang/serialization/Codec;Lnet/minecraft/world/level/chunk/PalettedContainer$Strategy;Ljava/lang/Object;)Lcom/mojang/serialization/Codec;"))
    private static Codec<Object> switchBlockStateCodec(Codec<BlockState> old) {
        return new Codec<>() {
            @Override
            @SuppressWarnings({"unchecked", "rawtypes"})
            public <T> DataResult<Pair<Object, T>> decode(DynamicOps<T> ops, T input) {
                if (ops instanceof NbtOps && input instanceof CompoundTag tag) {
                    if (tag.contains("Name", Tag.TAG_STRING)) {
                        if (!BuiltInRegistries.BLOCK.containsKey(ResourceLocation.parse(tag.getString("Name")))) {
                            return DataResult.success(Pair.of(UnknownBlockState.fromTag(tag), ops.empty()));
                        }
                    }
                }
                return (DataResult) old.decode(ops, input);
            }

            @SuppressWarnings("unchecked")
            @Override
            public <T> DataResult<T> encode(Object input, DynamicOps<T> ops, T prefix) {
                if (ops instanceof NbtOps && input instanceof UnknownBlockState ubs)
                    return DataResult.success((T) ((ubs.toTag(prefix instanceof CompoundTag ? (CompoundTag) prefix : new CompoundTag()))));

                return old.encode((BlockState) input, ops, prefix);
            }
        };
    }

    @Redirect(method = "makeBiomeCodec", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/Registry;holderByNameCodec()Lcom/mojang/serialization/Codec;"))
    private static Codec<Object> test(Registry<Biome> biomes) {
        var old = biomes.holderByNameCodec();
        return new Codec<>() {
            @SuppressWarnings({"unchecked", "rawtypes"})
            @Override
            public <T> DataResult<Pair<Object, T>> decode(DynamicOps<T> ops, T input) {
                var possibleUnknownBiome = ResourceLocation.CODEC.decode(ops, input).result().map(Pair::getFirst);
                if (possibleUnknownBiome.isPresent()) {
                    var id = possibleUnknownBiome.get();
                    if (!biomes.containsKey(id)) {
                        return DataResult.success(Pair.of(new UnknownBiome(id), ops.empty()));
                    }
                }
                return (DataResult) old.decode(ops, input);
            }

            @SuppressWarnings("unchecked")
            @Override
            public <T> DataResult<T> encode(Object input, DynamicOps<T> ops, T prefix) {
                if (input instanceof UnknownBiome ubs)
                    return ResourceLocation.CODEC.encode(ubs.id(), ops, prefix);

                return old.encode((Holder<Biome>) input, ops, prefix);
            }
        };
    }
}
