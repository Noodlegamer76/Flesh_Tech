package com.noodlegamer76.fleshtech.entity.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class BioForgeEntity extends BlockEntity {
    public BioForgeEntity(BlockPos pPos, BlockState pBlockState) {
        super(InitBlockEntities.BIO_FORGE.get(), pPos, pBlockState);
    }

    public static <T extends BlockEntity> void tick(Level level, BlockPos blockPos, BlockState blockState, T t) {

    }
}
