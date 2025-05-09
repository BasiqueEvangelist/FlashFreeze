package me.basiqueevangelist.flashfreeze.mixin;

import me.basiqueevangelist.flashfreeze.access.ChunkAccess;
import me.basiqueevangelist.flashfreeze.components.ComponentHolder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.chunk.UpgradeData;
import net.minecraft.world.level.levelgen.blending.BlendingData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

@Mixin(LevelChunk.class)
public abstract class LevelChunkMixin extends net.minecraft.world.level.chunk.ChunkAccess implements ChunkAccess {
    @Unique private final ComponentHolder componentHolder = new ComponentHolder();

    public LevelChunkMixin(ChunkPos chunkPos, UpgradeData upgradeData, LevelHeightAccessor heightLimitView, Registry<Biome> registry, long l, LevelChunkSection[] chunkSections, BlendingData blendingData) {
        super(chunkPos, upgradeData, heightLimitView, registry, l, chunkSections, blendingData);
    }

    @Redirect(method = "postProcessGeneration", at = @At(value = "INVOKE", target = "Ljava/util/Map;clear()V"))
    private void clearOnlyKnownBlockEntities(Map<BlockPos, CompoundTag> map) {
        map.entrySet().removeIf(entry -> blockEntities.containsKey(entry.getKey()));
    }

    @Redirect(method = "getBlockEntity(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/chunk/LevelChunk$EntityCreationType;)Lnet/minecraft/world/level/block/entity/BlockEntity;", at = @At(value = "FIELD", target = "Lnet/minecraft/world/level/chunk/LevelChunk;pendingBlockEntities:Ljava/util/Map;", shift = At.Shift.BY, by = 2, ordinal = 0))
    private Object onlyGetFromMap(Map<BlockPos, CompoundTag> map, Object key) {
        return map.get(key);
    }

    @Inject(method = "getBlockEntity(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/chunk/LevelChunk$EntityCreationType;)Lnet/minecraft/world/level/block/entity/BlockEntity;", at = @At(value = "RETURN", ordinal = 0))
    private void removeFromMap(BlockPos pos, LevelChunk.EntityCreationType creationType, CallbackInfoReturnable<BlockEntity> cir) {
        pendingBlockEntities.remove(pos);
    }

    @Inject(method = "promotePendingBlockEntity", at = @At(value = "INVOKE", target = "Lorg/slf4j/Logger;warn(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;)V", ordinal = 1), cancellable = true)
    private void shhhhhhh(BlockPos pos, CompoundTag nbt, CallbackInfoReturnable<BlockEntity> cir) {
        ResourceLocation id = ResourceLocation.tryParse(nbt.getString("id").orElse(""));

        if (id != null && !BuiltInRegistries.BLOCK_ENTITY_TYPE.containsKey(id)) {
            cir.setReturnValue(null);
        }
    }

    @Override
    public ComponentHolder flashfreeze$getComponentHolder() {
        return componentHolder;
    }
}
