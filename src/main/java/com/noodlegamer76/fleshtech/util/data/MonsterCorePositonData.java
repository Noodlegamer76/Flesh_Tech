package com.noodlegamer76.fleshtech.util.data;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;

import java.util.ArrayList;
import java.util.List;

public class MonsterCorePositonData extends SavedData {
    private static final String DATA_NAME = "fleshtech_data";

    private final List<DimensionBlockPos> blockPositions = new ArrayList<>();

    public CompoundTag save(CompoundTag tag) {
        ListTag positionsList = new ListTag();

        for (DimensionBlockPos dimensionBlockPos : blockPositions) {
            CompoundTag posTag = new CompoundTag();
            posTag.putString("dimension", dimensionBlockPos.dimension.toString());  // Save dimension
            posTag.putInt("x", dimensionBlockPos.pos.getX());
            posTag.putInt("y", dimensionBlockPos.pos.getY());
            posTag.putInt("z", dimensionBlockPos.pos.getZ());
            positionsList.add(posTag);
        }

        tag.put("monsterCorePositions", positionsList);
        return tag;
    }

    public static MonsterCorePositonData load(CompoundTag tag) {
        MonsterCorePositonData data = new MonsterCorePositonData();

        ListTag positionsList = tag.getList("monsterCorePositions", Tag.TAG_COMPOUND);
        for (Tag nbt : positionsList) {
            CompoundTag posTag = (CompoundTag) nbt;
            ResourceLocation dimensionId = new ResourceLocation(posTag.getString("dimension"));
            int x = posTag.getInt("x");
            int y = posTag.getInt("y");
            int z = posTag.getInt("z");

            data.blockPositions.add(new DimensionBlockPos(new BlockPos(x, y, z), dimensionId));
        }

        return data;
    }

    public void addBlockPosition(BlockPos pos, ResourceLocation dimension) {
        if (!containsPosition(pos, dimension)) {
            blockPositions.add(new DimensionBlockPos(pos, dimension));
            setDirty();
        }
    }

    public void removeBlockPosition(BlockPos pos, ResourceLocation dimension) {
        blockPositions.removeIf(dimensionBlockPos ->
                dimensionBlockPos.pos.equals(pos) && dimensionBlockPos.dimension.equals(dimension));

        setDirty();
    }

    public boolean containsPosition(BlockPos pos, ResourceLocation dimension) {
        return blockPositions.stream()
                .anyMatch(dimensionBlockPos -> dimensionBlockPos.pos.equals(pos) && dimensionBlockPos.dimension.equals(dimension));
    }

    public List<DimensionBlockPos> getBlockPositions() {
        return blockPositions;
    }

    public static MonsterCorePositonData get(ServerLevel serverLevel) {
        return serverLevel.getDataStorage()
                .computeIfAbsent(MonsterCorePositonData::load, MonsterCorePositonData::new, DATA_NAME);
    }

    public static class DimensionBlockPos {
        public final BlockPos pos;
        public final ResourceLocation dimension;

        public DimensionBlockPos(BlockPos pos, ResourceLocation dimension) {
            this.pos = pos;
            this.dimension = dimension;
        }
    }
}