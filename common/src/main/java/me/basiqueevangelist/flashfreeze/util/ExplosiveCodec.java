package me.basiqueevangelist.flashfreeze.util;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;

public class ExplosiveCodec implements Codec<Object> {
    private static final ExplosiveCodec INSTANCE = new ExplosiveCodec();

    private ExplosiveCodec() {

    }

    @SuppressWarnings("unchecked")
    public static <T> Codec<T> of() {
        return (Codec<T>) INSTANCE;
    }

    @Override
    public <T> DataResult<Pair<Object, T>> decode(DynamicOps<T> ops, T input) {
        throw new UnsupportedOperationException("Un-decodable type decoded");
    }

    @Override
    public <T> DataResult<T> encode(Object input, DynamicOps<T> ops, T prefix) {
        throw new UnsupportedOperationException("Un-encodable type encoded.");
    }
}
