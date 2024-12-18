package com.noodlegamer76.fleshtech.item;

import com.noodlegamer76.fleshtech.FleshTechMod;
import com.noodlegamer76.fleshtech.block.InitBlocks;
import net.minecraft.world.item.*;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class InitItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, FleshTechMod.MODID);

    //dev stuff
    public static final RegistryObject<Item> TEST_ITEM = ITEMS.register("test_item",
            () -> new TestItem(new Item.Properties()));
    public static final RegistryObject<Item> RENDER_TESTER_BLOCK_ITEM = ITEMS.register("render_tester_block",
            () -> new BlockItem(InitBlocks.RENDER_TESTER_BLOCK.get(), new Item.Properties()));


    public static final RegistryObject<Item> MONSTER_CORE = ITEMS.register("monster_core",
            () -> new BlockItem(InitBlocks.MONSTER_CORE.get(), new Item.Properties()));
    public static final RegistryObject<Item> STOMACH = ITEMS.register("stomach",
            () -> new BlockItem(InitBlocks.STOMACH.get(), new Item.Properties()));
}
