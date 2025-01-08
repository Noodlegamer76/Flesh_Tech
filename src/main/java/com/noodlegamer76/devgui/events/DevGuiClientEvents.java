package com.noodlegamer76.devgui.events;

import com.noodlegamer76.devgui.SyncedMap;
import com.noodlegamer76.devgui.client.Keybindings;
import com.noodlegamer76.devgui.particles.EditableParticleConfig;
import com.noodlegamer76.devgui.particles.EditableParticles;
import com.noodlegamer76.devgui.particles.InitParticles;
import com.noodlegamer76.devgui.particles.ParticleConfigJson;
import com.noodlegamer76.devgui.windows.DevGui;
import com.noodlegamer76.fleshtech.FleshTechMod;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.lwjgl.glfw.GLFW;

import java.util.List;

@Mod.EventBusSubscriber(modid = FleshTechMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class DevGuiClientEvents {
    public static DevGui window;

    @SubscribeEvent
    public static void keyEvent(RegisterKeyMappingsEvent event) {
        event.register(Keybindings.INSTANCE.devGui);
    }

    @SubscribeEvent
    public static void ClientSetup(FMLClientSetupEvent event) {
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 2); // Use OpenGL 2.1
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 1);
        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_PROFILE, GLFW.GLFW_OPENGL_ANY_PROFILE);

        window = new DevGui();



        //event.enqueueWork(() -> MenuScreens.register(GuiMenus.DEV_GUI.get(), (screen, inv, component) -> DevGui.devGui));
    }

    @SubscribeEvent
    public static void registerParticles(RegisterParticleProvidersEvent event) {
        Minecraft.getInstance().particleEngine.register(InitParticles.EDITABLE_PARTICLES.get(),
                EditableParticles.Provider::new);
    }

}
