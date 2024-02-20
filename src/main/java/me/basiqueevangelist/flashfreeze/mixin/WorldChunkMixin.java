package me.basiqueevangelist.flashfreeze.mixin;

import me.basiqueevangelist.flashfreeze.access.ChunkAccess;
import me.basiqueevangelist.flashfreeze.components.ComponentHolder;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.HeightLimitView;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkSection;
import net.minecraft.world.chunk.UpgradeData;
import net.minecraft.world.chunk.WorldChunk;
import net.minecraft.world.gen.chunk.BlendingData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

@Mixin(WorldChunk.class)
public abstract class WorldChunkMixin extends Chunk implements ChunkAccess {
    @Unique private final ComponentHolder componentHolder = new ComponentHolder();

    public WorldChunkMixin(ChunkPos chunkPos, UpgradeData upgradeData, HeightLimitView heightLimitView, Registry<Biome> registry, long l, ChunkSection[] chunkSections, BlendingData blendingData) {
        super(chunkPos, upgradeData, heightLimitView, registry, l, chunkSections, blendingData);
    }

    @Redirect(method = "runPostProcessing", at = @At(value = "INVOKE", target = "Ljava/util/Map;clear()V"))
    private void clearOnlyKnownBlockEntities(Map<BlockPos, NbtCompound> map) {
        map.entrySet().removeIf(entry -> blockEntities.containsKey(entry.getKey()));
    }

    @Redirect(method = "getBlockEntity(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/chunk/WorldChunk$CreationType;)Lnet/minecraft/block/entity/BlockEntity;", at = @At(value = "FIELD", target = "Lnet/minecraft/world/chunk/WorldChunk;blockEntityNbts:Ljava/util/Map;", shift = At.Shift.BY, by = 2, ordinal = 0))
    private Object onlyGetFromMap(Map<BlockPos, NbtCompound> map, Object key) {
        return map.get(key);
    }

    @Inject(method = "getBlockEntity(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/chunk/WorldChunk$CreationType;)Lnet/minecraft/block/entity/BlockEntity;", at = @At(value = "RETURN", ordinal = 0))
    private void removeFromMap(BlockPos pos, WorldChunk.CreationType creationType, CallbackInfoReturnable<BlockEntity> cir) {
        blockEntityNbts.remove(pos);
    }

    @Inject(method = "loadBlockEntity", at = @At(value = "INVOKE", target = "Lorg/slf4j/Logger;warn(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;)V", ordinal = 1), cancellable = true)
    private void shhhhhhh(BlockPos pos, NbtCompound nbt, CallbackInfoReturnable<BlockEntity> cir) {
        Identifier id = Identifier.tryParse(nbt.getString("id"));

        if (id != null && !Registries.BLOCK_ENTITY_TYPE.containsId(id)) {
            cir.setReturnValue(null);
        }
    }

    @Override
    public ComponentHolder flashfreeze$getComponentHolder() {
        return componentHolder;
    }
}
