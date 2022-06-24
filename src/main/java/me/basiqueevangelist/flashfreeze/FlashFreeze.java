package me.basiqueevangelist.flashfreeze;

import me.basiqueevangelist.flashfreeze.block.UnknownBlockBlock;
import me.basiqueevangelist.flashfreeze.config.FlashFreezeConfig;
import me.basiqueevangelist.flashfreeze.config.StubFlashFreezeConfig;
import me.basiqueevangelist.flashfreeze.config.clothconfig.FlashFreezeConfigImpl;
import me.basiqueevangelist.flashfreeze.item.UnknownItemItem;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.LogManager;

import java.lang.ref.WeakReference;

public class FlashFreeze implements ModInitializer {
    public static final String MODID = "flashfreeze";

    public static final Identifier MALDENHAGEN = FlashFreeze.id("maldenhagen");

    public static FlashFreezeConfig CONFIG = new StubFlashFreezeConfig();

    public static WeakReference<MinecraftServer> SERVER;

    public static final UnknownBlockBlock UNKNOWN_BLOCK = new UnknownBlockBlock();
    public static final UnknownItemItem UNKNOWN_ITEM = new UnknownItemItem();

    public void onInitialize() {
        LogManager.getLogger("FlashFreeze").info("Flash freezing content since 2021");

        ServerLifecycleEvents.SERVER_STARTING.register(server -> {
            FlashFreeze.SERVER = new WeakReference<>(server);
        });

        Registry.register(Registry.BLOCK, id("unknown_block"), UNKNOWN_BLOCK);
        Registry.register(Registry.ITEM, id("unknown_item"), UNKNOWN_ITEM);

        if (FabricLoader.getInstance().isModLoaded("cloth-config2")) {
            CONFIG = new FlashFreezeConfigImpl();
        }
    }

    public static Identifier id(String path) {
        return new Identifier(MODID, path);
    }

    public static ItemStack makeFakeStack(NbtCompound tag, byte count) {
        ItemStack stack = new ItemStack(UNKNOWN_ITEM, count);
        stack.getOrCreateNbt().put("OriginalData", tag);
        stack.getOrCreateNbt().putInt("CustomModelData", 10000);
        return stack;
    }
}
