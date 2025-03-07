package me.basiqueevangelist.flashfreeze.fabric;

import me.basiqueevangelist.flashfreeze.FlashFreeze;
import me.basiqueevangelist.flashfreeze.command.LookupCommand;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;

import java.lang.ref.WeakReference;

public class FlashFreezeFabric implements ModInitializer {
    
    @Override
    public void onInitialize() {
        ServerLifecycleEvents.SERVER_STARTING.register(server -> {
            FlashFreeze.SERVER = new WeakReference<>(server);
        });

        CommandRegistrationCallback.EVENT.register(LookupCommand::register);

        FlashFreeze.init();
    }
}
