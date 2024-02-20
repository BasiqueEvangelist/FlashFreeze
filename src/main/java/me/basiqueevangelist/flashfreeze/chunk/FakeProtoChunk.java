package me.basiqueevangelist.flashfreeze.chunk;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registry;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.HeightLimitView;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ProtoChunk;
import net.minecraft.world.chunk.UpgradeData;

import java.util.Collections;
import java.util.Set;

public class FakeProtoChunk extends ProtoChunk implements FakeChunk {
    private final NbtCompound updatedTag;

    public FakeProtoChunk(ChunkPos pos, HeightLimitView world, Registry<Biome> biomes, NbtCompound updatedTag) {
        super(pos, UpgradeData.NO_UPGRADE_DATA, world, biomes, null);
        this.updatedTag = updatedTag;
    }

    @Override
    public BlockState getBlockState(BlockPos pos) {
        return Blocks.BEDROCK.getDefaultState();
    }

    @Override
    public FluidState getFluidState(BlockPos pos) {
        return Fluids.EMPTY.getDefaultState();
    }

    @Override
    public BlockState setBlockState(BlockPos pos, BlockState state, boolean moved) {
        return Blocks.BEDROCK.getDefaultState();
    }

    @Override
    public void setBlockEntity(BlockEntity blockEntity) { }

    @Override
    public Set<BlockPos> getBlockEntityPositions() {
        return Collections.emptySet();
    }

    @Override
    public NbtCompound getUpdatedTag() {
        return updatedTag;
    }
}
