package com.noodlegamer76.fleshtech.util.infection;

import com.noodlegamer76.fleshtech.FleshTechMod;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.RandomState;

public class NoiseFromDimension {
    public static final ResourceKey<Level> FLESH = ResourceKey.create(Registries.DIMENSION, new ResourceLocation(FleshTechMod.MODID, "flesh_dimension"));

    public static BlockState sampleDefaultBlockFromDimension(ServerLevel serverLevel, BlockPos pos) {
        if (serverLevel == null) {
            return null;
        }

        RandomState randomState = serverLevel.getChunkSource().randomState();
        NoiseColumn column = serverLevel.getChunkSource().getGenerator()
                .getBaseColumn(pos.getX(), pos.getZ(), serverLevel, randomState);

        return column.getBlock(pos.getY());
    }


    /**
     * Samples a block in a ServerLevel and places it in another ServerLevel
     * @param baseLevel The level to place the sampled block state
     * @param sampleLevel The level to sample
     * @param pos Position to sample
     * @return Returns the default blocks blockstate sampled from the sampleLevel
     */
    public static BlockState setDefaultBlockFromDimension(ServerLevel baseLevel, ServerLevel sampleLevel, BlockPos pos) {
        BlockState state = sampleDefaultBlockFromDimension(sampleLevel, pos);
        baseLevel.setBlock(pos, state, 2);

        return state;
    }
}
