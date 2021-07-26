package me.basiqueevangelist.flashfreeze.util.fabric;

import net.fabricmc.loader.api.FabricLoader;

public class PlatformImpl {
    public static boolean isFabricModLoaded(String modid) {
        return FabricLoader.getInstance().isModLoaded(modid);
    }
}
