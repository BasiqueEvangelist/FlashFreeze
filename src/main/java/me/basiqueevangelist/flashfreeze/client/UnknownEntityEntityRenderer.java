package me.basiqueevangelist.flashfreeze.client;

import me.basiqueevangelist.flashfreeze.FlashFreeze;
import me.basiqueevangelist.flashfreeze.UnknownEntityEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class UnknownEntityEntityRenderer extends EntityRenderer<UnknownEntityEntity> {
    private final UnknownEntityEntityModel model;

    public UnknownEntityEntityRenderer(EntityRendererFactory.Context context) {
        super(context);

        this.model = new UnknownEntityEntityModel(context.getPart(UnknownEntityEntityModel.LAYER));
    }

    @Override
    public void render(UnknownEntityEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        matrices.push();

        this.model.handSwingProgress = 0;
        this.model.riding = false;
        this.model.child = false;

        float h = MathHelper.lerpAngleDegrees(tickDelta, entity.prevYaw, entity.getYaw());
        float k = 0.0f;


        float m = MathHelper.lerp(tickDelta, entity.prevPitch, entity.getPitch());

        k = MathHelper.wrapDegrees(k);


        matrices.scale(-1.0F, -1.0F, 1.0F);
        matrices.translate(0.0F, -1.501F, 0.0F);
        float o = 0.0F;
        float p = 0.0F;

        this.model.animateModel(entity, p, o, tickDelta);
        this.model.setAngles(entity, p, o, 0, k, m);
        MinecraftClient minecraftClient = MinecraftClient.getInstance();
        boolean showBody = !entity.isInvisible();

        // 		Identifier identifier = this.getTexture(entity);
        //		if (translucent) {
        //			return RenderLayer.getItemEntityTranslucentCull(identifier);
        //		} else if (showBody) {
        //			return this.model.getLayer(identifier);
        //		} else {
        //			return showOutline ? RenderLayer.getOutline(identifier) : null;
        //		}
        boolean translucent = !showBody && !entity.isInvisibleTo(minecraftClient.player);
        boolean showOutline = minecraftClient.hasOutline(entity);

        RenderLayer renderLayer;

        if (translucent) {
            renderLayer = RenderLayer.getItemEntityTranslucentCull(getTexture(entity));
        } else if (showBody) {
            renderLayer = this.model.getLayer(getTexture(entity));
        } else {
            renderLayer = showOutline ? RenderLayer.getOutline(getTexture(entity)) : null;
        }

        if (renderLayer != null) {
            VertexConsumer vertexConsumer = vertexConsumers.getBuffer(renderLayer);
            this.model.render(matrices, vertexConsumer, light, 0, translucent ? 654311423 : -1);
        }

        matrices.pop();
        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
    }

    @Override
    public Identifier getTexture(UnknownEntityEntity entity) {
        return FlashFreeze.id("textures/entity/unknown_entity.png");
    }
}
