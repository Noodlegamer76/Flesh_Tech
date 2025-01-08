package com.noodlegamer76.devgui.particles;

import com.noodlegamer76.fleshtech.FleshTechMod;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.Registries;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class InitParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES =
            DeferredRegister.create(Registries.PARTICLE_TYPE, FleshTechMod.MODID);

    public static final RegistryObject<SimpleParticleType> EDITABLE_PARTICLES =
            PARTICLE_TYPES.register("editable_particles", () -> new SimpleParticleType(true));
}
