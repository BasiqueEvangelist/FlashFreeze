package me.basiqueevangelist.flashfreeze;

import me.basiqueevangelist.flashfreeze.config.FlashFreezeConfig;
import me.basiqueevangelist.flashfreeze.config.StubFlashFreezeConfig;
import me.basiqueevangelist.flashfreeze.config.clothconfig.FlashFreezeConfigImpl;
import me.basiqueevangelist.flashfreeze.util.FFPlatform;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.text.LiteralText;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.BiomeKeys;
import org.apache.logging.log4j.LogManager;

import java.lang.ref.WeakReference;

public class FlashFreeze {
    public static final String MODID = "flashfreeze";

    public static FlashFreezeConfig CONFIG = new StubFlashFreezeConfig();

    public static WeakReference<MinecraftServer> SERVER;

    public static void init() {
        LogManager.getLogger("FlashFreeze").info("Flash freezing content since 2021");

        if (FFPlatform.isClothConfigPresent()) {
            CONFIG = new FlashFreezeConfigImpl();
        }
    }

    public static ItemStack makeFakeStack(NbtCompound tag, byte count) {
        ItemStack stack = new ItemStack(Items.NETHER_STAR, count);
        stack.getOrCreateNbt().put("OriginalData", tag);
        stack.getOrCreateNbt().putInt("CustomModelData", 10000);
        stack.setCustomName(new LiteralText("Unknown item: " + tag.getString("id")));
        return stack;
    }
}
