package me.basiqueevangelist.flashfreeze.mixin.capabilities;

import me.basiqueevangelist.flashfreeze.access.fabric.FabricChunkAccess;
import me.basiqueevangelist.flashfreeze.capabilities.CapabilityHolder;
import net.minecraft.world.chunk.WorldChunk;
import net.minecraft.world.chunk.WrapperProtoChunk;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(WrapperProtoChunk.class)
public class WrapperProtoChunkMixin implements FabricChunkAccess {
    @Shadow @Final private WorldChunk wrapped;

    @Override
    public CapabilityHolder flashfreeze$getCapabilityHolder() {
        return ((FabricChunkAccess) wrapped).flashfreeze$getCapabilityHolder();
    }
}
