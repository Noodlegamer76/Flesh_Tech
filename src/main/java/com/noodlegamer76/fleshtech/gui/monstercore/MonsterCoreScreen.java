package com.noodlegamer76.fleshtech.gui.monstercore;

import com.mojang.blaze3d.vertex.PoseStack;
import com.noodlegamer76.fleshtech.FleshTechMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import java.awt.*;

public class MonsterCoreScreen extends AbstractContainerScreen<MonsterCoreMenu> {
    private int scaledWidth;
    private int scaledHeight;
    private double guiScale;

    private static final ResourceLocation SQUARE_PANEL = new ResourceLocation(FleshTechMod.MODID, "textures/screens/basic_background.png");
    private static final ResourceLocation TEXTURE = new ResourceLocation(FleshTechMod.MODID, "textures/screens/slot.png");

    public MonsterCoreScreen(MonsterCoreMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);

        int screenWidth = Minecraft.getInstance().getWindow().getScreenWidth();
        int screenHeight = Minecraft.getInstance().getWindow().getScreenHeight();

        int[] clampedDimensions = clampTo16x9(screenWidth, screenHeight);
        scaledWidth = clampedDimensions[0];
        scaledHeight = clampedDimensions[1];

        this.guiScale = Minecraft.getInstance().getWindow().getGuiScale();
        this.imageWidth = (int) Math.round(scaledWidth / guiScale);
        this.imageHeight = (int) Math.round(scaledHeight / guiScale);

        this.leftPos = (screenWidth - imageWidth) / 2;
        this.topPos = (screenHeight - imageHeight) / 2;
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        this.renderBackground(guiGraphics);

        renderStats(guiGraphics, partialTick, mouseX, mouseY);


        int quarterWidth = imageWidth / 4;
        int smallHeightGap = (int) (imageHeight * 0.025);
        int smallWidthGap = (int) (imageWidth * 0.025);


        guiGraphics.blit(SQUARE_PANEL, leftPos + imageWidth - quarterWidth - smallWidthGap, topPos + smallHeightGap, 0, 0,
                quarterWidth,
                imageHeight - (smallHeightGap * 2),
                quarterWidth,
                imageHeight - (smallHeightGap * 2));

        guiGraphics.blit(SQUARE_PANEL, leftPos + quarterWidth + (smallWidthGap * 2), topPos + smallHeightGap * 5, 0, 0,
                quarterWidth * 2 - (smallWidthGap * 4),
                imageHeight - (smallHeightGap * 6),
                quarterWidth * 2 - (smallWidthGap * 4),
                imageHeight - (smallHeightGap * 6));
    }

    private void renderStats(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        int quarterWidth = imageWidth / 4;
        int smallHeightGap = (int) (imageHeight * 0.025);
        int smallWidthGap = (int) (imageWidth * 0.025);
        int smallTextHeightGap = (int) (imageHeight * 0.0125);
        int leftTextPos = leftPos + (int) (smallWidthGap * 1.25f);
        int topTextPos = topPos + (int) (smallHeightGap * 1.75f);

        guiGraphics.blit(SQUARE_PANEL, leftPos + smallWidthGap, topPos + smallHeightGap, 0, 0,
                quarterWidth,
                imageHeight - (smallHeightGap * 2),
                quarterWidth,
                imageHeight - (smallHeightGap * 2));

        CompoundTag tag = menu.entity.getUpdateTag();
        if (tag == null) {
            return;
        }

        renderString(font,
                Component.literal("Calories: " + tag.getLong("calories")),
                guiGraphics,
                leftTextPos,
                topTextPos);

        renderString(font,
                Component.literal("Carbohydrates: " + tag.getLong("carbohydrates")),
                guiGraphics,
                leftTextPos,
                topTextPos * 2);

    }

    private void renderString(Font font, Component component, GuiGraphics guiGraphics, int x, int y) {
        PoseStack poseStack = guiGraphics.pose();
        poseStack.pushPose();

        float scale = (float) imageWidth / 500;

        poseStack.translate(x, y, 0);
        poseStack.scale(scale, scale, scale);
        poseStack.translate(-x, -y, 0);

        guiGraphics.drawString(font,
                component,
                x, y,
                Color.WHITE.getRGB());

        poseStack.popPose();
    }

    @Override
    protected void renderLabels(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY) {
    }

    @Override
    public void resize(Minecraft pMinecraft, int pWidth, int pHeight) {
        super.resize(pMinecraft, pWidth, pHeight);

        int screenWidth = pMinecraft.getWindow().getWidth();
        int screenHeight = pMinecraft.getWindow().getHeight();

        int[] clampedDimensions = clampTo16x9(screenWidth, screenHeight);
        scaledWidth = clampedDimensions[0];
        scaledHeight = clampedDimensions[1];

        guiScale = pMinecraft.getWindow().getGuiScale();
        imageWidth = (int) Math.round(scaledWidth / guiScale);
        imageHeight = (int) Math.round(scaledHeight / guiScale);

        leftPos = (width - imageWidth) / 2;
        topPos = (height - imageHeight) / 2;
    }

    public static int[] clampTo16x9(int screenWidth, int screenHeight) {
        double targetAspectRatio = 16.0 / 9.0;

        // Calculate the aspect ratio of the current screen dimensions
        double currentAspectRatio = (double) screenWidth / screenHeight;

        if (currentAspectRatio > targetAspectRatio) {
            // Screen is wider than 16:9, clamp width based on height
            screenWidth = (int) (screenHeight * targetAspectRatio);
        } else if (currentAspectRatio < targetAspectRatio) {
            // Screen is taller than 16:9, clamp height based on width
            screenHeight = (int) (screenWidth / targetAspectRatio);
        }

        return new int[]{screenWidth, screenHeight};
    }
}
