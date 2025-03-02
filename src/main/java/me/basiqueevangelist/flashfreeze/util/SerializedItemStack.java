package me.basiqueevangelist.flashfreeze.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.basiqueevangelist.flashfreeze.item.FlashFreezeDataComponents;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.PatchedDataComponentMap;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.ItemStack;

public record SerializedItemStack(ResourceLocation id, int count, DataComponentPatch components) {
    public static final Codec<SerializedItemStack> CODEC = Codec.lazyInitialized(
        () -> RecordCodecBuilder.create(
            instance -> instance.group(
                    ResourceLocation.CODEC.fieldOf("id").forGetter(SerializedItemStack::id),
                    ExtraCodecs.POSITIVE_INT.fieldOf("count").orElse(1).forGetter(SerializedItemStack::count),
                    DataComponentPatch.CODEC.optionalFieldOf("components", DataComponentPatch.EMPTY).forGetter(SerializedItemStack::components)
                )
                .apply(instance, SerializedItemStack::new)
        )
    );
    public static final Codec<SerializedItemStack> UNCOUNTED_CODEC = Codec.lazyInitialized(
        () -> RecordCodecBuilder.create(
            instance -> instance.group(
                    ResourceLocation.CODEC.fieldOf("id").forGetter(SerializedItemStack::id),
                    DataComponentPatch.CODEC.optionalFieldOf("components", DataComponentPatch.EMPTY).forGetter(SerializedItemStack::components)
                )
                .apply(instance, (item, components) -> new SerializedItemStack(item, 1, components))
        )
    );

    public static SerializedItemStack from(ItemStack stack) {
        ResourceLocation itemId = stack.getOrDefault(FlashFreezeDataComponents.ORIGINAL_ITEM_ID, BuiltInRegistries.ITEM.getKey(stack.getItem()));
        DataComponentPatch changes;

        if (stack.has(FlashFreezeDataComponents.ORIGINAL_ITEM_ID)) {
            var copiedComponents = ((PatchedDataComponentMap) stack.getComponents()).copy();
            copiedComponents.remove(FlashFreezeDataComponents.ORIGINAL_ITEM_ID);
            changes = copiedComponents.asPatch();
        } else {
            changes = stack.getComponentsPatch();
        }

        return new SerializedItemStack(itemId, stack.getCount(), changes);
    }

    public DataResult<SerializedItemStack> validateUnknown() {
        if (BuiltInRegistries.ITEM.containsKey(id()))
            return DataResult.error(() -> "Item '" + id + "' is present in registry");

        return DataResult.success(this);
    }
}
