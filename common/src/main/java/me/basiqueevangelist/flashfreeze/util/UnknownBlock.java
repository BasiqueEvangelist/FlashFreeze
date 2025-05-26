package me.basiqueevangelist.flashfreeze.util;

import me.basiqueevangelist.flashfreeze.FlashFreeze;
import me.basiqueevangelist.flashfreeze.UnknownReplacer;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

public class UnknownBlock extends Block implements UnknownReplacer {
    private final ResourceLocation id;

    public UnknownBlock(ResourceLocation id) {
        super(Properties.of().setId(ResourceKey.create(Registries.BLOCK, id)));

        this.id = id;
    }

    public ResourceLocation id() {
        return id;
    }

    @Override
    public Object toReal() {
        return FlashFreeze.UNKNOWN_BLOCK;
    }
}
