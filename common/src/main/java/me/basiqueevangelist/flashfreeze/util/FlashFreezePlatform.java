package me.basiqueevangelist.flashfreeze.util;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import java.util.ServiceLoader;
import java.util.function.Function;
import java.util.function.Supplier;

public interface FlashFreezePlatform {
    FlashFreezePlatform I = ServiceLoader.load(FlashFreezePlatform.class).findFirst().get();

    <T> Supplier<T> register(ResourceKey<? extends Registry<?>> registryKey, ResourceLocation id, Supplier<T> supplier);

    default <T extends K, K> Supplier<T> registerWithKey(ResourceKey<? extends Registry<K>> registryKey, ResourceLocation id, Function<ResourceKey<K>, T> func) {
        var key = ResourceKey.create(registryKey, id);
        return register(registryKey, id, () -> func.apply(key));
    }

    boolean isModLoaded(String id);
}
