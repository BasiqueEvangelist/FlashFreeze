package me.basiqueevangelist.flashfreeze.mixin;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import me.basiqueevangelist.flashfreeze.UnknownBiome;
import me.basiqueevangelist.flashfreeze.UnknownBlockState;
import me.basiqueevangelist.flashfreeze.util.UnknownBlock;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.storage.SerializableChunkData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(SerializableChunkData.class)
public class SerializableChunkDataMixin {
    @Redirect(method = {"lambda$postLoadChunk$12", "method_61797"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/nbt/CompoundTag;getBooleanOr(Ljava/lang/String;Z)Z"))
    private static boolean dontLoadIfUnknown(CompoundTag instance, String key, boolean defaultValue) {
        if (instance.contains("id")) {
            String id = instance.getStringOr("id", "");

            if (!id.equals("DUMMY") && !BuiltInRegistries.BLOCK_ENTITY_TYPE.containsKey(ResourceLocation.parse(id)))
                return true;
        }

        return instance.getBooleanOr(key, defaultValue);
    }

    @ModifyArg(method = "<clinit>", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/chunk/PalettedContainer;codecRW(Lnet/minecraft/core/IdMap;Lcom/mojang/serialization/Codec;Lnet/minecraft/world/level/chunk/PalettedContainer$Strategy;Ljava/lang/Object;)Lcom/mojang/serialization/Codec;"))
    private static Codec<Object> switchBlockStateCodec(Codec<BlockState> old) {
        return new Codec<>() {
            @Override
            @SuppressWarnings({"unchecked", "rawtypes"})
            public <T> DataResult<Pair<Object, T>> decode(DynamicOps<T> ops, T input) {
                if (ops instanceof NbtOps && input instanceof CompoundTag tag) {
                    if (tag.contains("Name")) {
                        if (!BuiltInRegistries.BLOCK.containsKey(ResourceLocation.parse(tag.getStringOr("Name", "")))) {
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

    @ModifyArg(method = "<clinit>", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/ticks/SavedTick;codec(Lcom/mojang/serialization/Codec;)Lcom/mojang/serialization/Codec;", ordinal = 0))
    private static Codec<Block> switchOutBlockCodec(Codec<Block> codec) {
        return new Codec<Block>() {
            @Override
            public <T> DataResult<Pair<Block, T>> decode(DynamicOps<T> ops, T input) {
                var asString = ops.getStringValue(input).result().orElse(null);
                if (asString != null) {
                    var asId = ResourceLocation.tryParse(asString);

                    if (asId != null && !BuiltInRegistries.BLOCK.containsKey(asId))
                        return DataResult.success(Pair.of(UnknownBlock.get(asId), ops.empty()));
                }

                return codec.decode(ops, input);
            }

            @Override
            public <T> DataResult<T> encode(Block input, DynamicOps<T> ops, T prefix) {
                if (input instanceof UnknownBlock unk) {
                    return DataResult.success(ops.createString(unk.id().toString()));
                }

                return codec.encode(input, ops, prefix);
            }
        };
    }
}
