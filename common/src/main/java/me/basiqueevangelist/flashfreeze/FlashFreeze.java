package me.basiqueevangelist.flashfreeze;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.LiteralText;
import org.apache.logging.log4j.LogManager;

public class FlashFreeze {
    public static final String MODID = "flashfreeze";

    public static void init() {
        LogManager.getLogger("FlashFreeze").info("Flash freezing content since 2021");
    }

    public static BlockState getForUnknown(UnknownBlockState state) {
        // Returns bedrock for now.
        return Blocks.BEDROCK.getDefaultState();
    }

    public static ItemStack makeFakeStack(NbtCompound tag, byte count) {
        ItemStack stack = new ItemStack(Items.NETHER_STAR, count);
        stack.getOrCreateNbt().put("OriginalData", tag);
        stack.getOrCreateNbt().putInt("CustomModelData", 10000);
        stack.setCustomName(new LiteralText("Unknown item: " + tag.getString("id")));
        return stack;
    }
}
