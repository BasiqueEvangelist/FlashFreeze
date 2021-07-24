package me.basiqueevangelist.flashfreeze.fabric;

import me.basiqueevangelist.flashfreeze.FlashFreeze;
import net.fabricmc.api.ModInitializer;

public class FlashFreezeFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        FlashFreeze.init();
    }
}
