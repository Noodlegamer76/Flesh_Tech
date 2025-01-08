package com.noodlegamer76.devgui.windows;

import com.mojang.blaze3d.platform.Window;
import com.noodlegamer76.devgui.SyncedMap;
import com.noodlegamer76.devgui.particles.*;
import imgui.ImGui;
import imgui.ImVec2;
import imgui.app.Application;
import imgui.app.Configuration;
import imgui.type.ImBoolean;
import imgui.type.ImFloat;
import imgui.type.ImInt;
import imgui.type.ImString;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11C.*;

public class DevGui extends Application {
    Window minecraftWindow = Minecraft.getInstance().getWindow();
    private boolean wasOpen = false;
    ArrayList<EditableParticleConfig> configs = new ArrayList<>();
    ArrayList<ImString> names = new ArrayList<>();

    @Override
    protected void configure(Configuration config) {
        super.configure(config);
    }

    @Override
    protected void init(Configuration config) {
        super.init(config);
        glfwHideWindow(handle);
        glfwWindowHint(GLFW_TRANSPARENT_FRAMEBUFFER, GLFW_TRUE);
        glfwWindowHint(GLFW_DECORATED, GLFW_FALSE);

        List<EditableParticleConfig> loaded = ParticleConfigJson.loadAllParticles();
        for (int i = 0; i < loaded.size(); i++) {
            configs.add(loaded.get(i));
            names.add(new ImString(loaded.get(i).name));
        }
    }

    @Override
    public void process() {
        boolean isCurrentlyOpen = SyncedMap.getBoolean("isOpen");

        if (isCurrentlyOpen && !wasOpen) {
            glfwShowWindow(handle);
            wasOpen = true;
        } else if (!isCurrentlyOpen && wasOpen) {
            glfwHideWindow(handle);
            wasOpen = false;
        }
        ImGui.begin("Hello World!");

        ImGui.text("This is an Dev GUI on Minecraft!");
        ImGui.text("Block Position: " + SyncedMap.get("blockPos"));

        if (ImGui.button("Create Particle")) {
            EditableParticleConfig config = new EditableParticleConfig(0, 0,0, 0, 0, 0);
            configs.add(config);
            names.add(new ImString(config.name, 128));
        }

        ImGui.end();

        particlesWindow();
    }

    private void particlesWindow() {
        ImGui.begin("Particles");
        for (int i = 0; i < configs.size(); i++) {
            EditableParticleConfig config = configs.get(i);

            float[] color = new float[] {config.getRed(), config.getGreen(), config.getBlue()};
            float[] speed = new float[] {(float) config.getDx(), (float) config.getDy(), (float) config.getDz()};
            ImInt shapeIndex = new ImInt(config.getShape().ordinal());
            ImInt lifetime = new ImInt(config.getLifetime());
            ImFloat alpha = new ImFloat(config.getAlpha());
            ImFloat cubeScale = new ImFloat(config.getHeight());
            ImFloat cubeSnapSize = new ImFloat(config.getCubeSnapSize());
            ImString texture = new ImString(config.getTexture().toString(), 256);
            ImString name = names.get(i);
            if (ImGui.collapsingHeader("Properties of Particle: " + i)) {

                if (ImGui.inputText("Name " + i, name)) {
                    config.name = name.get();
                }

                ImGui.listBox("Shape " + i, shapeIndex, new String[] {"Quad", "Cube"});
                config.setShape(ParticleShapes.values()[shapeIndex.get()]);

                if (config.getShape() == ParticleShapes.CUBE) {
                    if (ImGui.collapsingHeader("Cube Settings " + i)) {
                        if (ImGui.inputFloat("Cube Scale " + i, cubeScale)) {
                            config.setHeight(cubeScale.get());
                        }
                        if (ImGui.inputFloat("Cube Snap Size " + i, cubeSnapSize)) {
                            config.setCubeSnapSize(cubeSnapSize.get());
                        }
                    }

                }

                if (ImGui.collapsingHeader("Color Picker Dropdown " + i)) {
                    if (ImGui.colorPicker3("Color Picker " + i, color)) {
                        config.setRed(color[0]);
                        config.setGreen(color[1]);
                        config.setBlue(color[2]);
                    }
                }

                if (ImGui.inputFloat3("Delta Speed " + i, speed)) {
                    config.setDx(speed[0]);
                    config.setDy(speed[1]);
                    config.setDz(speed[2]);
                }

                if (ImGui.inputFloat("Alpha " + i, alpha)) {
                    config.setAlpha(alpha.get());
                }

                if (ImGui.inputInt("Lifetime (Ticks) " + i, lifetime)) {
                    config.setLifetime(lifetime.get());
                }

                physicsSettings(config, i);

                if (ImGui.inputText("Texture " + i, texture)) {
                    config.setTexture(new ResourceLocation(texture.get()));
                }

                if (ImGui.button("Save to Config " + i)) {
                    ParticleConfigJson.saveParticles(config);
                }

            }

        }
        if (ImGui.button("Upload")) {
            for (EditableParticleConfig config: configs) {
                SyncedMap.put(new String(config.name), RegisterEditableParticles.copyConfig(config));
            }
        }
        ImGui.end();
    }

    private void physicsSettings(EditableParticleConfig config, int i) {
        ImBoolean hasPhysics = new ImBoolean(config.hasPhysics());
        ImFloat gravity = new ImFloat(config.getGravity());
        ImFloat friction = new ImFloat(config.getFriction());
        if (ImGui.collapsingHeader("Physics Settings " + i)) {

            if (ImGui.checkbox("Has Physics " + i, hasPhysics)) {
                config.setHasPhysics(hasPhysics.get());
            }

            if (ImGui.inputFloat("Gravity " + i, gravity)) {
                config.setGravity(gravity.get());
            }

            if (ImGui.inputFloat("Friction " + i, friction)) {
                config.setFriction(friction.get());
            }
        }
    }
}