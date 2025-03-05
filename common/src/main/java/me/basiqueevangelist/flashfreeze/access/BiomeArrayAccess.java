package me.basiqueevangelist.flashfreeze.access;

public interface BiomeArrayAccess {
    int[] toPlayerIntArray();

    int getUnknownIndexAt(int x, int y, int z);
}
