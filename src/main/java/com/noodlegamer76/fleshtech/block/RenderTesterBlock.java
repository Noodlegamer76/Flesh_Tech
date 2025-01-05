package com.noodlegamer76.fleshtech.block;

import com.noodlegamer76.fleshtech.entity.block.RenderTester;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.data.worldgen.DimensionTypes;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.*;
import net.minecraft.world.level.levelgen.synth.NoiseUtils;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import org.jetbrains.annotations.Nullable;

public class RenderTesterBlock extends Block implements EntityBlock {
    public RenderTesterBlock(Properties pProperties) {
        super(pProperties);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new RenderTester(pPos, pState);
    }
    @Override
    public boolean skipRendering(BlockState pState, BlockState pAdjacentState, Direction pDirection) {
        return true;
    }

    @Override
    public void onPlace(BlockState pState, Level pLevel, BlockPos pPos, BlockState pOldState, boolean pIsMoving) {
        super.onPlace(pState, pLevel, pPos, pOldState, pIsMoving);
        Level nether = pLevel.getServer().getLevel(Level.OVERWORLD);
        if (nether instanceof ServerLevel serverLevel) {

            RandomState randomState = serverLevel.getChunkSource().randomState();

            ChunkPos chunkPos = new ChunkPos(pPos);
            for (int x = chunkPos.getMinBlockX(); x <= chunkPos.getMaxBlockX(); x++) {
                for (int z = chunkPos.getMinBlockZ(); z <= chunkPos.getMaxBlockZ(); z++) {
                    //Get the base column for this (x, z) position
                    NoiseColumn column = serverLevel.getChunkSource().getGenerator()
                            .getBaseColumn(x, z, serverLevel, randomState);

                    for (int y = serverLevel.getMinBuildHeight(); y < serverLevel.getMaxBuildHeight(); y++) {
                        BlockState state = column.getBlock(y);
                        pLevel.setBlock(new BlockPos(x, y, z), state, 2);

                    }
                }
            }
        }
    }
}