package me.basiqueevangelist.flashfreeze.mixin;

import me.basiqueevangelist.flashfreeze.access.ChunkAccess;
import me.basiqueevangelist.flashfreeze.components.ComponentHolder;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.WorldChunk;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

@Mixin(WorldChunk.class)
public class WorldChunkMixin implements ChunkAccess {
    @Shadow @Final private Map<BlockPos, BlockEntity> blockEntities;

    @Shadow @Final private Map<BlockPos, NbtCompound> pendingBlockEntityNbts;

    @Unique private final ComponentHolder componentHolder = new ComponentHolder();

    @Redirect(method = "runPostProcessing", at = @At(value = "INVOKE", target = "Ljava/util/Map;clear()V"))
    private void clearOnlyKnownBlockEntities(Map<BlockPos, NbtCompound> map) {
        map.entrySet().removeIf(entry -> blockEntities.containsKey(entry.getKey()));
    }

    @Redirect(method = "getBlockEntity(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/chunk/WorldChunk$CreationType;)Lnet/minecraft/block/entity/BlockEntity;", at = @At(value = "INVOKE", target = "Ljava/util/Map;remove(Ljava/lang/Object;)Ljava/lang/Object;", ordinal = 0))
    private Object onlyGetFromMap(Map<BlockPos, NbtCompound> map, Object key) {
        return map.get(key);
    }

    @Inject(method = "getBlockEntity(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/chunk/WorldChunk$CreationType;)Lnet/minecraft/block/entity/BlockEntity;", at = @At(value = "RETURN", ordinal = 0))
    private void removeFromMap(BlockPos pos, WorldChunk.CreationType creationType, CallbackInfoReturnable<BlockEntity> cir) {
        pendingBlockEntityNbts.remove(pos);
    }

    @Override
    public ComponentHolder flashfreeze$getComponentHolder() {
        return componentHolder;
    }
}
