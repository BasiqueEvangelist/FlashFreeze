package me.basiqueevangelist.flashfreeze.client;

import me.basiqueevangelist.flashfreeze.FlashFreeze;
import me.basiqueevangelist.flashfreeze.item.FlashFreezeDataComponents;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;

public class FlashFreezeClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityModelLayerRegistry.registerModelLayer(UnknownEntityEntityModel.LAYER, UnknownEntityEntityModel::getTexturedModelData);

        EntityRendererRegistry.register(FlashFreeze.UNKNOWN_ENTITY, UnknownEntityEntityRenderer::new);

        ItemTooltipCallback.EVENT.register((stack, tooltipContext, tooltipType, lines) -> {
            var unknown = stack.get(FlashFreezeDataComponents.UNKNOWN_DATA_COMPONENTS);

            if (unknown == null || unknown.components().isEmpty()) return;

            lines.add(Component.translatable("text.flashfreeze.unknown_item_components", unknown.components().size())
                .withStyle(ChatFormatting.DARK_PURPLE));
        });

        ItemTooltipCallback.EVENT.register((stack, tooltipContext, tooltipType, lines) -> {
            var origItemId = stack.get(FlashFreezeDataComponents.ORIGINAL_ITEM_ID);

            if (origItemId == null || !stack.has(DataComponents.CUSTOM_NAME)) return;

            lines.add(Component.literal(origItemId.toString()).withStyle(ChatFormatting.DARK_GRAY)
                .append(Component.literal("?").withStyle(ChatFormatting.DARK_PURPLE)));
        });
    }
}
