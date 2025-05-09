package me.basiqueevangelist.flashfreeze.block;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class UnknownBlockBlock extends Block {
    public UnknownBlockBlock(ResourceKey<Block> id) {
        super(Properties.ofFullCopy(Blocks.BEDROCK).setId(id));
    }
}
