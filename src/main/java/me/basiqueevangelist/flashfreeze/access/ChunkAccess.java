package me.basiqueevangelist.flashfreeze.access;

import me.basiqueevangelist.flashfreeze.capabilities.CapabilityHolder;
import me.basiqueevangelist.flashfreeze.components.ComponentHolder;

public interface ChunkAccess {
    ComponentHolder flashfreeze$getComponentHolder();
    CapabilityHolder flashfreeze$getCapabilityHolder();
}
