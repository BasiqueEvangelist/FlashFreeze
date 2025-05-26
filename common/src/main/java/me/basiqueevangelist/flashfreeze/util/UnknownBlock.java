package me.basiqueevangelist.flashfreeze.util;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import me.basiqueevangelist.flashfreeze.FlashFreeze;
import me.basiqueevangelist.flashfreeze.UnknownReplacer;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

public class UnknownBlock extends Block implements UnknownReplacer {
    private static final LoadingCache<ResourceLocation, UnknownBlock> CACHE = CacheBuilder.newBuilder()
        .softValues()
        .build(CacheLoader.from(UnknownBlock::new));

    private final ResourceLocation id;

    private UnknownBlock(ResourceLocation id) {
        super(Properties.of().setId(ResourceKey.create(Registries.BLOCK, id)));

        this.id = id;
    }

    public static UnknownBlock get(ResourceLocation id) {
        return CACHE.getUnchecked(id);
    }

    public ResourceLocation id() {
        return id;
    }

    @Override
    public Object toReal() {
        return FlashFreeze.UNKNOWN_BLOCK;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UnknownBlock that = (UnknownBlock) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
