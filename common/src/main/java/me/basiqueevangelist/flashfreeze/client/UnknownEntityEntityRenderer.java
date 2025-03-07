package me.basiqueevangelist.flashfreeze.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import me.basiqueevangelist.flashfreeze.FlashFreeze;
import me.basiqueevangelist.flashfreeze.UnknownEntityEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class UnknownEntityEntityRenderer extends EntityRenderer<UnknownEntityEntity> {
    private final UnknownEntityEntityModel model;

    public UnknownEntityEntityRenderer(EntityRendererProvider.Context context) {
        super(context);

        this.model = new UnknownEntityEntityModel(context.bakeLayer(UnknownEntityEntityModel.LAYER));
    }

    @Override
    public void render(UnknownEntityEntity entity, float yaw, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, int light) {
        matrices.pushPose();

        this.model.attackTime = 0;
        this.model.riding = false;
        this.model.young = false;

        float h = Mth.rotLerp(tickDelta, entity.yRotO, entity.getYRot());


        float m = Mth.lerp(tickDelta, entity.xRotO, entity.getXRot());

        matrices.mulPose(Axis.YP.rotationDegrees(180.0F - h));
        matrices.scale(-1.0F, -1.0F, 1.0F);
        matrices.translate(0.0F, -1.501F, 0.0F);
        float o = 0.0F;
        float p = 0.0F;

        this.model.prepareMobModel(entity, p, o, tickDelta);
        this.model.setupAnim(entity, p, o, 0, 0, 0);
        Minecraft minecraftClient = Minecraft.getInstance();
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
        boolean showOutline = minecraftClient.shouldEntityAppearGlowing(entity);

        RenderType renderLayer;

        if (translucent) {
            renderLayer = RenderType.itemEntityTranslucentCull(getTextureLocation(entity));
        } else if (showBody) {
            renderLayer = this.model.renderType(getTextureLocation(entity));
        } else {
            renderLayer = showOutline ? RenderType.outline(getTextureLocation(entity)) : null;
        }

        if (renderLayer != null) {
            VertexConsumer vertexConsumer = vertexConsumers.getBuffer(renderLayer);
            this.model.renderToBuffer(matrices, vertexConsumer, light, 0, translucent ? 654311423 : -1);
        }

        matrices.popPose();
        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
    }

    @Override
    public ResourceLocation getTextureLocation(UnknownEntityEntity entity) {
        return FlashFreeze.id("textures/entity/unknown_entity.png");
    }
}
