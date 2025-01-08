package com.noodlegamer76.fleshtech.client.renderer.block;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.noodlegamer76.devgui.particles.EditableParticleConfig;
import com.noodlegamer76.devgui.particles.RegisterEditableParticles;
import com.noodlegamer76.fleshtech.entity.block.RenderTester;
import com.noodlegamer76.fleshtech.event.RegisterShadersEvent;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;

public class TestRenderer<T extends RenderTester> implements BlockEntityRenderer<RenderTester> {
    public static final ResourceLocation TEXTURE = new ResourceLocation("minecraft", "textures/block/stone.png");

    public TestRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(RenderTester pBlockEntity, float pPartialTick, PoseStack poseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
        EditableParticleConfig config = RegisterEditableParticles.copyDefaultParticles("Untit");

        if (config != null) {
            for (int i = 0; i < 100; i++) {
                Vec3 center = pBlockEntity.getBlockPos().getCenter();
                config.setPosition(center.x, center.y, center.z);
                Vec3 dir = new Vec3(Math.random() - 0.5, Math.random() - 0.5, Math.random() - 0.5);
                double spread = 0.1;
                config.setDx(config.getDx() + dir.x * spread);
                config.setDy(config.getDy() + dir.y * spread);
                config.setDz(config.getDz() + dir.z * spread);
                RegisterEditableParticles.spawnParticle(config);
            }
        }
    }
}
