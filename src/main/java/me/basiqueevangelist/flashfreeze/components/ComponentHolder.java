package me.basiqueevangelist.flashfreeze.components;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

// Used if the needed CCA component is not present.
public class ComponentHolder {
    public ComponentHolder() {

    }

    private final Map<Identifier, NbtCompound> components = new HashMap<>();

    public void fromTag(NbtCompound tag) {
        components.clear();

        if (tag.contains("cardinal_components", NbtElement.LIST_TYPE)) {
            NbtList list = (NbtList) tag.get("cardinal_components");
            for (int i = 0; i < list.size(); i++) {
                NbtCompound origComponentTag = list.getCompound(i);
                Identifier componentId = new Identifier(origComponentTag.getString("componentId"));
                NbtCompound componentTag = origComponentTag.copy();
                componentTag.remove("componentId");
                components.put(componentId, componentTag);
            }
        } else if (tag.contains("cardinal_components", NbtElement.COMPOUND_TYPE)) {
            NbtCompound componentMap = tag.getCompound("cardinal_components");
            for (String key : componentMap.getKeys()) {
                Identifier componentId = new Identifier(key);
                components.put(componentId, componentMap.getCompound(key));
            }
        }
    }

    public void toTag(NbtCompound tag) {
        NbtCompound componentMap = new NbtCompound();
        for (Map.Entry<Identifier, NbtCompound> entry : components.entrySet()) {
            componentMap.put(entry.getKey().toString(), entry.getValue());
        }
        if (!componentMap.isEmpty())
            tag.put("cardinal_components", componentMap);
    }
}
