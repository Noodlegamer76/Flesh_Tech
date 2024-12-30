package com.noodlegamer76.fleshtech.gui.widgets.bioforge;

import com.noodlegamer76.fleshtech.gui.bioforge.BioForgeMenu;
import com.noodlegamer76.fleshtech.gui.bioforge.BioForgeScreen;
import com.noodlegamer76.fleshtech.gui.widgets.ScrollableItemList;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;

public class BioForgeItemList extends ScrollableItemList {
    BioForgeScreen screen;
    public BioForgeItemList(int pX, int pY, int pWidth, int pHeight, ArrayList itemList, int itemSlotsX, Component pMessage, BioForgeMenu menu, BioForgeScreen screen) {
        super(pX, pY, pWidth, pHeight, itemList, itemSlotsX, pMessage, menu);
        this.screen = screen;
    }
}
