package me.basiqueevangelist.flashfreeze.compat.fabric.modmenu;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.basiqueevangelist.flashfreeze.config.clothconfig.FlashFreezeConfigImpl;
import me.basiqueevangelist.flashfreeze.util.fabric.FFPlatformImpl;

public class FlashFreezeModMenu implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        if (FFPlatformImpl.isClothConfigPresent()) {
            return FlashFreezeConfigImpl::createScreen;
        } else {
            return screen -> null;
        }
    }
}
