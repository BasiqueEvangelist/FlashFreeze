package me.basiqueevangelist.flashfreeze.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;

public class FlashFreezeCodecs {
    public static Codec<Tag> NBT_ELEMENT = Codec.PASSTHROUGH
        .comapFlatMap(
            dynamic -> {
                Tag nbtElement = dynamic.convert(NbtOps.INSTANCE).getValue();
                return DataResult.success(nbtElement == dynamic.getValue() ? nbtElement.copy() : nbtElement);
            },
            nbt -> new Dynamic<>(NbtOps.INSTANCE, nbt.copy())
        );
}
