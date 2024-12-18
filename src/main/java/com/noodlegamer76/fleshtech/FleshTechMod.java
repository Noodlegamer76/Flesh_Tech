package com.noodlegamer76.fleshtech;

import com.mojang.logging.LogUtils;
import com.noodlegamer76.fleshtech.block.InitBlocks;
import com.noodlegamer76.fleshtech.client.renderer.block.StomachEntityRenderer;
import com.noodlegamer76.fleshtech.client.renderer.block.TestRenderer;
import com.noodlegamer76.fleshtech.creativetabs.FleshTechTab;
import com.noodlegamer76.fleshtech.creativetabs.InitCreativeTabs;
import com.noodlegamer76.fleshtech.entity.InitEntity;
import com.noodlegamer76.fleshtech.entity.block.InitBlockEntities;
import com.noodlegamer76.fleshtech.event.RegisterShadersEvent;
import com.noodlegamer76.fleshtech.gui.InitMenus;
import com.noodlegamer76.fleshtech.gui.monstercore.MonsterCoreScreen;
import com.noodlegamer76.fleshtech.item.InitItems;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import org.slf4j.Logger;

import static com.noodlegamer76.fleshtech.gui.InitMenus.MONSTER_CORE_CONTAINER;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(FleshTechMod.MODID)
public class FleshTechMod
{
    // Define mod id in a common place for everything to reference
    public static final String MODID = "fleshtech";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    public FleshTechMod()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        InitBlocks.BLOCKS.register(modEventBus);
        InitEntity.ENTITIES.register(modEventBus);
        InitItems.ITEMS.register(modEventBus);
        InitBlockEntities.BLOCK_ENTITIES.register(modEventBus);
        InitMenus.MENU_TYPES.register(modEventBus);

        InitCreativeTabs.CREATIVE_TABS.register(modEventBus);
        modEventBus.register(new FleshTechTab());

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);


        if(Dist.CLIENT.isClient()) {
            modEventBus.register(new RegisterShadersEvent());
        }

        // Register our mod's ForgeConfigSpec so that Forge can create and load the config file for us
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");

        if (Config.logDirtBlock)
            LOGGER.info("DIRT BLOCK >> {}", ForgeRegistries.BLOCKS.getKey(Blocks.DIRT));

        LOGGER.info(Config.magicNumberIntroduction + Config.magicNumber);

        Config.items.forEach((item) -> LOGGER.info("ITEM >> {}", item.toString()));
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            event.enqueueWork(() -> MenuScreens.register(MONSTER_CORE_CONTAINER.get(), MonsterCoreScreen::new));
        }

        @SubscribeEvent
        public static void entityRenderers(EntityRenderersEvent.RegisterRenderers event) {
            event.registerBlockEntityRenderer(InitBlockEntities.RENDER_TESTER.get(), TestRenderer::new);
            event.registerBlockEntityRenderer(InitBlockEntities.STOMACH.get(), StomachEntityRenderer::new);
        }
    }
}
