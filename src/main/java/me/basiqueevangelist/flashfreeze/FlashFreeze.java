package me.basiqueevangelist.flashfreeze;

import me.basiqueevangelist.flashfreeze.block.UnknownBlockBlock;
import me.basiqueevangelist.flashfreeze.command.LookupCommand;
import me.basiqueevangelist.flashfreeze.item.FlashFreezeDataComponents;
import me.basiqueevangelist.flashfreeze.item.UnknownItemItem;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Identifier;
import org.slf4j.LoggerFactory;

import java.lang.ref.WeakReference;

public class FlashFreeze implements ModInitializer {
    public static final String MODID = "flashfreeze";

    public static WeakReference<MinecraftServer> SERVER;

    public static final UnknownBlockBlock UNKNOWN_BLOCK = new UnknownBlockBlock();
    public static final UnknownItemItem UNKNOWN_ITEM = new UnknownItemItem();

    public static final EntityType<UnknownEntityEntity> UNKNOWN_ENTITY = EntityType.Builder.<UnknownEntityEntity>create(UnknownEntityEntity::new, SpawnGroup.MISC)
        .dimensions(0.9f, 0.9f)
        .build();

    public void onInitialize() {
        LoggerFactory.getLogger("FlashFreeze").info("Flash freezing content since 2021");

        ServerLifecycleEvents.SERVER_STARTING.register(server -> {
            FlashFreeze.SERVER = new WeakReference<>(server);
        });

        CommandRegistrationCallback.EVENT.register(LookupCommand::register);

        FlashFreezeDataComponents.init();

        Registry.register(Registries.BLOCK, id("unknown_block"), UNKNOWN_BLOCK);
        Registry.register(Registries.ITEM, id("unknown_item"), UNKNOWN_ITEM);
        Registry.register(Registries.ENTITY_TYPE, id("unknown_entity"), UNKNOWN_ENTITY);
    }

    public static Identifier id(String path) {
        return Identifier.of(MODID, path);
    }
}
