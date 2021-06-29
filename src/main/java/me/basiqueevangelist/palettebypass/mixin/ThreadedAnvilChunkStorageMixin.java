package me.basiqueevangelist.palettebypass.mixin;

import com.mojang.datafixers.util.Either;
import me.basiqueevangelist.palettebypass.FakeProtoChunk;
import me.basiqueevangelist.palettebypass.FakeWorldChunk;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.server.world.ChunkHolder;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.server.world.ThreadedAnvilChunkStorage;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.chunk.*;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.IOException;
import java.util.function.Consumer;

@Mixin(ThreadedAnvilChunkStorage.class)
public abstract class ThreadedAnvilChunkStorageMixin {
    @Shadow protected abstract byte method_27053(ChunkPos chunkPos, ChunkStatus.ChunkType chunkType);

    @Shadow @Final ServerWorld world;

    @Shadow @Nullable protected abstract NbtCompound getUpdatedChunkNbt(ChunkPos pos) throws IOException;

    @Inject(method = "method_17256", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/world/ThreadedAnvilChunkStorage;method_27054(Lnet/minecraft/util/math/ChunkPos;)V", ordinal = 1), cancellable = true)
    private void makeFakeChunk(ChunkPos pos, CallbackInfoReturnable<Either<Chunk, ChunkHolder.Unloaded>> cir) {
        try {
            NbtCompound updatedTag = getUpdatedChunkNbt(pos);
            if (!updatedTag.contains("Level", NbtElement.COMPOUND_TYPE) || !updatedTag.getCompound("Level").contains("Status", NbtElement.STRING_TYPE))
                return;
            FakeProtoChunk fake = new FakeProtoChunk(pos, UpgradeData.NO_UPGRADE_DATA, world, updatedTag);
            method_27053(pos, ChunkStatus.ChunkType.PROTOCHUNK);
            cir.setReturnValue(Either.left(fake));
        } catch (Exception ignored) {

        }
    }

    @Redirect(method = "method_17227", at = @At(value = "NEW", target = "net/minecraft/world/chunk/WorldChunk"))
    private WorldChunk replaceWithFakeIfNeeded(ServerWorld serverWorld, ProtoChunk protoChunk, @Nullable Consumer<WorldChunk> consumer) {
        if (protoChunk instanceof FakeProtoChunk)
            return new FakeWorldChunk(serverWorld, protoChunk.getPos(), ((FakeProtoChunk) protoChunk).getUpdatedTag());

        return new WorldChunk(serverWorld, protoChunk, consumer);
    }
}
