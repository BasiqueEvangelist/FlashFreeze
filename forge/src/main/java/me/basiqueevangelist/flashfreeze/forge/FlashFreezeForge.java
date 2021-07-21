package me.basiqueevangelist.flashfreeze.forge;

import me.basiqueevangelist.flashfreeze.FlashFreeze;
import net.minecraftforge.fml.ExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.FMLNetworkConstants;
import org.apache.commons.lang3.tuple.Pair;

@Mod(FlashFreeze.MODID)
public class FlashFreezeForge {
    public FlashFreezeForge() {
        ModLoadingContext.get().registerExtensionPoint(ExtensionPoint.DISPLAYTEST, () -> Pair.of(() -> FMLNetworkConstants.IGNORESERVERONLY, (a, b) -> true));

        FlashFreeze.init();
    }
}
