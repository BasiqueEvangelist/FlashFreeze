package me.basiqueevangelist.flashfreeze.util;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;

public record AlternativeCodec<A>(Codec<A> main, Codec<A> alternative) implements Codec<A> {
    @Override
    public <T> DataResult<Pair<A, T>> decode(DynamicOps<T> ops, T input) {
        final DataResult<Pair<A, T>> mainRead = main.decode(ops, input);

        if (mainRead.isSuccess()) return mainRead;

        final DataResult<Pair<A, T>> altRead = alternative.decode(ops, input);

        if (altRead.isSuccess()) return altRead;

        return mainRead.apply2((a, b) -> b, altRead);
    }

    @Override
    public <T> DataResult<T> encode(A input, DynamicOps<T> ops, T prefix) {
        final DataResult<T> mainWrite = main.encode(input, ops, prefix);

        if (mainWrite.isSuccess()) return mainWrite;

        final DataResult<T> altWrite = alternative.encode(input, ops, prefix);

        if (altWrite.isSuccess()) return altWrite;

        return mainWrite.apply2((a, b) -> b, altWrite);
    }
}
