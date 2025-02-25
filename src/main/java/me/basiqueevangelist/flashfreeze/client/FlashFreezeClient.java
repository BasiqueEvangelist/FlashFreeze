package me.basiqueevangelist.flashfreeze.client;

import me.basiqueevangelist.flashfreeze.FlashFreeze;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class FlashFreezeClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityModelLayerRegistry.registerModelLayer(UnknownEntityEntityModel.LAYER, UnknownEntityEntityModel::getTexturedModelData);

        EntityRendererRegistry.register(FlashFreeze.UNKNOWN_ENTITY, UnknownEntityEntityRenderer::new);
    }
}
