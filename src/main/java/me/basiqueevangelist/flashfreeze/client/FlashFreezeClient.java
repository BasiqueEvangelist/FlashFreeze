package me.basiqueevangelist.flashfreeze.client;

import me.basiqueevangelist.flashfreeze.FlashFreeze;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import org.slf4j.LoggerFactory;

public class FlashFreezeClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientPlayNetworking.registerGlobalReceiver(FlashFreeze.MALDENHAGEN, (client, handler, buf, responseSender) -> {
            LoggerFactory.getLogger("FlashFreeze").warn("Why are you malding?");
        });
    }
}
