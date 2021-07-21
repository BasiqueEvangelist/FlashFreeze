package me.basiqueevangelist.flashfreeze.chunk;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface FakeChunk {
    static boolean isPosFake(World world, BlockPos pos) {
        if (pos.getY() < 0 || pos.getY() >= 16)
            return false;
        return world.getChunk(pos) instanceof FakeChunk;
    }

    NbtCompound getUpdatedTag();
}
