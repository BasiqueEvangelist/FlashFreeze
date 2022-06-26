package me.basiqueevangelist.flashfreeze;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public record UnknownBlockState(Identifier blockId, Map<String, String> properties) implements UnknownReplacer {
    public static UnknownBlockState fromTag(NbtCompound tag) {
        Identifier blockId = new Identifier(tag.getString("Name"));
        Map<String, String> properties = new HashMap<>();

        if (tag.contains("Properties", NbtElement.COMPOUND_TYPE)) {
            NbtCompound propertiesTag = tag.getCompound("Properties");

            for (String key : propertiesTag.getKeys()) {
                properties.put(key, propertiesTag.getString(key));
            }
        }

        return new UnknownBlockState(blockId, properties);
    }

    public NbtCompound toTag(NbtCompound tag) {
        tag.putString("Name", blockId.toString());
        NbtCompound propsTag = new NbtCompound();
        for (Map.Entry<String, String> entry : properties.entrySet()) {
            propsTag.putString(entry.getKey(), entry.getValue());
        }
        tag.put("Properties", propsTag);
        return tag;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(blockId);

        if (!properties.isEmpty()) {
            boolean isFirst = true;

            sb.append('[');

            for (Map.Entry<String, String> entry : properties.entrySet()) {
                if (!isFirst)
                    sb.append(',');

                isFirst = false;

                sb.append(entry.getKey()).append('=').append(entry.getValue());
            }

            sb.append(']');
        }

        return sb.toString();
    }

    @Override
    public Object toReal() {
        return FlashFreeze.UNKNOWN_BLOCK.getDefaultState();
    }
}
