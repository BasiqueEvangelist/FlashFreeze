package me.basiqueevangelist.flashfreeze.config.clothconfig;

import me.basiqueevangelist.flashfreeze.config.FlashFreezeConfig;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.ConfigHolder;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.minecraft.client.gui.screen.Screen;

public class FlashFreezeConfigImpl implements FlashFreezeConfig {
    private static final ConfigHolder<FFConfigData> CONFIG = AutoConfig.register(FFConfigData.class, JanksonConfigSerializer::new);

    public static Screen createScreen(Screen parent) {
        return AutoConfig.getConfigScreen(FFConfigData.class, parent).get();
    }

    @Override
    public boolean showWarningScreen() {
        return CONFIG.getConfig().showWarningScreen;
    }

    @Config(name = "flashfreeze")
    public static class FFConfigData implements ConfigData {
        public boolean showWarningScreen = true;

    }
}
