package me.basiqueevangelist.palettebypass;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface FakeChunk {
    static boolean isPosFake(World world, BlockPos pos) {
        int sectionIndex = world.getSectionIndex(pos.getY());
        if (sectionIndex < 0 || sectionIndex >= world.countVerticalSections())
            return false;
        return world.getChunk(pos) instanceof FakeChunk;
    }

    NbtCompound getUpdatedTag();
}
