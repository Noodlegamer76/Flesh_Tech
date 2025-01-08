package com.noodlegamer76.devgui.particles;

import com.noodlegamer76.fleshtech.FleshTechMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class RegisterEditableParticles {
    private static final Map<String, EditableParticleConfig> EDITABLE_PARTICLES = new HashMap<>();

    public static void put(String key, EditableParticleConfig particles) {
        EDITABLE_PARTICLES.put(key, particles);
    }

    public static EditableParticleConfig get(String key) {
        return EDITABLE_PARTICLES.get(key);
    }

    public static void spawnParticle(EditableParticleConfig config) {

        if (config != null) {

            EditableParticles particle = (EditableParticles) Minecraft.getInstance().particleEngine.createParticle(InitParticles.EDITABLE_PARTICLES.get(),
                    config.getX(), config.getY(), config.getZ(), config.getDx(), config.getDy(), config.getDz());
            if (particle == null) {
                return;
            }
            particle.setConfig(config);

            Minecraft.getInstance().particleEngine.add(particle);
        }
    }

    public static EditableParticleConfig copyDefaultParticles(String key) {
        EditableParticleConfig config = EDITABLE_PARTICLES.get(key);
        if (config == null) {
            return null;
        }
        return copyConfig(config);
    }

    public static EditableParticleConfig copyConfig(EditableParticleConfig config) {
        EditableParticleConfig copy = new EditableParticleConfig(config.getX(), config.getY(), config.getZ(), config.getDx(), config.getDy(), config.getDz());
        copy.setHeight(config.getHeight());
        copy.setWidth(config.getWidth());
        copy.setRed(config.getRed());
        copy.setGreen(config.getGreen());
        copy.setBlue(config.getBlue());
        copy.setAlpha(config.getAlpha());
        copy.setLifetime(config.getLifetime());
        copy.setTexture(config.getTexture());
        copy.setShape(config.getShape());
        copy.setDx(config.getDx());
        copy.setDy(config.getDy());
        copy.setDz(config.getDz());
        copy.setCubeSnapSize(config.getCubeSnapSize());
        copy.setGravity(config.getGravity());
        copy.setFriction(config.getFriction());
        copy.setHasPhysics(config.hasPhysics());
        copy.name = new String(config.name);
        return copy;
    }

    public static void registerDefaultParticles() {
        {
            EditableParticleConfig config = new EditableParticleConfig(0, 0, 0, 0, 0, 0);
            config.setShape(ParticleShapes.QUAD);
            config.setLifetime(ThreadLocalRandom.current().nextInt(25, 50));
            config.setWidth(1);
            config.setHeight(1);
            config.setTexture(new ResourceLocation(FleshTechMod.MODID, "textures/particle/confetti.png"));
            config.setRed(1.0f);
            config.setBlue(0.0f);
            config.setGreen(0.0f);
            EDITABLE_PARTICLES.put("confetti", config);
        }
    }
}
