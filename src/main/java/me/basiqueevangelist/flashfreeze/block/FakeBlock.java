package me.basiqueevangelist.flashfreeze.block;

import eu.pb4.polymer.block.VirtualBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

public class FakeBlock extends Block implements VirtualBlock {
    public FakeBlock(Settings settings) {
        super(settings);
    }

    @Override
    public Block getVirtualBlock() {
        return Blocks.BEDROCK;
    }
}
