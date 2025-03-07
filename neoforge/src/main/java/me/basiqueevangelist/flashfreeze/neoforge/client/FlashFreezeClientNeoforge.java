package me.basiqueevangelist.flashfreeze.neoforge.client;

import me.basiqueevangelist.flashfreeze.FlashFreeze;
import me.basiqueevangelist.flashfreeze.client.FlashFreezeClient;
import me.basiqueevangelist.flashfreeze.client.UnknownEntityEntityModel;
import me.basiqueevangelist.flashfreeze.client.UnknownEntityEntityRenderer;
import net.minecraft.client.model.geom.ModelLayers;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;


@EventBusSubscriber(modid = FlashFreeze.MODID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
@Mod(value = FlashFreeze.MODID, dist = Dist.CLIENT)
public class FlashFreezeClientNeoforge {
    @SubscribeEvent
    public static void handleRegisterLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(UnknownEntityEntityModel.LAYER, UnknownEntityEntityModel::getTexturedModelData);
    }

    @SubscribeEvent
    public static void handleRegisterEntityRenderer(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(FlashFreeze.UNKNOWN_ENTITY.get(), UnknownEntityEntityRenderer::new);
    }

    public FlashFreezeClientNeoforge() {
        NeoForge.EVENT_BUS.addListener(FlashFreezeClientNeoforge::handleTooltip);
    }

    public static void handleTooltip(ItemTooltipEvent event) {
        FlashFreezeClient.addTooltips(event.getItemStack(), event.getToolTip());
    }
}
