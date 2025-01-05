package com.noodlegamer76.fleshtech.util.data;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class MonsterCorePositonData extends SavedData {
    private static final String DATA_NAME = "fleshtech_data";

    private final List<DimensionBlockPos> blockPositions = new ArrayList<>();
    private final Map<DimensionBlockPos, List<SubBlockPos>> subBlockPositions = new HashMap<>();

    // Updated save method with SubBlockPos
    public CompoundTag save(CompoundTag tag) {
        ListTag positionsList = new ListTag();

        for (DimensionBlockPos dimensionBlockPos : blockPositions) {
            CompoundTag posTag = new CompoundTag();
            posTag.putString("dimension", dimensionBlockPos.dimension.toString());  // Save dimension
            posTag.putInt("x", dimensionBlockPos.pos.getX());
            posTag.putInt("y", dimensionBlockPos.pos.getY());
            posTag.putInt("z", dimensionBlockPos.pos.getZ());
            positionsList.add(posTag);

            List<SubBlockPos> subBlocks = subBlockPositions.getOrDefault(dimensionBlockPos, new ArrayList<>());
            CompoundTag subBlockPositionList = new CompoundTag();
            for (SubBlockPos subBlock : subBlocks) {
                CompoundTag subBlockTag = new CompoundTag();
                subBlockTag.putInt("x", subBlock.pos.getX());
                subBlockTag.putInt("y", subBlock.pos.getY());
                subBlockTag.putInt("z", subBlock.pos.getZ());
                subBlockTag.putDouble("distance", subBlock.distance);
                subBlockPositionList.put(subBlock.toString(), subBlockTag);
            }
            posTag.put("infecting", subBlockPositionList);
        }

        tag.put("monsterCorePositions", positionsList);
        return tag;
    }

    // Updated load method to load SubBlockPos with distance
    public static MonsterCorePositonData load(CompoundTag tag) {
        MonsterCorePositonData data = new MonsterCorePositonData();

        ListTag positionsList = tag.getList("monsterCorePositions", Tag.TAG_COMPOUND);
        for (Tag nbt : positionsList) {
            CompoundTag posTag = (CompoundTag) nbt;

            // Extract dimension and main block position
            ResourceLocation dimensionId = new ResourceLocation(posTag.getString("dimension"));
            int x = posTag.getInt("x");
            int y = posTag.getInt("y");
            int z = posTag.getInt("z");

            DimensionBlockPos dimensionBlockPos = new DimensionBlockPos(new BlockPos(x, y, z), dimensionId);
            data.blockPositions.add(dimensionBlockPos);

            // Extract sub-block positions and distances
            CompoundTag subBlockPositionList = posTag.getCompound("infecting");
            List<SubBlockPos> subBlocks = new ArrayList<>();
            for (String key : subBlockPositionList.getAllKeys()) {
                CompoundTag subBlockTag = subBlockPositionList.getCompound(key);

                // Extract sub-block position and distance
                int subX = subBlockTag.getInt("x");
                int subY = subBlockTag.getInt("y");
                int subZ = subBlockTag.getInt("z");
                double distance = subBlockTag.getDouble("distance");

                subBlocks.add(new SubBlockPos(new BlockPos(subX, subY, subZ), distance));
            }

            data.subBlockPositions.put(dimensionBlockPos, subBlocks);
        }

        return data;
    }

    public SubBlockPos getRandomInfectionBlock(DimensionBlockPos corePos) {
        List<SubBlockPos> list = subBlockPositions.get(corePos);
        if (list == null || list.isEmpty()) {
            return null;
        }
        Random random = new Random();
        return list.get(random.nextInt(list.size()));
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

    public void addSubBlockPosition(DimensionBlockPos dimensionBlockPos, BlockPos subBlockPos) {
        if (containsSubBlockPosition(dimensionBlockPos, subBlockPos)) {
            return;
        }

        double distance = new Vec3(dimensionBlockPos.pos.getX(), dimensionBlockPos.pos.getY(), dimensionBlockPos.pos.getZ())
                .distanceTo(new Vec3(subBlockPos.getX(), subBlockPos.getY(), subBlockPos.getZ()));

        subBlockPositions.computeIfAbsent(dimensionBlockPos, k -> new ArrayList<>());

        SubBlockPos subBlock = new SubBlockPos(subBlockPos, distance);
        subBlockPositions.get(dimensionBlockPos).add(subBlock);

        setDirty();
    }

    public void removeSubBlockPosition(DimensionBlockPos dimensionBlockPos, BlockPos subBlockPos) {
        List<SubBlockPos> subBlocks = subBlockPositions.get(dimensionBlockPos);

        if (subBlocks != null) {
            subBlocks.removeIf(subBlock -> subBlock.pos.equals(subBlockPos));
            setDirty();
        }
    }

    public boolean containsSubBlockPosition(DimensionBlockPos dimensionBlockPos, BlockPos subBlockPos) {
        List<SubBlockPos> subBlocks = subBlockPositions.get(dimensionBlockPos);
        return subBlocks != null && subBlocks.stream().anyMatch(subBlock -> subBlock.pos.equals(subBlockPos));
    }

    public List<SubBlockPos> getSubBlockPositions(DimensionBlockPos dimensionBlockPos) {
        return subBlockPositions.getOrDefault(dimensionBlockPos, new ArrayList<>());
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

        @Override
        public boolean equals(Object o) {
            if (this == o) return true; // Same reference
            if (o == null || getClass() != o.getClass()) return false; // Null or different class

            DimensionBlockPos that = (DimensionBlockPos) o;
            return pos.equals(that.pos) && dimension.equals(that.dimension); // Compare fields
        }

        @Override
        public int hashCode() {
            return Objects.hash(pos, dimension); // Use Java's utility for generating a hash
        }
    }

    public static class SubBlockPos {
        public final BlockPos pos;
        public final double distance;

        public SubBlockPos(BlockPos pos, double distance) {
            this.pos = pos;
            this.distance = distance;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true; // Same reference
            if (o == null || getClass() != o.getClass()) return false; // Null or different class

            SubBlockPos that = (SubBlockPos) o;
            return pos.equals(that.pos) && Double.compare(that.distance, distance) == 0; // Compare fields
        }

        @Override
        public int hashCode() {
            return Objects.hash(pos, distance); // Use Java's utility for generating a hash
        }

        @Override
        public String toString() {
            return "SubBlockPos{" +
                    "pos=" + pos +
                    ", distance=" + distance +
                    '}';
        }
    }
}