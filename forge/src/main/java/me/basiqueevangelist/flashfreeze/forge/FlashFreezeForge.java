package me.basiqueevangelist.flashfreeze.forge;

import me.basiqueevangelist.flashfreeze.FlashFreeze;
import me.basiqueevangelist.flashfreeze.config.clothconfig.FlashFreezeConfigImpl;
import me.basiqueevangelist.flashfreeze.util.forge.FFPlatformImpl;
import net.minecraftforge.client.ConfigGuiHandler;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.IExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.NetworkConstants;

import java.lang.ref.WeakReference;

@Mod(FlashFreeze.MODID)
@Mod.EventBusSubscriber(modid = FlashFreeze.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class FlashFreezeForge {
    public FlashFreezeForge() {
        ModLoadingContext.get().registerExtensionPoint(IExtensionPoint.DisplayTest.class, () -> new IExtensionPoint.DisplayTest(() -> NetworkConstants.IGNORESERVERONLY, (a, b) -> true));

        FlashFreeze.init();

        if (FFPlatformImpl.isClothConfigPresent()) {
            ModLoadingContext.get().registerExtensionPoint(ConfigGuiHandler.ConfigGuiFactory.class, () -> new ConfigGuiHandler.ConfigGuiFactory((mc, parent) -> FlashFreezeConfigImpl.createScreen(parent)));
        }
    }

    @SubscribeEvent
    public static void onServerStarting(ServerStartingEvent event) {
        FlashFreeze.SERVER = new WeakReference<>(event.getServer());
    }
}