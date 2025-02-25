package me.basiqueevangelist.flashfreeze.item;

import me.basiqueevangelist.flashfreeze.FlashFreeze;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class FlashFreezeDataComponents {
    public static final ComponentType<Identifier> ORIGINAL_ITEM_ID = ComponentType.<Identifier>builder()
        .packetCodec(Identifier.PACKET_CODEC)
        .build();

    public static final ComponentType<UnknownDataComponents> UNKNOWN_DATA_COMPONENTS = ComponentType.<UnknownDataComponents>builder()
        .packetCodec(UnknownDataComponents.PACKET_CODEC)
        .build();

    public static void init() {
        Registry.register(Registries.DATA_COMPONENT_TYPE, FlashFreeze.id("original_item_id"), ORIGINAL_ITEM_ID);
        Registry.register(Registries.DATA_COMPONENT_TYPE, FlashFreeze.id("unknown_data_components"), UNKNOWN_DATA_COMPONENTS);
    }
}
