package com.noodlegamer76.devgui.particles;

import com.noodlegamer76.fleshtech.FleshTechMod;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;

public class EditableParticleConfig {
        private double x, y, z, dx, dy, dz;
        private float width, height, gravity, friction = 0.98f;
        private float red = 1, green = 1, blue = 1, alpha = 1;
        private int lifetime;
        private ResourceLocation texture = new ResourceLocation(FleshTechMod.MODID, "textures/block/flesh_block.png");
        private ParticleShapes shape = ParticleShapes.QUAD;
        private boolean hasPhysics = true;
        public String name = "Untitled";
        private float cubeSnapSize;

    public EditableParticleConfig(double x, double y, double z, double dx, double dy, double dz) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.dx = dx;
        this.dy = dy;
        this.dz = dz;
    }

    public void setHasPhysics(boolean hasPhysics) {
        this.hasPhysics = hasPhysics;
    }

    public boolean hasPhysics() {
        return hasPhysics;
    }

    public float getFriction() {
        return friction;
    }

    public void setFriction(float friction) {
        this.friction = friction;
    }

    public float getGravity() {
        return gravity;
    }

    public void setGravity(float gravity) {
        this.gravity = gravity;
    }

    public float getCubeSnapSize() {
        return cubeSnapSize;
    }

    public void setCubeSnapSize(float cubeSnapSize) {
        this.cubeSnapSize = cubeSnapSize;
    }

    public void setShape(ParticleShapes shape) {
        this.shape = shape;
    }

    public ParticleShapes getShape() {
        return shape;
    }

    public void setTexture(ResourceLocation texture) {
        this.texture = texture;
    }

    public ResourceLocation getTexture() {
        return texture;
    }

    public int getLifetime() {
        return lifetime;
    }

    public void setLifetime(int lifetime) {
        this.lifetime = lifetime;
    }

    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }

    public void setBlue(float blue) {
        this.blue = blue;
    }

    public void setGreen(float green) {
        this.green = green;
    }

    public void setRed(float red) {
        this.red = red;
    }

    public float getAlpha() {
        return alpha;
    }

    public float getBlue() {
        return blue;
    }

    public float getGreen() {
        return green;
    }

    public float getRed() {
        return red;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public float getWidth() {
        return width;
    }

    public void setDx(double dx) {
        this.dx = dx;
    }

    public void setDy(double dy) {
        this.dy = dy;
    }

    public void setDz(double dz) {
        this.dz = dz;
    }

    public void setPosition(double x, double y, double z) {
        setX(x);
        setY(y);
        setZ(z);
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public double getDx() {
        return dx;
    }

    public double getDy() {
        return dy;
    }

    public double getDz() {
        return dz;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }
}
