package me.basiqueevangelist.flashfreeze.compat.modmenu;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.basiqueevangelist.flashfreeze.config.clothconfig.FlashFreezeConfigImpl;
import net.fabricmc.loader.api.FabricLoader;

public class FlashFreezeModMenu implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        if (FabricLoader.getInstance().isModLoaded("cloth-config2")) {
            return FlashFreezeConfigImpl::createScreen;
        } else {
            return screen -> null;
        }
    }
}
