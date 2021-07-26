package me.basiqueevangelist.flashfreeze.util;

import dev.architectury.injectables.annotations.ExpectPlatform;

public final class Platform {
    private Platform() {

    }

    @ExpectPlatform
    public static boolean isFabricModLoaded(String modid) {
        throw new AssertionError();
    }
}
