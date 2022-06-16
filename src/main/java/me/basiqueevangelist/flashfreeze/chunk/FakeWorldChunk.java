package me.basiqueevangelist.flashfreeze.chunk;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkSection;
import net.minecraft.world.chunk.WorldChunk;

public class FakeWorldChunk extends WorldChunk implements FakeChunk {
    private final NbtCompound updatedTag;

    public FakeWorldChunk(World world, ChunkPos pos, NbtCompound updatedTag) {
        super(world, pos);
        this.updatedTag = updatedTag;

        for (int i = 0; i < world.countVerticalSections(); i++) {
            ChunkSection section = new ChunkSection(world.sectionIndexToCoord(i), world.getRegistryManager().get(Registry.BIOME_KEY));
            for (int x = 0; x < 16; x++)
                for (int y = 0; y < 16; y++)
                    for (int z = 0; z < 16; z++)
                        section.setBlockState(x, y, z, Blocks.BEDROCK.getDefaultState());
            getSectionArray()[i] = section;
        }
    }

    @Override
    public BlockEntity getBlockEntity(BlockPos pos, CreationType creationType) {
        return null;
    }

    @Override
    public BlockState getBlockState(BlockPos pos) {
        int sectionIndex = this.getSectionIndex(pos.getY());
        if (sectionIndex < 0 || sectionIndex >= getSectionArray().length)
            return Blocks.AIR.getDefaultState();

        return Blocks.BEDROCK.getDefaultState();
    }

    @Override
    public BlockState setBlockState(BlockPos pos, BlockState state, boolean moved) {
        return Blocks.BEDROCK.getDefaultState();
    }

    public NbtCompound getUpdatedTag() {
        return updatedTag;
    }

    @Override
    public void setBlockEntity(BlockEntity blockEntity) { }
}
