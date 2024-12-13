package com.noodlegamer76.fleshtech.creativetabs;

import com.noodlegamer76.fleshtech.FleshTechMod;
import com.noodlegamer76.fleshtech.item.InitItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class InitCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, FleshTechMod.MODID);

    public static RegistryObject<CreativeModeTab> fleshTechTab = CREATIVE_TABS.register("fleshtech_tab", () -> CreativeModeTab.builder()
            .title(Component.translatable("fleshtech.creative_tab"))
            .icon(() -> new ItemStack(InitItems.TEST_ITEM.get()))
            .build());
}
