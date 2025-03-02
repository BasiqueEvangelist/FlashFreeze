package me.basiqueevangelist.flashfreeze.mixin.capabilities;

import me.basiqueevangelist.flashfreeze.access.fabric.FabricChunkAccess;
import me.basiqueevangelist.flashfreeze.capabilities.CapabilityHolder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ProtoChunk;
import net.minecraft.world.level.chunk.storage.ChunkSerializer;
import net.minecraft.world.level.chunk.storage.RegionStorageInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ChunkSerializer.class)
public class ChunkSerializerMixin {
    @Inject(method = "write", at = @At("RETURN"))
    private static void writeCapabilities(ServerLevel world, ChunkAccess chunk, CallbackInfoReturnable<CompoundTag> cir) {
        CompoundTag targetTag = cir.getReturnValue().getCompound("Level");
        CapabilityHolder holder = ((FabricChunkAccess) chunk).flashfreeze$getCapabilityHolder();
        if (holder != null)
            holder.toTag(targetTag);
    }

    @Inject(method = "read", at = @At("RETURN"))
    private static void readCapabilities(ServerLevel world, PoiManager poiStorage, RegionStorageInfo key, ChunkPos chunkPos, CompoundTag nbt, CallbackInfoReturnable<ProtoChunk> cir) {
        CapabilityHolder holder = ((FabricChunkAccess) cir.getReturnValue()).flashfreeze$getCapabilityHolder();
        if (holder != null)
            holder.toTag(nbt);
    }
}
