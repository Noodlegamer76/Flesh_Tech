package com.noodlegamer76.fleshtech.util.infection;

import com.noodlegamer76.fleshtech.block.InitBlocks;
import com.noodlegamer76.fleshtech.entity.block.MonsterCoreEntity;
import com.noodlegamer76.fleshtech.util.data.MonsterCorePositonData;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class Infection {
    private final ServerLevel level;
    private final ServerLevel infectionLevel;
    private final MonsterCoreEntity entity;
    private final BlockPos corePos;
    private final MonsterCorePositonData.DimensionBlockPos coreDimPos;

    public Infection(ServerLevel level, ServerLevel infectionLevel, MonsterCoreEntity entity) {
        this.level = level;
        this.infectionLevel = infectionLevel;
        this.entity = entity;
        this.corePos = entity.getBlockPos();
        this.coreDimPos = new MonsterCorePositonData.DimensionBlockPos(corePos, level.dimension().location());
    }

    public void tick() {
        for (int i = 0; i < 1; i++) {
            infectRandomBlock();
        }
    }

    private void infectRandomBlock() {
        MonsterCorePositonData.SubBlockPos pos = MonsterCorePositonData.get(level)
                .getRandomInfectionBlock(coreDimPos);

        if (pos == null || !level.isLoaded(pos.pos)) {
            return;
        }

        if (isInfectable(pos.pos)) {
            level.setBlock(pos.pos, InitBlocks.FLESH_BLOCK.get().defaultBlockState(), 2);
        }
        infectNeighbors(pos.pos);
        MonsterCorePositonData.get(level).removeSubBlockPosition(coreDimPos, pos.pos);
    }

    private boolean isInfectable(BlockPos pos) {
        if (level.getBlockState(pos).isSolidRender(level, pos)
                && !level.getBlockState(pos).is(InitBlocks.MONSTER_CORE.get())
                && !level.getBlockState(pos).is(InitBlocks.FLESH_BLOCK.get())) {
            return true;
        }

        return false;
    }

    private void infectNeighbors(BlockPos pos) {
        if (isInfectable(pos.above())) {
            MonsterCorePositonData.get(level).addSubBlockPosition(coreDimPos, pos.above());
        }
        if (isInfectable(pos.below())) {
            MonsterCorePositonData.get(level).addSubBlockPosition(coreDimPos, pos.below());
        }
        if (isInfectable(pos.east())) {
            MonsterCorePositonData.get(level).addSubBlockPosition(coreDimPos, pos.east());
        }
        if (isInfectable(pos.west())) {
            MonsterCorePositonData.get(level).addSubBlockPosition(coreDimPos, pos.west());
        }
        if (isInfectable(pos.north())) {
            MonsterCorePositonData.get(level).addSubBlockPosition(coreDimPos, pos.north());
        }
        if (isInfectable(pos.south())) {
            MonsterCorePositonData.get(level).addSubBlockPosition(coreDimPos, pos.south());
        }
    }
}