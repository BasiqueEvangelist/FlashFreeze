package me.basiqueevangelist.flashfreeze.client;

import me.basiqueevangelist.flashfreeze.FlashFreeze;
import me.basiqueevangelist.flashfreeze.UnknownEntityEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;

// Made with Blockbench 4.12.1
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports
public class UnknownEntityEntityModel extends EntityModel<UnknownEntityEntity> {
	public static final EntityModelLayer LAYER = new EntityModelLayer(FlashFreeze.id("unknown_entity"), "main");

	private final ModelPart e;
	private final ModelPart r1;
	private final ModelPart r2;
	private final ModelPart o;
	private final ModelPart r3;

	public UnknownEntityEntityModel(ModelPart root) {
		this.e = root.getChild("e");
		this.r1 = root.getChild("r1");
		this.r2 = root.getChild("r2");
		this.o = root.getChild("o");
		this.r3 = root.getChild("r3");
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData e = modelPartData.addChild("e", ModelPartBuilder.create().uv(6, 1).cuboid(4.0F, -10.0F, -1.0F, 1.0F, 10.0F, 2.0F, new Dilation(0.0F))
		.uv(3, 1).cuboid(2.0F, -1.0F, -1.0F, 2.0F, 1.0F, 2.0F, new Dilation(0.0F))
		.uv(3, 1).cuboid(2.0F, -6.0F, -1.0F, 2.0F, 1.0F, 2.0F, new Dilation(0.0F))
		.uv(3, 1).cuboid(2.0F, -10.0F, -1.0F, 2.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(5.0F, 24.0F, 0.0F));

		ModelPartData r1 = modelPartData.addChild("r1", ModelPartBuilder.create().uv(4, 1).cuboid(0.0F, -10.0F, -1.0F, 1.0F, 10.0F, 2.0F, new Dilation(0.0F))
		.uv(3, 1).cuboid(-2.0F, -10.0F, -1.0F, 2.0F, 1.0F, 2.0F, new Dilation(0.0F))
		.uv(4, 1).cuboid(-2.0F, -4.0F, -1.0F, 1.0F, 4.0F, 2.0F, new Dilation(0.0F))
		.uv(4, 1).cuboid(-1.0F, -5.0F, -1.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F))
		.uv(3, 1).cuboid(-2.0F, -6.0F, -1.0F, 2.0F, 1.0F, 2.0F, new Dilation(0.0F))
		.uv(4, 1).cuboid(-2.0F, -9.0F, -1.0F, 1.0F, 3.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(5.0F, 24.0F, 0.0F));

		ModelPartData r2 = modelPartData.addChild("r2", ModelPartBuilder.create().uv(4, 1).cuboid(0.0F, -10.0F, -1.0F, 1.0F, 10.0F, 2.0F, new Dilation(0.0F))
		.uv(3, 1).cuboid(-2.0F, -10.0F, -1.0F, 2.0F, 1.0F, 2.0F, new Dilation(0.0F))
		.uv(4, 1).cuboid(-2.0F, -4.0F, -1.0F, 1.0F, 4.0F, 2.0F, new Dilation(0.0F))
		.uv(4, 1).cuboid(-1.0F, -5.0F, -1.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F))
		.uv(3, 1).cuboid(-2.0F, -6.0F, -1.0F, 2.0F, 1.0F, 2.0F, new Dilation(0.0F))
		.uv(4, 1).cuboid(-2.0F, -9.0F, -1.0F, 1.0F, 3.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(1.0F, 24.0F, 0.0F));

		ModelPartData o = modelPartData.addChild("o", ModelPartBuilder.create().uv(4, 1).cuboid(-4.0F, -10.0F, -1.0F, 1.0F, 10.0F, 2.0F, new Dilation(0.0F))
		.uv(4, 1).cuboid(-6.0F, -10.0F, -1.0F, 1.0F, 10.0F, 2.0F, new Dilation(0.0F))
		.uv(4, 1).cuboid(-5.0F, -10.0F, -1.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F))
		.uv(4, 1).cuboid(-5.0F, -1.0F, -1.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(1.0F, 24.0F, 0.0F));

		ModelPartData r3 = modelPartData.addChild("r3", ModelPartBuilder.create().uv(4, 1).cuboid(0.0F, -10.0F, -1.0F, 1.0F, 10.0F, 2.0F, new Dilation(0.0F))
		.uv(3, 1).cuboid(-2.0F, -10.0F, -1.0F, 2.0F, 1.0F, 2.0F, new Dilation(0.0F))
		.uv(4, 1).cuboid(-2.0F, -4.0F, -1.0F, 1.0F, 4.0F, 2.0F, new Dilation(0.0F))
		.uv(4, 1).cuboid(-1.0F, -5.0F, -1.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F))
		.uv(3, 1).cuboid(-2.0F, -6.0F, -1.0F, 2.0F, 1.0F, 2.0F, new Dilation(0.0F))
		.uv(4, 1).cuboid(-2.0F, -9.0F, -1.0F, 1.0F, 3.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(-7.0F, 24.0F, 0.0F));
		return TexturedModelData.of(modelData, 16, 16);
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, int color) {
		e.render(matrices, vertexConsumer, light, overlay);
		r1.render(matrices, vertexConsumer, light, overlay);
		r2.render(matrices, vertexConsumer, light, overlay);
		o.render(matrices, vertexConsumer, light, overlay);
		r3.render(matrices, vertexConsumer, light, overlay);
	}

	@Override
	public void setAngles(UnknownEntityEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {

	}
}