package me.basiqueevangelist.flashfreeze.components;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;

// Used if the needed CCA component is not present.
public class ComponentHolder {
    public ComponentHolder() {

    }

    private final Map<ResourceLocation, CompoundTag> components = new HashMap<>();

    public void fromTag(CompoundTag tag) {
        components.clear();

        if (tag.contains("cardinal_components", Tag.TAG_LIST)) {
            ListTag list = (ListTag) tag.get("cardinal_components");
            for (int i = 0; i < list.size(); i++) {
                CompoundTag origComponentTag = list.getCompound(i);
                ResourceLocation componentId = ResourceLocation.parse(origComponentTag.getString("componentId"));
                CompoundTag componentTag = origComponentTag.copy();
                componentTag.remove("componentId");
                components.put(componentId, componentTag);
            }
        } else if (tag.contains("cardinal_components", Tag.TAG_COMPOUND)) {
            CompoundTag componentMap = tag.getCompound("cardinal_components");
            for (String key : componentMap.getAllKeys()) {
                ResourceLocation componentId = ResourceLocation.parse(key);
                components.put(componentId, componentMap.getCompound(key));
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
