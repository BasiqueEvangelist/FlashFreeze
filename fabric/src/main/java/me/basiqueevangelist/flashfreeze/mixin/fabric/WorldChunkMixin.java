package me.basiqueevangelist.flashfreeze.mixin.fabric;

import me.basiqueevangelist.flashfreeze.fabric.access.FabricChunkAccess;
import me.basiqueevangelist.flashfreeze.fabric.capabilities.CapabilityHolder;
import net.minecraft.world.chunk.WorldChunk;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(WorldChunk.class)
public class WorldChunkMixin implements FabricChunkAccess {
    @Unique private final CapabilityHolder capabilityHolder = new CapabilityHolder();

    @Override
    public @Nullable CapabilityHolder flashfreeze$getCapabilityHolder() {
        return capabilityHolder;
    }
}
