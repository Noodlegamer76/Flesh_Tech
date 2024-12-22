package com.noodlegamer76.fleshtech.gui.widgets;

import com.mojang.blaze3d.vertex.PoseStack;
import com.noodlegamer76.fleshtech.FleshTechMod;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractScrollWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Overlay;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;

import static net.minecraft.client.gui.screens.inventory.AbstractContainerScreen.INVENTORY_LOCATION;

public class ScrollableItemList extends AbstractScrollWidget {
    private ArrayList<ItemStack> itemList;
    private ItemStack hoveredItem;
    private ItemStack selectedItem;
    private final int itemSlotsX;
    private static final ResourceLocation SLOT = new ResourceLocation(FleshTechMod.MODID, "textures/screens/slot.png");

    public ScrollableItemList(int pX, int pY, int pWidth, int pHeight, ArrayList itemList, int itemSlotsX, Component pMessage) {
        super(pX, pY, pWidth, pHeight, pMessage);
        this.itemList = itemList;
        this.itemSlotsX = itemSlotsX;
    }

    @Override
    protected int getInnerHeight() {
        return ((itemList.size() / itemSlotsX) * (width / itemSlotsX)) + (width / itemSlotsX);
    }

    @Override
    protected double scrollRate() {
        return (double) width / itemSlotsX;
    }

    @Override
    protected void renderBorder(GuiGraphics pGuiGraphics, int pX, int pY, int pWidth, int pHeight) {
        int i = this.isFocused() ? -1 : -6250336;
        //pGuiGraphics.fill(pX, pY, pX + pWidth, pY + pHeight, i);
        //pGuiGraphics.fill(pX + 1, pY + 1, pX + pWidth - 1, pY + pHeight - 1, -16777216);
    }

    @Override
    protected void renderContents(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        renderItemList(guiGraphics, mouseX, mouseY, partialTick);
    }

    private void renderItemList(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        int widthSize = width / itemSlotsX;
        float itemScale = (float) widthSize / 18;
        hoveredItem = null;
        for (int i = 0; i < itemList.size(); i++) {
            int col = i % itemSlotsX;
            int row = i / itemSlotsX;

            int x = getX() + col * widthSize;
            int y = getY() + row * widthSize;

            guiGraphics.blit(SLOT, x, y,0, 0, widthSize, widthSize, widthSize, widthSize);

            PoseStack poseStack = guiGraphics.pose();

            poseStack.pushPose();
            poseStack.translate(x + itemScale, y + itemScale, 0);
            poseStack.scale(itemScale, itemScale, itemScale);
            guiGraphics.renderFakeItem(itemList.get(i), 0, 0);
            poseStack.popPose();

            if ((mouseX >= x) && (mouseX < x + widthSize) && (mouseY + scrollAmount() >= y) && (mouseY + scrollAmount() < y + widthSize)) {
                guiGraphics.fillGradient(RenderType.guiOverlay(), x, y, x + widthSize, y + widthSize, -2130706433, -2130706433, 0);
                hoveredItem = itemList.get(i);
            }
        }
    }

    public ItemStack getSelectedItem() {
        return selectedItem;
    }

    @Override
    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
        if (hoveredItem != null) {
            selectedItem = hoveredItem;
        }
        return super.mouseClicked(pMouseX, pMouseY, pButton);
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput pNarrationElementOutput) {

    }
}
