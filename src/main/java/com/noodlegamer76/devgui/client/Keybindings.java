package com.noodlegamer76.devgui.client;

import com.mojang.blaze3d.platform.InputConstants;
import com.noodlegamer76.fleshtech.FleshTechMod;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;

public final class Keybindings {
    public static final Keybindings INSTANCE = new Keybindings();

    private Keybindings() {}

    private static final String CATEGORY = "key.categories." + FleshTechMod.MODID;

    public final KeyMapping devGui = new KeyMapping(
            "key." + FleshTechMod.MODID + ".F6",
            KeyConflictContext.IN_GAME,
            InputConstants.getKey(InputConstants.KEY_F6, -1),
            CATEGORY
    );
}
