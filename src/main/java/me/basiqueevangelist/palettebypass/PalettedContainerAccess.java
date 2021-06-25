package me.basiqueevangelist.palettebypass;

import org.jetbrains.annotations.Nullable;

public interface PalettedContainerAccess {
    @Nullable UnknownBlockState getUnknown(int x, int y, int z);
}
