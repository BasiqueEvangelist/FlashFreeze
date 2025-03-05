package me.basiqueevangelist.flashfreeze.client;

import me.basiqueevangelist.flashfreeze.item.FlashFreezeDataComponents;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class FlashFreezeClient {
    public static void addTooltips(ItemStack stack, List<Component> lines) {
        var origItemId = stack.get(FlashFreezeDataComponents.ORIGINAL_ITEM_ID);

        if (origItemId != null && stack.has(DataComponents.CUSTOM_NAME)) {
            lines.add(Component.literal(origItemId.toString()).withStyle(ChatFormatting.DARK_GRAY)
                .append(Component.literal("?").withStyle(ChatFormatting.DARK_PURPLE)));
        }

        var unknown = stack.get(FlashFreezeDataComponents.UNKNOWN_DATA_COMPONENTS);

        if (unknown != null && !unknown.components().isEmpty()) {
            lines.add(Component.translatable("text.flashfreeze.unknown_item_components", unknown.components().size())
                .withStyle(ChatFormatting.DARK_PURPLE));
        }
    }
}
