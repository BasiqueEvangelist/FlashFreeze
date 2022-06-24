package me.basiqueevangelist.flashfreeze.block;

import eu.pb4.polymer.api.block.PolymerBlock;
import eu.pb4.polymer.api.client.PolymerClientDecoded;
import eu.pb4.polymer.api.client.PolymerKeepModel;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;

public class UnknownBlockBlock extends Block implements PolymerBlock, PolymerKeepModel, PolymerClientDecoded {
    public UnknownBlockBlock() {
        super(FabricBlockSettings.copyOf(Blocks.BEDROCK));
    }

    @Override
    public Block getPolymerBlock(BlockState state) {
        return Blocks.BEDROCK;
    }
}
