package me.basiqueevangelist.flashfreeze.mixin;

import me.basiqueevangelist.flashfreeze.access.ChunkAccess;
import me.basiqueevangelist.flashfreeze.chunk.FakeWorldChunk;
import me.basiqueevangelist.flashfreeze.util.FFPlatform;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.structure.StructureManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.ChunkSerializer;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ProtoChunk;
import net.minecraft.world.poi.PointOfInterestStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ChunkSerializer.class)
public class ChunkSerializerMixin {
    @Redirect(method = "loadEntities", at = @At(value = "INVOKE", target = "Lnet/minecraft/nbt/NbtCompound;getBoolean(Ljava/lang/String;)Z"))
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
        if (FFPlatform.isFabricModLoaded("cardinal-components-chunk")) return;

        NbtCompound targetTag = cir.getReturnValue().getCompound("Level");
        ((ChunkAccess) chunk).flashfreeze$getComponentHolder().toTag(targetTag);
    }

    @Inject(method = "deserialize", at = @At("RETURN"))
    private static void readCCAComponents(ServerWorld world, StructureManager structureManager, PointOfInterestStorage poiStorage, ChunkPos pos, NbtCompound nbt, CallbackInfoReturnable<ProtoChunk> cir) {
        if (FFPlatform.isFabricModLoaded("cardinal-components-chunk")) return;

        NbtCompound targetTag = nbt.getCompound("Level");
        ((ChunkAccess) cir.getReturnValue()).flashfreeze$getComponentHolder().fromTag(targetTag);
    }
}
