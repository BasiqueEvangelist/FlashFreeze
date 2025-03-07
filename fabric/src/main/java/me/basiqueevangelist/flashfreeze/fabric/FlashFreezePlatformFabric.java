package me.basiqueevangelist.flashfreeze.fabric;

import me.basiqueevangelist.flashfreeze.util.FlashFreezePlatform;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Supplier;

public class FlashFreezePlatformFabric implements FlashFreezePlatform {
    @SuppressWarnings("unchecked")
    @Override
    public <T> Supplier<T> register(ResourceKey<? extends Registry<?>> registryKey, ResourceLocation id, Supplier<T> supplier) {
        T instance = supplier.get();

        Registry.register((Registry<? super T>) BuiltInRegistries.REGISTRY.get(registryKey.location()), id, instance);

        return () -> instance;
    }

    @Override
    public boolean isModLoaded(String id) {
        return FabricLoader.getInstance().isModLoaded(id);
    }
}
