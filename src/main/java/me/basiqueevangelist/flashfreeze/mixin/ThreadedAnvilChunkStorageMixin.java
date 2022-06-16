package me.basiqueevangelist.flashfreeze.mixin;

import com.mojang.datafixers.util.Either;
import me.basiqueevangelist.flashfreeze.chunk.FakeProtoChunk;
import me.basiqueevangelist.flashfreeze.chunk.FakeWorldChunk;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.server.world.ChunkHolder;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.server.world.ThreadedAnvilChunkStorage;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraft.world.chunk.ProtoChunk;
import net.minecraft.world.chunk.WorldChunk;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.IOException;

@Mixin(ThreadedAnvilChunkStorage.class)
public abstract class ThreadedAnvilChunkStorageMixin {
    @Shadow protected abstract byte mark(ChunkPos chunkPos, ChunkStatus.ChunkType chunkType);

    @Shadow @Final ServerWorld world;

    @Shadow protected abstract NbtCompound getUpdatedChunkNbt(ChunkPos pos) throws IOException;

    @Inject(method = "method_17256", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/world/ThreadedAnvilChunkStorage;markAsProtoChunk(Lnet/minecraft/util/math/ChunkPos;)V", ordinal = 1), cancellable = true)
    private void makeFakeChunk(ChunkPos pos, CallbackInfoReturnable<Either<Chunk, ChunkHolder.Unloaded>> cir) {
        try {
            NbtCompound updatedTag = getUpdatedChunkNbt(pos);
            if (!updatedTag.contains("Level", NbtElement.COMPOUND_TYPE) || !updatedTag.getCompound("Level").contains("Status", NbtElement.STRING_TYPE))
                return;
            FakeProtoChunk fake = new FakeProtoChunk(pos, world, world.getRegistryManager().get(Registry.BIOME_KEY), updatedTag);
            mark(pos, ChunkStatus.ChunkType.PROTOCHUNK);
            cir.setReturnValue(Either.left(fake));
        } catch (Exception ignored) {

        }
    }

    @Redirect(method = "method_17227", at = @At(value = "NEW", target = "net/minecraft/world/chunk/WorldChunk"))
    private WorldChunk replaceWithFakeIfNeeded(ServerWorld serverWorld, ProtoChunk protoChunk, WorldChunk.EntityLoader entityLoader) {
        if (protoChunk instanceof FakeProtoChunk)
            return new FakeWorldChunk(serverWorld, protoChunk.getPos(), ((FakeProtoChunk) protoChunk).getUpdatedTag());

        return new WorldChunk(serverWorld, protoChunk, entityLoader);
    }
}
