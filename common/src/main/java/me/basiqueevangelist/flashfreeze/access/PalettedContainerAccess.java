package me.basiqueevangelist.flashfreeze.access;

import me.basiqueevangelist.flashfreeze.UnknownReplacer;

public interface PalettedContainerAccess {
    UnknownReplacer getUnknown(int x, int y, int z);
}
