package me.basiqueevangelist.flashfreeze.forge;

import me.basiqueevangelist.flashfreeze.FlashFreeze;
import me.basiqueevangelist.flashfreeze.config.clothconfig.FlashFreezeConfigImpl;
import me.basiqueevangelist.flashfreeze.util.forge.FFPlatformImpl;
import net.minecraftforge.fml.IExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fmlclient.ConfigGuiHandler;
import net.minecraftforge.fmllegacy.network.FMLNetworkConstants;

@Mod(FlashFreeze.MODID)
public class FlashFreezeForge {
    public FlashFreezeForge() {
        ModLoadingContext.get().registerExtensionPoint(IExtensionPoint.DisplayTest.class, () -> new IExtensionPoint.DisplayTest(() -> FMLNetworkConstants.IGNORESERVERONLY, (a, b) -> true));

        FlashFreeze.init();

        if (FFPlatformImpl.isClothConfigPresent()) {
            ModLoadingContext.get().registerExtensionPoint(ConfigGuiHandler.ConfigGuiFactory.class, () -> new ConfigGuiHandler.ConfigGuiFactory((mc, parent) -> FlashFreezeConfigImpl.createScreen(parent)));
        }
    }
}