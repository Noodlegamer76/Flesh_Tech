package com.noodlegamer76.devgui.particles;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.noodlegamer76.fleshtech.FleshTechMod;
import net.minecraft.client.Minecraft;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ParticleConfigJson {

    public static void saveParticles(EditableParticleConfig config) {
        File configFolder = new File(Minecraft.getInstance().gameDirectory, "config");
        File modFolder = new File(configFolder, FleshTechMod.MODID);
        File particlesFolder = new File(modFolder, "particles");

        if (!particlesFolder.exists() && !particlesFolder.mkdirs()) {
            System.out.println("Failed to create directory: " + particlesFolder.getPath());
            return;
        }

        File file = new File(particlesFolder, config.name + ".json");

        try (FileWriter writer = new FileWriter(file)) {

            Gson data = new Gson();

            data.toJson(config, writer);
            System.out.println("JSON file saved to: " + file.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static EditableParticleConfig loadParticles(String configName) {
        File configFolder = new File(Minecraft.getInstance().gameDirectory, "config");
        File modFolder = new File(configFolder, FleshTechMod.MODID);
        File particlesFolder = new File(modFolder, "particles");

        // Create the file object from the given config name
        File file = new File(particlesFolder, configName + ".json");

        // Check if the file exists
        if (!file.exists()) {
            System.out.println("File does not exist: " + file.getAbsolutePath());
            return null;  // Return null if the file doesn't exist
        }

        // Create a Gson instance to deserialize the JSON
        Gson gson = new Gson();

        // Read and deserialize the JSON into an EditableParticleConfig object
        try (FileReader reader = new FileReader(file)) {
            return gson.fromJson(reader, EditableParticleConfig.class);
        } catch (JsonIOException | IOException e) {
            e.printStackTrace();
            return null;  // Return null if there's an error reading the file
        }
    }

    public static List<EditableParticleConfig> loadAllParticles() {
        List<EditableParticleConfig> particlesList = new ArrayList<>();

        File configFolder = new File(Minecraft.getInstance().gameDirectory, "config");
        File modFolder = new File(configFolder, FleshTechMod.MODID);
        File particlesFolder = new File(modFolder, "particles");

        // Check if the particles folder exists
        if (!particlesFolder.exists()) {
            System.out.println("Particles folder does not exist: " + particlesFolder.getPath());
            return particlesList;  // Return an empty list if the folder doesn't exist
        }

        // Get all .json files in the particles folder
        File[] files = particlesFolder.listFiles((dir, name) -> name.endsWith(".json"));

        // If there are no .json files, return an empty list
        if (files == null || files.length == 0) {
            System.out.println("No particle configuration files found.");
            return particlesList;
        }

        // Create a Gson instance for deserialization
        Gson gson = new Gson();

        // Loop through each file and deserialize into EditableParticleConfig
        for (File file : files) {
            try (FileReader reader = new FileReader(file)) {
                // Deserialize the JSON file into an EditableParticleConfig object
                EditableParticleConfig config = gson.fromJson(reader, EditableParticleConfig.class);
                if (config != null) {
                    particlesList.add(config);  // Add the config to the list
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return particlesList;
    }
}
