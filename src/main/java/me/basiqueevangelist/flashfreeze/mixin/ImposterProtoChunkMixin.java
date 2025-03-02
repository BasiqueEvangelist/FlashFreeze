package me.basiqueevangelist.flashfreeze.mixin;

import me.basiqueevangelist.flashfreeze.access.ChunkAccess;
import me.basiqueevangelist.flashfreeze.components.ComponentHolder;
import net.minecraft.world.level.chunk.ImposterProtoChunk;
import net.minecraft.world.level.chunk.LevelChunk;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ImposterProtoChunk.class)
public class ImposterProtoChunkMixin implements ChunkAccess {
    @Shadow @Final private LevelChunk wrapped;

    @Override
    public ComponentHolder flashfreeze$getComponentHolder() {
        return ((ChunkAccess) wrapped).flashfreeze$getComponentHolder();
    }
}
