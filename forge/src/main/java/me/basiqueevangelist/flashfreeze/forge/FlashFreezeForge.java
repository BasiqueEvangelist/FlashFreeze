package me.basiqueevangelist.flashfreeze.forge;

import me.basiqueevangelist.flashfreeze.FlashFreeze;
import net.minecraftforge.fml.IExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fmllegacy.network.FMLNetworkConstants;

@Mod(FlashFreeze.MODID)
public class FlashFreezeForge {
    public FlashFreezeForge() {
        ModLoadingContext.get().registerExtensionPoint(IExtensionPoint.DisplayTest.class, () -> new IExtensionPoint.DisplayTest(() -> FMLNetworkConstants.IGNORESERVERONLY, (a, b) -> true));

        FlashFreeze.init();
    }
}