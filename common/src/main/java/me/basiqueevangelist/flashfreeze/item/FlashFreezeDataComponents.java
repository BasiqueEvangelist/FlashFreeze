package me.basiqueevangelist.flashfreeze.item;

import me.basiqueevangelist.flashfreeze.FlashFreeze;
import me.basiqueevangelist.flashfreeze.util.FlashFreezePlatform;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;

public class FlashFreezeDataComponents {
    public static final DataComponentType<ResourceLocation> ORIGINAL_ITEM_ID = DataComponentType.<ResourceLocation>builder()
        .networkSynchronized(ResourceLocation.STREAM_CODEC)
        .build();

    public static final DataComponentType<UnknownDataComponents> UNKNOWN_DATA_COMPONENTS = DataComponentType.<UnknownDataComponents>builder()
        .networkSynchronized(UnknownDataComponents.PACKET_CODEC)
        .build();

    public static void init() {
        FlashFreezePlatform.I.register(Registries.DATA_COMPONENT_TYPE, FlashFreeze.id("original_item_id"), () -> ORIGINAL_ITEM_ID);
        FlashFreezePlatform.I.register(Registries.DATA_COMPONENT_TYPE, FlashFreeze.id("unknown_data_components"), () -> UNKNOWN_DATA_COMPONENTS);
    }
}
