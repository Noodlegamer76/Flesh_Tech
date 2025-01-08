package com.noodlegamer76.devgui.client.util;

import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Axis;
import com.noodlegamer76.devgui.particles.EditableParticleConfig;
import com.noodlegamer76.devgui.particles.EditableParticles;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class RenderCube {

    public static void renderParticleCube(VertexConsumer buffer, EditableParticleConfig config, int lightColor, EditableParticles particle) {
        float size = config.getHeight();
        PoseStack poseStack = new PoseStack();
        Vec3 cameraPos = Minecraft.getInstance().gameRenderer.getMainCamera().getPosition();

        float f = (float) Mth.lerp(particle.partialTicks, particle.xo1, config.getX());
        float f1 = (float) Mth.lerp(particle.partialTicks, particle.yo1, config.getY());
        float f2 = (float) Mth.lerp(particle.partialTicks, particle.zo1, config.getZ());

        for (int i = 0; i < 6; i++) {
            poseStack.pushPose();

            poseStack.translate(f, f1, f2);

            if (config.getCubeSnapSize() > 0) {
                float snapSize = config.getCubeSnapSize();
                float snappedX = (float) roundToNearest(f, snapSize);
                float snappedY = (float) roundToNearest(f1, snapSize);
                float snappedZ = (float) roundToNearest(f2, snapSize);

                poseStack.translate(snappedX - f - 0.5, snappedY - f1 - 0.5, snappedZ - f2 - 0.5);
            }

            poseStack.translate(-cameraPos.x, -cameraPos.y, -cameraPos.z);



            poseStack.scale(size, size, size);;
            switch (i) {
                case 0:
                    break;
                case 1:
                    poseStack.mulPose(Axis.XP.rotationDegrees(90));
                    break;
                case 2:
                    poseStack.mulPose(Axis.XP.rotationDegrees(180));
                    break;
                case 3:
                    poseStack.mulPose(Axis.XP.rotationDegrees(-90));
                    break;
                case 4:
                    poseStack.mulPose(Axis.ZP.rotationDegrees(-90));
                    break;
                case 5:
                    poseStack.mulPose(Axis.ZN.rotationDegrees(-90));
                    break;
            }

            poseStack.translate(0, -0.5, 0);
            poseStack.scale(-0.5f, -0.5f, -0.5f);



            //poseStack.scale(size, size, size);

            Matrix4f matrix4f = poseStack.last().pose();

            buffer.vertex(matrix4f, -1, 0, -1)
                    .uv(0, 0)
                    .color(config.getRed(), config.getGreen(), config.getBlue(), config.getAlpha())
                    .uv2(lightColor)
                    .endVertex();

            buffer.vertex(matrix4f, 1, 0, -1)
                    .uv(1, 0)
                    .color(config.getRed(), config.getGreen(), config.getBlue(), config.getAlpha())
                    .uv2(lightColor)
                    .endVertex();

            buffer.vertex(matrix4f, 1, 0, 1)
                    .uv(1, 1)
                    .color(config.getRed(), config.getGreen(), config.getBlue(), config.getAlpha())
                    .uv2(lightColor)
                    .endVertex();

            buffer.vertex(matrix4f, -1, 0, 1)
                    .uv(0, 1)
                    .color(config.getRed(), config.getGreen(), config.getBlue(), config.getAlpha())
                    .uv2(lightColor)
                    .endVertex();

            poseStack.popPose();

        }
    }

    public static double roundToNearest(float number, float roundTo) {
        return Math.round(number / roundTo) * roundTo;
    }
}
