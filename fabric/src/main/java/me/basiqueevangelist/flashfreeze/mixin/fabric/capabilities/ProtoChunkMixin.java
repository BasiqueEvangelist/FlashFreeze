package me.basiqueevangelist.flashfreeze.mixin.fabric.capabilities;

import me.basiqueevangelist.flashfreeze.access.fabric.FabricChunkAccess;
import me.basiqueevangelist.flashfreeze.fabric.capabilities.CapabilityHolder;
import net.minecraft.world.chunk.ProtoChunk;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ProtoChunk.class)
public class ProtoChunkMixin implements FabricChunkAccess {
    @Override
    public CapabilityHolder flashfreeze$getCapabilityHolder() {
        return null;
    }
}
