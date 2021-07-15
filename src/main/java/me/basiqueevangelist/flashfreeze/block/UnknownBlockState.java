package me.basiqueevangelist.flashfreeze.block;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

public class UnknownBlockState {
    private final Identifier blockId;
    private final Map<String, String> properties;

    public UnknownBlockState(NbtCompound tag) {
        blockId = new Identifier(tag.getString("Name"));
        properties = new HashMap<>();
        if (tag.contains("Properties", NbtElement.COMPOUND_TYPE)) {
            NbtCompound propertiesTag = tag.getCompound("Properties");

            for (String key : propertiesTag.getKeys()) {
                properties.put(key, propertiesTag.getString(key));
            }
        }
    }

    public Identifier getBlockId() {
        return blockId;
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    public NbtCompound toTag() {
        NbtCompound tag = new NbtCompound();
        tag.putString("Name", blockId.toString());
        NbtCompound propsTag = new NbtCompound();
        for (Map.Entry<String, String> entry : properties.entrySet()) {
            propsTag.putString(entry.getKey(), entry.getValue());
        }
        tag.put("Properties", propsTag);
        return tag;
    }
}
