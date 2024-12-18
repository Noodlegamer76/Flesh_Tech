package com.noodlegamer76.fleshtech.event;

import com.noodlegamer76.fleshtech.FleshTechMod;
import com.noodlegamer76.fleshtech.util.data.MonsterCorePositonData;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.level.ChunkEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod.EventBusSubscriber(modid = FleshTechMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class LoadChunksEvent {

    @SubscribeEvent
    public static void onServerStarting(ServerStartingEvent event) {
        MinecraftServer server = event.getServer();

        for (ServerLevel serverLevel : server.getAllLevels()) {
            loadAndForceChunks(serverLevel);
        }
    }

    private static void loadAndForceChunks(ServerLevel serverLevel) {
        MonsterCorePositonData data = MonsterCorePositonData.get(serverLevel);

        for (MonsterCorePositonData.DimensionBlockPos dimensionBlockPos : data.getBlockPositions()) {
            if (dimensionBlockPos.dimension.equals(serverLevel.dimension().location())) {
                ChunkPos chunkPos = new ChunkPos(dimensionBlockPos.pos);

                serverLevel.setChunkForced(chunkPos.x, chunkPos.z, true);
            }
        }
    }

    @SubscribeEvent
    public static void test(ChunkEvent event) {
    }
}
