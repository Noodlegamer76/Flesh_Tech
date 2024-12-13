package com.noodlegamer76.fleshtech.event;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.noodlegamer76.fleshtech.FleshTechMod;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterNamedRenderTypesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.io.IOException;

@Mod.EventBusSubscriber(modid = FleshTechMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class RegisterShadersEvent {
    public static ShaderInstance TEST;

    @SubscribeEvent
    public static void registerShaders(net.minecraftforge.client.event.RegisterShadersEvent event) throws IOException {

        event.registerShader(new ShaderInstance(
                event.getResourceProvider(),
                new ResourceLocation(FleshTechMod.MODID,
                "test"),
                DefaultVertexFormat.POSITION_TEX_COLOR),
                (e) -> TEST = e);
    }

    @SubscribeEvent
    public static void registerNamedRenderTypes(RegisterNamedRenderTypesEvent event) {
    }
}
