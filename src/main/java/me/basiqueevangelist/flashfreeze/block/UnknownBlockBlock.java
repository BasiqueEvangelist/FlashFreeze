package me.basiqueevangelist.flashfreeze.block;

import eu.pb4.polymer.api.block.PolymerBlock;
import eu.pb4.polymer.api.client.PolymerClientDecoded;
import eu.pb4.polymer.api.client.PolymerKeepModel;
import me.basiqueevangelist.flashfreeze.FlashFreeze;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.server.network.ServerPlayerEntity;

public class UnknownBlockBlock extends Block implements PolymerBlock, PolymerKeepModel, PolymerClientDecoded {
    public UnknownBlockBlock() {
        super(FabricBlockSettings.copyOf(Blocks.BEDROCK));
    }

    @Override
    public Block getPolymerBlock(BlockState state) {
        return Blocks.BEDROCK;
    }

    @Override
    public Block getPolymerBlock(ServerPlayerEntity player, BlockState state) {
        if (player != null && ServerPlayNetworking.canSend(player, FlashFreeze.MALDENHAGEN)) {
            return this;
        }

        return Blocks.BEDROCK;
    }

    @Override
    public BlockState getPolymerBlockState(ServerPlayerEntity player, BlockState state) {
        if (player != null && ServerPlayNetworking.canSend(player, FlashFreeze.MALDENHAGEN)) {
            return this.getDefaultState();
        }

        return Blocks.BEDROCK.getDefaultState();
    }
}
