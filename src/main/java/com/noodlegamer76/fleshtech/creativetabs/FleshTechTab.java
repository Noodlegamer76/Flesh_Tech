package com.noodlegamer76.fleshtech.creativetabs;

import com.noodlegamer76.fleshtech.item.InitItems;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class FleshTechTab {
    @SubscribeEvent
    public void buildContents(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == InitCreativeTabs.fleshTechTab.getKey()) {
            event.accept(InitItems.TEST_ITEM);
            event.accept(InitItems.RENDER_TESTER_BLOCK_ITEM);
            event.accept(InitItems.MONSTER_CORE);
            event.accept(InitItems.STOMACH);
            event.accept(InitItems.BIO_FORGE);
        }
    }
}
