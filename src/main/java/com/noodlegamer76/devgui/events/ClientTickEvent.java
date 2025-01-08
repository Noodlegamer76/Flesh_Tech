package com.noodlegamer76.devgui.events;

import com.noodlegamer76.devgui.SyncedMap;
import com.noodlegamer76.devgui.client.Keybindings;
import com.noodlegamer76.devgui.particles.EditableParticleConfig;
import com.noodlegamer76.devgui.particles.EditableParticles;
import com.noodlegamer76.devgui.particles.ParticleConfigJson;
import com.noodlegamer76.devgui.particles.RegisterEditableParticles;
import com.noodlegamer76.devgui.windows.DevGui;
import com.noodlegamer76.fleshtech.FleshTechMod;
import imgui.ImGui;
import imgui.app.Application;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = FleshTechMod.MODID, value = Dist.CLIENT)
public class ClientTickEvent {
    private static boolean initialized = false;
    private static boolean isGuiOpen = false;
    public static Thread imGui;

    @SubscribeEvent
    public static void clientTick(TickEvent.ClientTickEvent event) {

        List<EditableParticleConfig> configs = SyncedMap.getAllConfigs();

        for (EditableParticleConfig config: configs) {
            RegisterEditableParticles.put(config.name, config);
        }

        if (!initialized) {
            initializeIMGui();
            initialized = true;
            imGui = new Thread(() -> {
                DevGui gui = new com.noodlegamer76.devgui.windows.DevGui();
                Application.launch(gui);
            }, "IMGUI-Thread");
            imGui.start();

            List<EditableParticleConfig> loaded = ParticleConfigJson.loadAllParticles();
            for (int i = 0; i < loaded.size(); i++) {
                SyncedMap.put(loaded.get(i).name, loaded.get(i));
                RegisterEditableParticles.put(loaded.get(i).name, loaded.get(i));
            }
        }
        if (Minecraft.getInstance().screen == null && Keybindings.INSTANCE.devGui.consumeClick() && Minecraft.getInstance().player != null && !isGuiOpen) {
            //Minecraft.getInstance().setScreen(DevGui.devGui);
            //Application.launch(window);
           SyncedMap.put("isOpen", !SyncedMap.getBoolean("isOpen"));
        }
    }

    @SubscribeEvent
    public static void onRenderTick(RenderLevelStageEvent event) {
        if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_LEVEL) {
            if (imGui.isAlive() && Minecraft.getInstance().player != null) {
                SyncedMap.put("blockPos", Minecraft.getInstance().player.blockPosition());
            }
            // Initialize IMGui if it's not initialized yet
            //if (!initialized) {
            //    initializeIMGui();
            //    initialized = true;
            //}

            //setIMGuiDisplaySize();
//
            //ImGui.newFrame();
//
            //ImGui.begin("Mini Window");
//
            //ImGui.text("This is an overlay window!");
//
            //ImGui.setWindowSize(400, 200); // 400x200 size
            //ImGui.setWindowPos(100, 100);
//
            //ImGui.end();
//
            //ImGui.render();

        }
    }

    private static void initializeIMGui() {
        Minecraft.getInstance().submit(() -> {
            ImGui.createContext();

            ImGui.getIO().getFonts().build();
        });
    }

    private static void setIMGuiDisplaySize() {
        int width = Minecraft.getInstance().getWindow().getWidth();
        int height = Minecraft.getInstance().getWindow().getHeight();

        ImGui.getIO().setDisplaySize(width, height);
    }

    // Optional cleanup method
    @SubscribeEvent
    public static void onShutdown(TickEvent.ClientTickEvent event) {
       // if (event.phase == TickEvent.Phase.END && initialized) {
       //     disposeIMGui();
       //     initialized = false;
       // }
    }

    private static void disposeIMGui() {
        ImGui.destroyContext();
    }
}
