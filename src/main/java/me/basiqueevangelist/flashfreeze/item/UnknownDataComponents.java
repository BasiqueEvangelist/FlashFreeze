package me.basiqueevangelist.flashfreeze.item;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public record UnknownDataComponents(Map<Identifier, Optional<NbtElement>> components) {
    public static final PacketCodec<RegistryByteBuf, UnknownDataComponents> PACKET_CODEC = PacketCodecs.<ByteBuf, Identifier, Optional<NbtElement>, Map<Identifier, Optional<NbtElement>>>map(
        HashMap::new,
        Identifier.PACKET_CODEC,
        PacketCodecs.optional(PacketCodecs.NBT_ELEMENT)
    ).xmap(UnknownDataComponents::new, UnknownDataComponents::components).cast();
}
