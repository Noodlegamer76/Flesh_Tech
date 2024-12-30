package com.noodlegamer76.fleshtech.gui.bioforge;

import com.mojang.blaze3d.vertex.PoseStack;
import com.noodlegamer76.fleshtech.FleshTechMod;
import com.noodlegamer76.fleshtech.gui.widgets.ScrollableItemList;
import com.noodlegamer76.fleshtech.gui.widgets.bioforge.BioForgeItemList;
import com.noodlegamer76.fleshtech.gui.widgets.bioforge.CraftButton;
import com.noodlegamer76.fleshtech.recipes.InitRecipeTypes;
import com.noodlegamer76.fleshtech.recipes.bioforge.BioForgeRecipe;
import com.noodlegamer76.fleshtech.recipes.bioforge.BioForgeRecipeType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.FittingMultiLineTextWidget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.client.gui.widget.ForgeSlider;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;


public class BioForgeScreen extends AbstractContainerScreen<BioForgeMenu> {
    private int scaledWidth;
    private int scaledHeight;
    private double guiScale;
    private BioForgeItemList scrollableItemList;
    CraftButton button;
    ItemStack selectedItem;
    public int selectedItemIndex;
    List<BioForgeRecipe> recipes = menu.level.getRecipeManager().getAllRecipesFor(InitRecipeTypes.BIOFORGE_RECIPE_TYPE.get());

    private static final ResourceLocation SQUARE_PANEL = new ResourceLocation(FleshTechMod.MODID, "textures/screens/basic_background.png");
    private static final ResourceLocation SLOT = new ResourceLocation(FleshTechMod.MODID, "textures/screens/slot.png");

    public BioForgeScreen(BioForgeMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
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
    protected void init() {
        super.init();
        setupWidgets();
    }

    private void setupWidgets() {
        int quarterWidth = imageWidth / 4;
        int smallHeightGap = (int) (imageHeight * 0.025);
        int smallWidthGap = (int) (imageWidth * 0.025);

        ArrayList<ItemStack> items = new ArrayList<>();

        for (BioForgeRecipe recipe: recipes) {
            items.add(recipe.getResultItem(menu.level.registryAccess()));
        }

        scrollableItemList = new BioForgeItemList(
                leftPos + smallWidthGap,
                topPos + smallHeightGap,
                imageWidth / 2,
                imageHeight - smallHeightGap * 2,
                items, 12,
                Component.literal("e \n \n \n \n \n \n \n \n \n \n \n \n \n \n \n \n \n \n \n \n e"),
                menu, this);

        button = new CraftButton(
                leftPos + imageWidth - 40,
                topPos + imageHeight - 40,
                imageWidth,
                imageHeight,
                Component.literal("test"),
                this);

        addRenderableWidget(scrollableItemList);
        addRenderableWidget(button);
    }

    @Override
    protected void rebuildWidgets() {
        super.rebuildWidgets();
        removeWidget(scrollableItemList);
        setupWidgets();
    }

    @Override
    protected void renderLabels(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY) {
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        this.renderBackground(guiGraphics);

        int quarterWidth = imageWidth / 4;
        int smallHeightGap = (int) (imageHeight * 0.025);
        int smallWidthGap = (int) (imageWidth * 0.025);


        if (scrollableItemList != null) {
            if (selectedItem != scrollableItemList.getSelectedItem() && scrollableItemList.getSelectedItem() != null) {
                selectedItem = scrollableItemList.getSelectedItem();
                selectedItemIndex = scrollableItemList.selectedItemIndex;

            }
            renderDescription(guiGraphics,
                    leftPos + smallWidthGap * 2 + quarterWidth * 2,
                    topPos + smallHeightGap,
                    imageWidth / 2 - smallWidthGap * 3,
                    imageHeight - smallHeightGap * 2);
        }
    }

    private void renderDescription(GuiGraphics guiGraphics, int x, int y, int width, int height) {
        int quarterWidth = width / 4;
        int smallHeightGap = (int) (imageHeight * 0.025);
        int smallWidthGap = (int) (imageWidth * 0.025);
        ItemStack stack = selectedItem;
        float itemScale = quarterWidth / 18;


        guiGraphics.blit(SQUARE_PANEL, x, y, 0, 0, width, height, width, height);

        guiGraphics.blit(SLOT, x + smallHeightGap, y + smallHeightGap, 0, 0, quarterWidth, quarterWidth, quarterWidth, quarterWidth);

        if (selectedItem != null) {
            PoseStack poseStack = guiGraphics.pose();
            poseStack.pushPose();
            poseStack.translate(x + smallHeightGap + itemScale, y + smallHeightGap + itemScale, 0);
            poseStack.scale(itemScale, itemScale, itemScale);
            guiGraphics.renderFakeItem(stack, 0, 0);
            poseStack.popPose();

            poseStack.pushPose();
            poseStack.translate(x + smallHeightGap + (float) quarterWidth + itemScale, y + (float) quarterWidth / 2, 0);
            poseStack.scale(itemScale / 2, itemScale / 2, itemScale / 2);
            guiGraphics.drawString(font, selectedItem.getHoverName(), 0, 0, Color.WHITE.getRGB(), true);
            poseStack.popPose();
        }
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

        rebuildWidgets();
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
