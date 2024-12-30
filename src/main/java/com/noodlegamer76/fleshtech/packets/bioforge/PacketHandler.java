package com.noodlegamer76.fleshtech.packets.bioforge;

import com.noodlegamer76.fleshtech.FleshTechMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkConstants;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class PacketHandler {

    public static final SimpleChannel INSTANCE = NetworkRegistry.ChannelBuilder.named(
                    new ResourceLocation(FleshTechMod.MODID, "main"))
            .serverAcceptedVersions((e) -> true)
            .clientAcceptedVersions((e) -> true)
            .networkProtocolVersion(() -> NetworkConstants.NETVERSION)
            .simpleChannel();

    public static void register() {
        INSTANCE.messageBuilder(BioForgeCraftingIDPacket.class, 0, NetworkDirection.PLAY_TO_SERVER)
                .encoder(BioForgeCraftingIDPacket::encode)
                .decoder(BioForgeCraftingIDPacket::new)
                .consumerMainThread(BioForgeCraftingIDPacket::handle)
                .add();
    }

    public static void sendToServer(Object msg) {
        INSTANCE.send(PacketDistributor.SERVER.noArg(), msg);
    }
}
