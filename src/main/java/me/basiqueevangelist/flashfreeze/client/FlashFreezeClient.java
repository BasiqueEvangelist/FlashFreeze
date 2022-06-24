package me.basiqueevangelist.flashfreeze.client;

import me.basiqueevangelist.flashfreeze.FlashFreeze;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import org.apache.logging.log4j.LogManager;

public class FlashFreezeClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientPlayNetworking.registerGlobalReceiver(FlashFreeze.MALDENHAGEN, (client, handler, buf, responseSender) -> {
            LogManager.getLogger("FlashFreeze").warn("Why are you malding?");
        });
    }
}
