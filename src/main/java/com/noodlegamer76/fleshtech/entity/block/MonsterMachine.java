package com.noodlegamer76.fleshtech.entity.block;

import com.noodlegamer76.fleshtech.util.data.MonsterCorePositonData;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;
import java.util.function.Predicate;

public abstract class MonsterMachine extends BlockEntity {

    boolean isCreated = false;
    public BlockPos corePos;

    public MonsterMachine(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);
    }

    public static void create(Level level, MonsterMachine entity) {
        if (level instanceof ServerLevel serverLevel) {
            BlockPos entityPos = entity.getBlockPos(); // Current position of the entity
            ResourceLocation currentDimension = serverLevel.dimension().location();

            // Fetch saved positions
            List<MonsterCorePositonData.DimensionBlockPos> savedPositions =
                    MonsterCorePositonData.get(serverLevel).getBlockPositions();

            MonsterCorePositonData.DimensionBlockPos closestPos = null;
            double closestDistance = Double.MAX_VALUE;

            for (MonsterCorePositonData.DimensionBlockPos savedPos : savedPositions) {
                // Check only positions in the current dimension
                if (savedPos.dimension.equals(currentDimension)) {
                    // Fetch the BlockEntity at the saved position
                    BlockEntity blockEntity = serverLevel.getBlockEntity(savedPos.pos);

                    if (blockEntity instanceof MonsterCoreEntity monsterCore) {
                        double growthRange = monsterCore.growthRange; // Fetch growthRange
                        double distance = entityPos.distSqr(savedPos.pos); // Squared distance

                        // Check if the distance is within the growth range and is the closest
                        if (distance <= (growthRange * growthRange) && distance < closestDistance) {
                            closestDistance = distance;
                            closestPos = savedPos;
                        }
                    }
                }
            }

            if (closestPos != null) {
                System.out.println("Closest position to " + entityPos + " in dimension " + currentDimension +
                        " is " + closestPos.pos + " at distance " + Math.sqrt(closestDistance));
                entity.corePos = closestPos.pos; // Update the core position
            } else {
                System.out.println("No saved positions found within growth range in dimension " + currentDimension);
            }
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        if (corePos != null) {
            tag.putLong("corePos", corePos.asLong());
            tag.putBoolean("isCreated", isCreated);
        }
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        corePos = BlockPos.of(tag.getLong("corePos"));
        isCreated = tag.getBoolean("isCreated");
    }

    public CompoundTag getUpdateTag() {
        CompoundTag tag = new CompoundTag();
        if (corePos != null) {
            tag.putLong("corePos", corePos.asLong());
        }
        return tag;
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        // Will get tag from #getUpdateTag
        return ClientboundBlockEntityDataPacket.create(this);
    }
}
