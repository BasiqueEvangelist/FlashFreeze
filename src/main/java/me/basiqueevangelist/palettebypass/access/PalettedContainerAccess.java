package me.basiqueevangelist.palettebypass.access;

import me.basiqueevangelist.palettebypass.UnknownBlockState;
import org.jetbrains.annotations.Nullable;

public interface PalettedContainerAccess {
    @Nullable UnknownBlockState getUnknown(int x, int y, int z);
}
