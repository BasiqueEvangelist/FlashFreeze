package me.basiqueevangelist.flashfreeze.util;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import java.util.ServiceLoader;
import java.util.function.Supplier;

public interface FlashFreezePlatform {
    FlashFreezePlatform I = ServiceLoader.load(FlashFreezePlatform.class).findFirst().get();

    <T> Supplier<T> register(ResourceKey<? extends Registry<?>> registryKey, ResourceLocation id, Supplier<T> supplier);

    boolean isModLoaded(String id);
}
