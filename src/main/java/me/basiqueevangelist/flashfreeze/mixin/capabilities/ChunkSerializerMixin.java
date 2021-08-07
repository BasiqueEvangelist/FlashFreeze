package me.basiqueevangelist.flashfreeze.mixin.capabilities;

import me.basiqueevangelist.flashfreeze.access.ChunkAccess;
import me.basiqueevangelist.flashfreeze.capabilities.CapabilityHolder;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.structure.StructureManager;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.ChunkSerializer;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ProtoChunk;
import net.minecraft.world.poi.PointOfInterestStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ChunkSerializer.class)
public class ChunkSerializerMixin {
    @Inject(method = "serialize", at = @At("RETURN"))
    private static void writeCapabilities(ServerWorld world, Chunk chunk, CallbackInfoReturnable<NbtCompound> cir) {
        NbtCompound targetTag = cir.getReturnValue().getCompound("Level");
        CapabilityHolder holder = ((ChunkAccess) chunk).flashfreeze$getCapabilityHolder();
        if (holder != null)
            holder.toTag(targetTag);
    }

    @Inject(method = "deserialize", at = @At("RETURN"))
    private static void readCapabilities(ServerWorld world, StructureManager structureManager, PointOfInterestStorage poiStorage, ChunkPos pos, NbtCompound nbt, CallbackInfoReturnable<ProtoChunk> cir) {
        NbtCompound targetTag = nbt.getCompound("Level");
        CapabilityHolder holder = ((ChunkAccess) cir.getReturnValue()).flashfreeze$getCapabilityHolder();
        if (holder != null)
            holder.toTag(targetTag);
    }
}
