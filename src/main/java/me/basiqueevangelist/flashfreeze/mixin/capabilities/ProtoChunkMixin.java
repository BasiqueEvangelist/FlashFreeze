package me.basiqueevangelist.flashfreeze.mixin.capabilities;

import me.basiqueevangelist.flashfreeze.access.fabric.FabricChunkAccess;
import me.basiqueevangelist.flashfreeze.capabilities.CapabilityHolder;
import net.minecraft.world.chunk.ProtoChunk;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ProtoChunk.class)
public class ProtoChunkMixin implements FabricChunkAccess {
    @Override
    public CapabilityHolder flashfreeze$getCapabilityHolder() {
        return null;
    }
}
