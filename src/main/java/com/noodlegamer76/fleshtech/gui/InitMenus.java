package com.noodlegamer76.fleshtech.gui;

import com.noodlegamer76.fleshtech.FleshTechMod;
import com.noodlegamer76.fleshtech.gui.monstercore.MonsterCoreMenu;
import com.noodlegamer76.fleshtech.gui.monstercore.MonsterCoreScreen;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;


public class InitMenus {

    public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(ForgeRegistries.MENU_TYPES, FleshTechMod.MODID);

    public static final RegistryObject<MenuType<MonsterCoreMenu>> MONSTER_CORE_CONTAINER = MENU_TYPES.register("monster_core_block",
            () -> IForgeMenuType.create((windowId, inv, data) -> new MonsterCoreMenu(windowId, inv.player, data.readBlockPos())));



}
