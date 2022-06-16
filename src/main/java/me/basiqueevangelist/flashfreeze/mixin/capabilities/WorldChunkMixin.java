package me.basiqueevangelist.flashfreeze.mixin.capabilities;

import me.basiqueevangelist.flashfreeze.access.fabric.FabricChunkAccess;
import me.basiqueevangelist.flashfreeze.capabilities.CapabilityHolder;
import net.minecraft.world.chunk.WorldChunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(WorldChunk.class)
public class WorldChunkMixin implements FabricChunkAccess {
    @Unique private final CapabilityHolder capabilityHolder = new CapabilityHolder();

    @Override
    public CapabilityHolder flashfreeze$getCapabilityHolder() {
        return capabilityHolder;
    }
}
