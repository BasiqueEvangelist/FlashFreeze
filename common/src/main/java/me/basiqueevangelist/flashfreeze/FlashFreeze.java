package me.basiqueevangelist.flashfreeze;

import me.basiqueevangelist.flashfreeze.block.UnknownBlockBlock;
import me.basiqueevangelist.flashfreeze.item.FlashFreezeDataComponents;
import me.basiqueevangelist.flashfreeze.item.UnknownItemItem;
import me.basiqueevangelist.flashfreeze.util.FlashFreezePlatform;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import org.slf4j.LoggerFactory;

import java.lang.ref.WeakReference;
import java.util.function.Supplier;

public class FlashFreeze {
    public static final String MODID = "flashfreeze";

    public static WeakReference<MinecraftServer> SERVER;

    public static final Supplier<UnknownBlockBlock> UNKNOWN_BLOCK = FlashFreezePlatform.I.registerWithKey(
        Registries.BLOCK,
        id("unknown_block"),
        UnknownBlockBlock::new
    );

    public static final Supplier<UnknownItemItem> UNKNOWN_ITEM = FlashFreezePlatform.I.registerWithKey(
        Registries.ITEM,
        id("unknown_item"),
        UnknownItemItem::new
    );

    public static final Supplier<EntityType<UnknownEntityEntity>> UNKNOWN_ENTITY = FlashFreezePlatform.I.registerWithKey(
        Registries.ENTITY_TYPE,
        id("unknown_entity"),
        key -> EntityType.Builder.<UnknownEntityEntity>of(UnknownEntityEntity::new, MobCategory.MISC)
            .sized(0.9f, 0.9f)
            .build(key)
    );

    public static void init() {
        LoggerFactory.getLogger("FlashFreeze").info("Flash freezing content since 2021");

        FlashFreezeDataComponents.init();
    }

    public static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }
}
