package com.noodlegamer76.fleshtech.entity.block;

import com.noodlegamer76.fleshtech.util.data.MonsterCorePositonData;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class MonsterCoreEntity extends BlockEntity {
    boolean isCreated = false;
    public int growthRange = 0;
    public long calories;
    public long carbohydrates;

    public MonsterCoreEntity(BlockPos pPos, BlockState pBlockState) {
        super(InitBlockEntities.MONSTER_CORE.get(), pPos, pBlockState);
    }

    public static <T extends BlockEntity> void tick(Level level, BlockPos blockPos, BlockState blockState, T t) {
        MonsterCoreEntity entity = (MonsterCoreEntity) level.getBlockEntity(blockPos);
        if (entity == null || !(level instanceof ServerLevel)) {
            return;
        }

        if (!entity.isCreated) {
            create(level, entity);
            loadChunk((ServerLevel) level, blockPos);
        }

        level.sendBlockUpdated(blockPos, blockState, blockState, 2);
        entity.setChanged();
    }

    private static void create(Level level, MonsterCoreEntity entity) {
        if (level instanceof ServerLevel serverLevel) {
            MonsterCorePositonData.get(serverLevel).addBlockPosition(entity.getBlockPos(), level.dimension().location());
            entity.growthRange = 8;
            entity.isCreated = true;
        }
    }

    public static void loadChunk(ServerLevel serverLevel, BlockPos pos) {
        ChunkPos chunkPos = new ChunkPos(pos);
        long chunkKey = ChunkPos.asLong(chunkPos.x, chunkPos.z);

        if (!serverLevel.getForcedChunks().contains(chunkKey)) {
            serverLevel.setChunkForced(chunkPos.x, chunkPos.z, true);
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putLong("calories", calories);
        tag.putLong("carbohydrates", carbohydrates);
        tag.putInt("growthRange", growthRange);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        calories = tag.getLong("calories");
        carbohydrates = tag.getLong("carbohydrates");
        growthRange = tag.getInt("growthRange");
    }

    public CompoundTag getUpdateTag() {
        CompoundTag tag = new CompoundTag();
        tag.putLong("calories", calories);
        tag.putLong("carbohydrates", carbohydrates);
        return tag;
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        // Will get tag from #getUpdateTag
        return ClientboundBlockEntityDataPacket.create(this);
    }
}
