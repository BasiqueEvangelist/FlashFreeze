package me.basiqueevangelist.flashfreeze.client;

import me.basiqueevangelist.flashfreeze.FlashFreeze;
import me.basiqueevangelist.flashfreeze.item.FlashFreezeDataComponents;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class FlashFreezeClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityModelLayerRegistry.registerModelLayer(UnknownEntityEntityModel.LAYER, UnknownEntityEntityModel::getTexturedModelData);

        EntityRendererRegistry.register(FlashFreeze.UNKNOWN_ENTITY, UnknownEntityEntityRenderer::new);

        ItemTooltipCallback.EVENT.register((stack, tooltipContext, tooltipType, lines) -> {
            var unknown = stack.get(FlashFreezeDataComponents.UNKNOWN_DATA_COMPONENTS);

            if (unknown == null || unknown.components().isEmpty()) return;

            lines.add(Text.translatable("text.flashfreeze.unknown_item_components", unknown.components().size())
                .formatted(Formatting.DARK_PURPLE));
        });

        ItemTooltipCallback.EVENT.register((stack, tooltipContext, tooltipType, lines) -> {
            var origItemId = stack.get(FlashFreezeDataComponents.ORIGINAL_ITEM_ID);

            if (origItemId == null || !stack.contains(DataComponentTypes.CUSTOM_NAME)) return;

            lines.add(Text.literal(origItemId.toString()).formatted(Formatting.DARK_GRAY)
                .append(Text.literal("?").formatted(Formatting.DARK_PURPLE)));
        });
    }
}
