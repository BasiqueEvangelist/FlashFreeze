package me.basiqueevangelist.flashfreeze;

import me.basiqueevangelist.flashfreeze.block.UnknownBlockBlock;
import me.basiqueevangelist.flashfreeze.command.LookupCommand;
import me.basiqueevangelist.flashfreeze.item.FlashFreezeDataComponents;
import me.basiqueevangelist.flashfreeze.item.UnknownItemItem;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import org.slf4j.LoggerFactory;

import java.lang.ref.WeakReference;

public class FlashFreeze implements ModInitializer {
    public static final String MODID = "flashfreeze";

    public static WeakReference<MinecraftServer> SERVER;

    public static final UnknownBlockBlock UNKNOWN_BLOCK = new UnknownBlockBlock();
    public static final UnknownItemItem UNKNOWN_ITEM = new UnknownItemItem();

    public static final EntityType<UnknownEntityEntity> UNKNOWN_ENTITY = EntityType.Builder.<UnknownEntityEntity>of(UnknownEntityEntity::new, MobCategory.MISC)
        .sized(0.9f, 0.9f)
        .build();

    public void onInitialize() {
        LoggerFactory.getLogger("FlashFreeze").info("Flash freezing content since 2021");

        ServerLifecycleEvents.SERVER_STARTING.register(server -> {
            FlashFreeze.SERVER = new WeakReference<>(server);
        });

        CommandRegistrationCallback.EVENT.register(LookupCommand::register);

        FlashFreezeDataComponents.init();

        Registry.register(BuiltInRegistries.BLOCK, id("unknown_block"), UNKNOWN_BLOCK);
        Registry.register(BuiltInRegistries.ITEM, id("unknown_item"), UNKNOWN_ITEM);
        Registry.register(BuiltInRegistries.ENTITY_TYPE, id("unknown_entity"), UNKNOWN_ENTITY);
    }

    public static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }
}
