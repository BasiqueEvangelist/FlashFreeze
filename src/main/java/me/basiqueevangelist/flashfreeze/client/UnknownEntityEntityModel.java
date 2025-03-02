package me.basiqueevangelist.flashfreeze.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import me.basiqueevangelist.flashfreeze.FlashFreeze;
import me.basiqueevangelist.flashfreeze.UnknownEntityEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

// Made with Blockbench 4.12.1
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports
public class UnknownEntityEntityModel extends EntityModel<UnknownEntityEntity> {
	public static final ModelLayerLocation LAYER = new ModelLayerLocation(FlashFreeze.id("unknown_entity"), "main");

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

	public static LayerDefinition getTexturedModelData() {
		MeshDefinition modelData = new MeshDefinition();
		PartDefinition modelPartData = modelData.getRoot();
		PartDefinition e = modelPartData.addOrReplaceChild("e", CubeListBuilder.create().texOffs(6, 1).addBox(4.0F, -10.0F, -1.0F, 1.0F, 10.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(3, 1).addBox(2.0F, -1.0F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(3, 1).addBox(2.0F, -6.0F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(3, 1).addBox(2.0F, -10.0F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(5.0F, 24.0F, 0.0F));

		PartDefinition r1 = modelPartData.addOrReplaceChild("r1", CubeListBuilder.create().texOffs(4, 1).addBox(0.0F, -10.0F, -1.0F, 1.0F, 10.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(3, 1).addBox(-2.0F, -10.0F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(4, 1).addBox(-2.0F, -4.0F, -1.0F, 1.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(4, 1).addBox(-1.0F, -5.0F, -1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(3, 1).addBox(-2.0F, -6.0F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(4, 1).addBox(-2.0F, -9.0F, -1.0F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(5.0F, 24.0F, 0.0F));

		PartDefinition r2 = modelPartData.addOrReplaceChild("r2", CubeListBuilder.create().texOffs(4, 1).addBox(0.0F, -10.0F, -1.0F, 1.0F, 10.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(3, 1).addBox(-2.0F, -10.0F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(4, 1).addBox(-2.0F, -4.0F, -1.0F, 1.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(4, 1).addBox(-1.0F, -5.0F, -1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(3, 1).addBox(-2.0F, -6.0F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(4, 1).addBox(-2.0F, -9.0F, -1.0F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, 24.0F, 0.0F));

		PartDefinition o = modelPartData.addOrReplaceChild("o", CubeListBuilder.create().texOffs(4, 1).addBox(-4.0F, -10.0F, -1.0F, 1.0F, 10.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(4, 1).addBox(-6.0F, -10.0F, -1.0F, 1.0F, 10.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(4, 1).addBox(-5.0F, -10.0F, -1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(4, 1).addBox(-5.0F, -1.0F, -1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, 24.0F, 0.0F));

		PartDefinition r3 = modelPartData.addOrReplaceChild("r3", CubeListBuilder.create().texOffs(4, 1).addBox(0.0F, -10.0F, -1.0F, 1.0F, 10.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(3, 1).addBox(-2.0F, -10.0F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(4, 1).addBox(-2.0F, -4.0F, -1.0F, 1.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(4, 1).addBox(-1.0F, -5.0F, -1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(3, 1).addBox(-2.0F, -6.0F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(4, 1).addBox(-2.0F, -9.0F, -1.0F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-7.0F, 24.0F, 0.0F));
		return LayerDefinition.create(modelData, 16, 16);
	}

	@Override
	public void renderToBuffer(PoseStack matrices, VertexConsumer vertexConsumer, int light, int overlay, int color) {
		e.render(matrices, vertexConsumer, light, overlay);
		r1.render(matrices, vertexConsumer, light, overlay);
		r2.render(matrices, vertexConsumer, light, overlay);
		o.render(matrices, vertexConsumer, light, overlay);
		r3.render(matrices, vertexConsumer, light, overlay);
	}

	@Override
	public void setupAnim(UnknownEntityEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {

	}
}