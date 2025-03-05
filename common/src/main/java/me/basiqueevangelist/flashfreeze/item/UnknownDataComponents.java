package me.basiqueevangelist.flashfreeze.item;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.Tag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public record UnknownDataComponents(Map<ResourceLocation, Optional<Tag>> components) {
    public static final StreamCodec<RegistryFriendlyByteBuf, UnknownDataComponents> PACKET_CODEC = ByteBufCodecs.<ByteBuf, ResourceLocation, Optional<Tag>, Map<ResourceLocation, Optional<Tag>>>map(
        HashMap::new,
        ResourceLocation.STREAM_CODEC,
        ByteBufCodecs.optional(ByteBufCodecs.TAG)
    ).map(UnknownDataComponents::new, UnknownDataComponents::components).cast();
}
