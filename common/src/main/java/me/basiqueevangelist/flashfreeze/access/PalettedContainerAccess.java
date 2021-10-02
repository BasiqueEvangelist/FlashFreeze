package me.basiqueevangelist.flashfreeze.access;

import me.basiqueevangelist.flashfreeze.UnknownBlockState;
import org.jetbrains.annotations.Nullable;

public interface PalettedContainerAccess {
    @Nullable UnknownBlockState getUnknown(int x, int y, int z);
}
