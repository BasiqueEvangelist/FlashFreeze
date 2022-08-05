package me.basiqueevangelist.flashfreeze;

import com.mojang.datafixers.util.Either;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;

import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

public record UnknownBiome(Identifier id) implements UnknownReplacer, RegistryEntry<Biome> {
    @Override
    public RegistryEntry<Biome> toReal() {
        return FlashFreeze.SERVER.get().getRegistryManager().get(Registry.BIOME_KEY).getEntry(BiomeKeys.THE_VOID).get();
    }

    @Override
    public Biome value() {
        return toReal().value();
    }

    @Override
    public boolean hasKeyAndValue() {
        return toReal().hasKeyAndValue();
    }

    @Override
    public boolean matchesId(Identifier id) {
        return toReal().matchesId(id);
    }

    @Override
    public boolean matchesKey(RegistryKey<Biome> key) {
        return toReal().matchesKey(key);
    }

    @Override
    public boolean matches(Predicate<RegistryKey<Biome>> predicate) {
        return toReal().matches(predicate);
    }

    @Override
    public boolean isIn(TagKey<Biome> tag) {
        return toReal().isIn(tag);
    }

    @Override
    public Stream<TagKey<Biome>> streamTags() {
        return toReal().streamTags();
    }

    @Override
    public Either<RegistryKey<Biome>, Biome> getKeyOrValue() {
        return toReal().getKeyOrValue();
    }

    @Override
    public Optional<RegistryKey<Biome>> getKey() {
        return toReal().getKey();
    }

    @Override
    public Type getType() {
        return toReal().getType();
    }

    @Override
    public boolean matchesRegistry(Registry<Biome> registry) {
        return toReal().matchesRegistry(registry);
    }
}
