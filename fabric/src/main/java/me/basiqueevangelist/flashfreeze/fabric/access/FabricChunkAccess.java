package me.basiqueevangelist.flashfreeze.fabric.access;

import me.basiqueevangelist.flashfreeze.fabric.capabilities.CapabilityHolder;
import org.jetbrains.annotations.Nullable;

public interface FabricChunkAccess {
    @Nullable CapabilityHolder flashfreeze$getCapabilityHolder();
}
