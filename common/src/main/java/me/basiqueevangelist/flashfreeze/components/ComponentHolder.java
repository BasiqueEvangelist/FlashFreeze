package me.basiqueevangelist.flashfreeze.components;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

// Used if the needed CCA component is not present.
public class ComponentHolder {
    public ComponentHolder() {

    }

    private final Map<ResourceLocation, CompoundTag> components = new HashMap<>();

    public void fromTag(CompoundTag tag) {
        components.clear();

        var listOpt = tag.getList("cardinal_components");
        if (listOpt.isPresent()) {
            ListTag list = listOpt.get();
            for (int i = 0; i < list.size(); i++) {
                CompoundTag origComponentTag = list.getCompound(i).orElseThrow();
                ResourceLocation componentId = ResourceLocation.parse(origComponentTag.getString("componentId").orElseThrow());
                CompoundTag componentTag = origComponentTag.copy();
                componentTag.remove("componentId");
                components.put(componentId, componentTag);
            }
        } else {
            var componentMapOpt = tag.getCompound("cardinal_components");

            if (componentMapOpt.isPresent()) {
                CompoundTag componentMap = componentMapOpt.get();
                for (String key : componentMap.keySet()) {
                    ResourceLocation componentId = ResourceLocation.parse(key);
                    components.put(componentId, componentMap.getCompound(key).orElseThrow());
                }
            }
        }
    }

    public void toTag(CompoundTag tag) {
        CompoundTag componentMap = new CompoundTag();
        for (Map.Entry<ResourceLocation, CompoundTag> entry : components.entrySet()) {
            componentMap.put(entry.getKey().toString(), entry.getValue());
        }
        if (!componentMap.isEmpty())
            tag.put("cardinal_components", componentMap);
    }
}
