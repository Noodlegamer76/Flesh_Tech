package com.noodlegamer76.devgui.particles;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.noodlegamer76.devgui.client.util.RenderCube;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.checkerframework.checker.units.qual.A;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;
import software.bernie.geckolib.util.RenderUtils;

import java.util.Map;

public class EditableParticles extends SingleQuadParticle implements GeoAnimatable {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private EditableParticleConfig config;
    private ResourceLocation texture;
    private ParticleShapes shape;
    int age;
    public float partialTicks;
    public double xo1, yo1, zo1;
    public AABB cubeBox;

    protected EditableParticles(ClientLevel level, EditableParticleConfig config, SpriteSet spriteSet) {
        super(level, config.getX(), config.getY(), config.getZ());
    }

    @Override
    public void tick() {
        super.tick();
        age++;
    }

    @Override
    public void render(VertexConsumer buffer, Camera renderInfo, float partialTicks) {
        config.setPosition(x, y, z);
        config.setDx(xd);
        config.setDy(yd);
        config.setDz(zd);
        xo1 = xo;
        yo1 = yo;
        zo1 = zo;
        //setConfig(config);
        this.partialTicks = partialTicks;

        RenderSystem._setShaderTexture(0, config.getTexture());
        if (shape == ParticleShapes.QUAD) {
            super.render(buffer, renderInfo, partialTicks);
        }
        else if (shape == ParticleShapes.CUBE) {
            RenderCube.renderParticleCube(buffer, config, getLightColor(partialTicks), this);
        }
    }

    public void setConfig(EditableParticleConfig config) {
        this.config = config;
        setPos(config.getX(), config.getY(), config.getZ());
        setParticleSpeed(config.getDx(), config.getDy(), config.getDz());
        setSize(config.getWidth(), config.getHeight());
        setColor(config.getRed(), config.getGreen(), config.getBlue());
        setAlpha(config.getAlpha());
        setLifetime(config.getLifetime());
        gravity = config.getGravity();
        texture = config.getTexture();
        shape = config.getShape();
        friction = config.getFriction();
        hasPhysics = config.hasPhysics();

        if (shape == ParticleShapes.CUBE) {

            double min = config.getCubeSnapSize() - config.getHeight();
            double max = config.getCubeSnapSize() + config.getHeight();
            double x = config.getX();
            double y = config.getY();
            double z = config.getZ();

            cubeBox = new AABB(
                    x - min, y - min, z - min,
                    x + max, y + max, z + max
            );

            setBoundingBox(cubeBox);
        }
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Override
    protected float getU0() {
        return 0;
    }

    @Override
    protected float getU1() {
        return 1;
    }

    @Override
    protected float getV0() {
        return 0;
    }

    @Override
    protected float getV1() {
        return 1;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<GeoAnimatable>(this, this::animate));
    }

    private PlayState animate(AnimationState<GeoAnimatable> geoAnimatableAnimationState) {
        return PlayState.STOP;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public double getTick(Object object) {
        return age;
    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public Provider(SpriteSet spriteSet) {
            this.sprites = spriteSet;
        }

        @Override
        public Particle createParticle(SimpleParticleType particleType, ClientLevel level,
                                       double x, double y, double z,
                                       double dx, double dy, double dz) {

            EditableParticleConfig config = new EditableParticleConfig(x, y, z, dx, dy, dz);
            return new EditableParticles(level, config, this.sprites);
        }
    }
}