package me.basiqueevangelist.palettebypass;

import net.minecraft.util.Identifier;

public interface BiomeArrayAccess {
    int[] toPlayerIntArray();

    int getUnknownIndexAt(int x, int y, int z);

    default Identifier getUnknownAt(int x, int y, int z) {
        return FabricDynamicRegistryData.getBiomeForId(getUnknownIndexAt(x, y, z));
    }
}
