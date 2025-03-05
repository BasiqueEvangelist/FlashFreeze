package me.basiqueevangelist.flashfreeze.neoforge;


import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import me.basiqueevangelist.flashfreeze.FlashFreeze;
import me.basiqueevangelist.flashfreeze.command.LookupCommand;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.registries.RegisterEvent;

import java.lang.ref.WeakReference;

@Mod(FlashFreeze.MODID)
@EventBusSubscriber(modid = FlashFreeze.MODID)
public class FlashFreezeNeoforge {
    public FlashFreezeNeoforge(IEventBus eventBus) {
        FlashFreeze.init();
    }

    @SubscribeEvent
    public static void handleServerStarting(ServerStartingEvent event) {
        FlashFreeze.SERVER = new WeakReference<>(event.getServer());
    }

    @SubscribeEvent
    public static void handleRegisterCommands(RegisterCommandsEvent event) {
        LookupCommand.register(event.getDispatcher(), event.getBuildContext(), event.getCommandSelection());
    }
}