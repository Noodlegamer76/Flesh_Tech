package com.noodlegamer76.fleshtech.block;

import com.noodlegamer76.fleshtech.entity.block.RenderTester;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.data.worldgen.DimensionTypes;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
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
        Level nether = pLevel.getServer().getLevel(Level.NETHER);
        if (nether instanceof ServerLevel serverLevel) {

            RandomState randomState = serverLevel.getChunkSource().randomState();
            DensityFunction finalDensity = randomState.router().finalDensity();



            int radius = 15;
            for (int x = -radius; x < radius; x++) {
                for (int y = -radius; y < radius; y++) {
                    for (int z = -radius; z < radius; z++) {
                        BlockPos currentPos = pPos.offset(x, y, z);
                        double noise = finalDensity.compute(new DensityFunction.SinglePointContext(currentPos.getX(), currentPos.getY(), currentPos.getZ()));
                        if (noise >= 0) {
                            pLevel.setBlock(currentPos, Blocks.NETHERRACK.defaultBlockState(), 2);
                        }
                        else {
                            pLevel.setBlock(currentPos, Blocks.AIR.defaultBlockState(), 2);
                        }
                    }
                }
            }
        }
    }
}