package me.basiqueevangelist.flashfreeze;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;

import java.util.HashMap;
import java.util.Map;

public record UnknownBlockState(ResourceLocation blockId, Map<String, String> properties) implements UnknownReplacer {
    public static UnknownBlockState fromTag(CompoundTag tag) {
        ResourceLocation blockId = ResourceLocation.parse(tag.getString("Name").orElseThrow());
        Map<String, String> properties = new HashMap<>();

        var propertiesOpt = tag.getCompound("Properties");
        if (propertiesOpt.isPresent()) {
            CompoundTag propertiesTag = propertiesOpt.get();

            for (String key : propertiesTag.keySet()) {
                properties.put(key, propertiesTag.getString(key).orElseThrow());
            }
        }

        return new UnknownBlockState(blockId, properties);
    }

    public CompoundTag toTag(CompoundTag tag) {
        tag.putString("Name", blockId.toString());
        CompoundTag propsTag = new CompoundTag();
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
    public BlockState toReal() {
        return FlashFreeze.UNKNOWN_BLOCK.get().defaultBlockState();
    }
}
