package me.basiqueevangelist.palettebypass;

import net.fabricmc.api.ModInitializer;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
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
}
