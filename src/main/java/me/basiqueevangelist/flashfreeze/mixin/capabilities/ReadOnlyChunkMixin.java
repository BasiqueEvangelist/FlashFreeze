package me.basiqueevangelist.flashfreeze.mixin.capabilities;

import me.basiqueevangelist.flashfreeze.access.fabric.FabricChunkAccess;
import me.basiqueevangelist.flashfreeze.capabilities.CapabilityHolder;
import net.minecraft.world.chunk.ReadOnlyChunk;
import net.minecraft.world.chunk.WorldChunk;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ReadOnlyChunk.class)
public class ReadOnlyChunkMixin implements FabricChunkAccess {
    @Shadow @Final private WorldChunk wrapped;

    @Override
    public CapabilityHolder flashfreeze$getCapabilityHolder() {
        return ((FabricChunkAccess) wrapped).flashfreeze$getCapabilityHolder();
    }
}
