package com.noodlegamer76.fleshtech.gui.widgets.bioforge;

import com.noodlegamer76.fleshtech.gui.bioforge.BioForgeMenu;
import com.noodlegamer76.fleshtech.gui.bioforge.BioForgeScreen;
import com.noodlegamer76.fleshtech.packets.bioforge.BioForgeCraftingIDPacket;
import com.noodlegamer76.fleshtech.packets.bioforge.PacketHandler;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;

public class CraftButton extends AbstractWidget {
    BioForgeScreen screen;
    public CraftButton(int pX, int pY, int pWidth, int pHeight, Component pMessage, BioForgeScreen screen) {
        super(pX, pY, pWidth, pHeight, pMessage);
        this.screen = screen;
    }

    @Override
    protected void renderWidget(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
    }

    @Override
    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
        PacketHandler.sendToServer(new BioForgeCraftingIDPacket(screen.selectedItemIndex, screen.getMenu().entity.getBlockPos()));
        return super.mouseClicked(pMouseX, pMouseY, pButton);
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput pNarrationElementOutput) {

    }
}
