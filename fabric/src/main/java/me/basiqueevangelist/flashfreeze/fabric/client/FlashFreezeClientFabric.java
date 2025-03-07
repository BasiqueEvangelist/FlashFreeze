package me.basiqueevangelist.flashfreeze.fabric.client;

import me.basiqueevangelist.flashfreeze.FlashFreeze;
import me.basiqueevangelist.flashfreeze.client.FlashFreezeClient;
import me.basiqueevangelist.flashfreeze.client.UnknownEntityEntityModel;
import me.basiqueevangelist.flashfreeze.client.UnknownEntityEntityRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class FlashFreezeClientFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityModelLayerRegistry.registerModelLayer(UnknownEntityEntityModel.LAYER, UnknownEntityEntityModel::getTexturedModelData);

        EntityRendererRegistry.register(FlashFreeze.UNKNOWN_ENTITY.get(), UnknownEntityEntityRenderer::new);

        ItemTooltipCallback.EVENT.register((stack, tooltipContext, tooltipType, lines) -> {
            FlashFreezeClient.addTooltips(stack, lines);
        });
    }
}
