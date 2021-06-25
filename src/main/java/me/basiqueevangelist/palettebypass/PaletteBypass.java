package me.basiqueevangelist.palettebypass;

import net.fabricmc.api.ModInitializer;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.LiteralText;
import org.apache.logging.log4j.LogManager;

public class PaletteBypass implements ModInitializer {
    @Override
    public void onInitialize() {
        LogManager.getLogger("PaletteBypass").info("Bypassing palettes since 2021");
    }

    public static BlockState getForUnknown(UnknownBlockState state) {
        // Returns bedrock for now.
        return Blocks.BEDROCK.getDefaultState();
    }

    public static ItemStack makeFakeStack(NbtCompound tag) {
        ItemStack stack = new ItemStack(Items.NETHER_STAR);
        stack.getOrCreateTag().put("OriginalData", tag);
        stack.setCustomName(new LiteralText("Unknown item: " + tag.getString("id")));
        return stack;
    }
}
