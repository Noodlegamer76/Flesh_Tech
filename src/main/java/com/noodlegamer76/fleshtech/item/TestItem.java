package com.noodlegamer76.fleshtech.item;

import com.noodlegamer76.devgui.particles.EditableParticleConfig;
import com.noodlegamer76.devgui.particles.ParticleShapes;
import com.noodlegamer76.devgui.particles.RegisterEditableParticles;
import com.noodlegamer76.fleshtech.FleshTechMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.TextureAtlasHolder;
import net.minecraft.client.resources.model.AtlasSet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class TestItem extends Item {
    //item i use to trigger stuff in the mod
    public TestItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if (pLevel.isClientSide) {
            RegisterEditableParticles.registerDefaultParticles();
            for (int i = 0; i < 1; i++) {
                EditableParticleConfig config = RegisterEditableParticles.copyDefaultParticles("confetti");
                config.setX(pPlayer.getX());
                config.setY(pPlayer.getY());
                config.setZ(pPlayer.getZ());

                RegisterEditableParticles.spawnParticle(config);
            }
        }
        return super.use(pLevel, pPlayer, pUsedHand);
    }
}
