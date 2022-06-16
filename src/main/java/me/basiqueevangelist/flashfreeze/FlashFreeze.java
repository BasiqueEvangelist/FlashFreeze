package me.basiqueevangelist.flashfreeze;

import me.basiqueevangelist.flashfreeze.config.FlashFreezeConfig;
import me.basiqueevangelist.flashfreeze.config.StubFlashFreezeConfig;
import me.basiqueevangelist.flashfreeze.config.clothconfig.FlashFreezeConfigImpl;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.text.Text;
import org.apache.logging.log4j.LogManager;

import java.lang.ref.WeakReference;

public class FlashFreeze implements ModInitializer {
    public static final String MODID = "flashfreeze";

    public static FlashFreezeConfig CONFIG = new StubFlashFreezeConfig();

    public static WeakReference<MinecraftServer> SERVER;

    public void onInitialize() {
        LogManager.getLogger("FlashFreeze").info("Flash freezing content since 2021");

        ServerLifecycleEvents.SERVER_STARTING.register(server -> {
            FlashFreeze.SERVER = new WeakReference<>(server);
        });

        if (FabricLoader.getInstance().isModLoaded("cloth-config2")) {
            CONFIG = new FlashFreezeConfigImpl();
        }
    }

    public static ItemStack makeFakeStack(NbtCompound tag, byte count) {
        ItemStack stack = new ItemStack(Items.NETHER_STAR, count);
        stack.getOrCreateNbt().put("OriginalData", tag);
        stack.getOrCreateNbt().putInt("CustomModelData", 10000);
        stack.setCustomName(Text.of("Unknown item: " + tag.getString("id")));
        return stack;
    }
}
