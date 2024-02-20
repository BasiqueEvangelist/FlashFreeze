package me.basiqueevangelist.flashfreeze.mixin;

import me.basiqueevangelist.flashfreeze.access.ChunkAccess;
import me.basiqueevangelist.flashfreeze.components.ComponentHolder;
import net.minecraft.world.chunk.WorldChunk;
import net.minecraft.world.chunk.WrapperProtoChunk;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(WrapperProtoChunk.class)
public class WrapperProtoChunkMixin implements ChunkAccess {
    @Shadow @Final private WorldChunk wrapped;

    @Override
    public ComponentHolder flashfreeze$getComponentHolder() {
        return ((ChunkAccess) wrapped).flashfreeze$getComponentHolder();
    }
}
