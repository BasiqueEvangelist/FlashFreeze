package me.basiqueevangelist.flashfreeze.util.forge;

import net.minecraftforge.fml.ModList;

public class PlatformImpl {
    public static boolean isFabricModLoaded(String modid) {
        return false;
    }

    public static boolean isClothConfigPresent() {
        return ModList.get().isLoaded("cloth-config");
    }
}
