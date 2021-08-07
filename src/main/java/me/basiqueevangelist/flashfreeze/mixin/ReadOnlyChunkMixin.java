package me.basiqueevangelist.flashfreeze.mixin;

import me.basiqueevangelist.flashfreeze.access.ChunkAccess;
import me.basiqueevangelist.flashfreeze.capabilities.CapabilityHolder;
import me.basiqueevangelist.flashfreeze.components.ComponentHolder;
import net.minecraft.world.chunk.ReadOnlyChunk;
import net.minecraft.world.chunk.WorldChunk;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ReadOnlyChunk.class)
public class ReadOnlyChunkMixin implements ChunkAccess {
    @Shadow @Final private WorldChunk wrapped;

    @Override
    public ComponentHolder flashfreeze$getComponentHolder() {
        return ((ChunkAccess) wrapped).flashfreeze$getComponentHolder();
    }

    @Override
    public CapabilityHolder flashfreeze$getCapabilityHolder() {
        return ((ChunkAccess) wrapped).flashfreeze$getCapabilityHolder();
    }
}
