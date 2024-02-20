package me.basiqueevangelist.flashfreeze;

import me.basiqueevangelist.flashfreeze.block.UnknownBlockBlock;
import me.basiqueevangelist.flashfreeze.command.LookupCommand;
import me.basiqueevangelist.flashfreeze.item.UnknownItemItem;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Identifier;
import org.slf4j.LoggerFactory;

import java.lang.ref.WeakReference;

public class FlashFreeze implements ModInitializer {
    public static final String MODID = "flashfreeze";

    public static final Identifier MALDENHAGEN = FlashFreeze.id("maldenhagen");

    public static WeakReference<MinecraftServer> SERVER;

    public static final UnknownBlockBlock UNKNOWN_BLOCK = new UnknownBlockBlock();
    public static final UnknownItemItem UNKNOWN_ITEM = new UnknownItemItem();

    public void onInitialize() {
        LoggerFactory.getLogger("FlashFreeze").info("Flash freezing content since 2021");

        ServerLifecycleEvents.SERVER_STARTING.register(server -> {
            FlashFreeze.SERVER = new WeakReference<>(server);
        });

        CommandRegistrationCallback.EVENT.register(LookupCommand::register);

        Registry.register(Registries.BLOCK, id("unknown_block"), UNKNOWN_BLOCK);
        Registry.register(Registries.ITEM, id("unknown_item"), UNKNOWN_ITEM);
    }

    public static Identifier id(String path) {
        return new Identifier(MODID, path);
    }

    public static ItemStack makeFakeStack(NbtCompound tag, byte count) {
        ItemStack stack = new ItemStack(UNKNOWN_ITEM, count);
        stack.getOrCreateNbt().put("OriginalData", tag);
        return stack;
    }
}
