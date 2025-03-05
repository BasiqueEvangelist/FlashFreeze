package me.basiqueevangelist.flashfreeze.capabilities;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public class CapabilityHolder {
    private final Map<ResourceLocation, Tag> capabilities = new HashMap<>();

    public void fromTag(CompoundTag tag) {
        capabilities.clear();

        if (tag.contains("ForgeCaps", Tag.TAG_COMPOUND)) {
            CompoundTag componentMap = tag.getCompound("ForgeCaps");
            for (String key : componentMap.getAllKeys()) {
                ResourceLocation componentId = ResourceLocation.parse(key);
                capabilities.put(componentId, componentMap.get(key));
            }
        }
    }

    public void toTag(CompoundTag tag) {
        CompoundTag capabilityMap = new CompoundTag();
        for (Map.Entry<ResourceLocation, Tag> entry : capabilities.entrySet()) {
            capabilityMap.put(entry.getKey().toString(), entry.getValue());
        }
        if (!capabilityMap.isEmpty())
            tag.put("ForgeCaps", capabilityMap);
    }
}
