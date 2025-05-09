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
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ARGB;
import net.minecraft.util.Mth;

import static net.minecraft.client.renderer.entity.LivingEntityRenderer.getOverlayCoords;

public class UnknownEntityEntityRenderer extends EntityRenderer<UnknownEntityEntity, UnknownEntityEntityRenderState> {
    private final UnknownEntityEntityModel model;

    public UnknownEntityEntityRenderer(EntityRendererProvider.Context context) {
        super(context);

        this.model = new UnknownEntityEntityModel(context.bakeLayer(UnknownEntityEntityModel.LAYER));
    }

    @Override
    public UnknownEntityEntityRenderState createRenderState() {
        return new UnknownEntityEntityRenderState();
    }

    @Override
    public void extractRenderState(UnknownEntityEntity entity, UnknownEntityEntityRenderState state, float partialTick) {
        super.extractRenderState(entity, state, partialTick);
        state.isInvisibleToPlayer = state.isInvisible && entity.isInvisibleTo(Minecraft.getInstance().player);

        state.yRot = Mth.rotLerp(partialTick, entity.yRotO, entity.getYRot());
        state.xRot = entity.getXRot(partialTick);
    }

    @Override
    public void render(UnknownEntityEntityRenderState renderState, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        poseStack.pushPose();

        poseStack.mulPose(Axis.YP.rotationDegrees(180.0F - renderState.yRot));

        poseStack.scale(-1.0F, -1.0F, 1.0F);
        poseStack.translate(0.0F, -1.501F, 0.0F);
        this.model.setupAnim(renderState);
        boolean bl = true;
        boolean bl2 = false;
        RenderType renderType = this.model.renderType(this.getTextureLocation(renderState));

        VertexConsumer vertexConsumer = bufferSource.getBuffer(renderType);
        int j = OverlayTexture.pack(OverlayTexture.u(0), OverlayTexture.v(false));
        int k = -1;
        int l = ARGB.multiply(k, -1);
        this.model.renderToBuffer(poseStack, vertexConsumer, packedLight, j, l);


        poseStack.popPose();
        super.render(renderState, poseStack, bufferSource, packedLight);
    }

    public ResourceLocation getTextureLocation(UnknownEntityEntityRenderState renderState) {
        return FlashFreeze.id("textures/entity/unknown_entity.png");
    }
}
