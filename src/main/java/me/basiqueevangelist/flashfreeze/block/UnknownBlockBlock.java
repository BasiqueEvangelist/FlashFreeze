package me.basiqueevangelist.flashfreeze.block;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

public class UnknownBlockBlock extends Block {
    public UnknownBlockBlock() {
        super(Settings.copy(Blocks.BEDROCK));
    }
}
