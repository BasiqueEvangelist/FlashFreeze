package me.basiqueevangelist.flashfreeze;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;

public final class FabricDynamicRegistryData {
    private FabricDynamicRegistryData() {

    }

    public static void setRegistryData(NbtCompound registryData) {
        NbtCompound biomeTag = registryData.getCompound("registries").getCompound("minecraft:worldgen/biome");
        biomeMap.clear();
        for (String key : biomeTag.getKeys()) {
            biomeMap.put(new Identifier(key), biomeTag.getInt(key));
        }
    }

    private static BiMap<Identifier, Integer> biomeMap = HashBiMap.create();

    public static int getIdForBiome(Identifier biomeId) {
        return biomeMap.get(biomeId);
    }

    public static Identifier getBiomeForId(int id) {
        return biomeMap.inverse().get(id);
    }
}
