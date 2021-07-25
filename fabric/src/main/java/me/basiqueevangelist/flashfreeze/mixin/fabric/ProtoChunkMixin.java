package me.basiqueevangelist.flashfreeze.mixin.fabric;

import me.basiqueevangelist.flashfreeze.fabric.access.FabricChunkAccess;
import me.basiqueevangelist.flashfreeze.fabric.capabilities.CapabilityHolder;
import net.minecraft.world.chunk.ProtoChunk;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ProtoChunk.class)
public class ProtoChunkMixin implements FabricChunkAccess {
    @Override
    public @Nullable CapabilityHolder flashfreeze$getCapabilityHolder() {
        return null;
    }
}
