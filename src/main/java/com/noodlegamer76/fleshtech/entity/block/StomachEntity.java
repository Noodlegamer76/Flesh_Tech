package com.noodlegamer76.fleshtech.entity.block;

import com.noodlegamer76.fleshtech.datagen.ModItemTagGenerator;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;

public class StomachEntity extends MonsterMachine implements GeoBlockEntity {
    public static final RawAnimation OPEN_IDLE = RawAnimation.begin().thenLoop("open_idle");
    public static final RawAnimation OPEN = RawAnimation.begin().thenPlay("open");
    public static final RawAnimation CLOSE = RawAnimation.begin().thenPlay("close");
    public static final RawAnimation DIGESTING = RawAnimation.begin().thenLoop("digesting");

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    boolean isEating = false;
    boolean wasEating = false;
    int eatingCooldown = 0;
    public StomachEntity(BlockPos pPos, BlockState pBlockState) {
        super(InitBlockEntities.STOMACH.get(), pPos, pBlockState);
    }

    public static <T extends BlockEntity> void tick(Level level, BlockPos blockPos, BlockState blockState, T t) {
        StomachEntity entity = (StomachEntity) level.getBlockEntity(blockPos);
        if (entity == null || !(level instanceof ServerLevel)) {
            return;
        }
        if (!entity.isCreated) {
            create(level, entity);
            entity.isCreated = true;
        }
        if (entity.corePos != null) {
            consumeFoodAtOrAbove(level, entity);
            consumeBiomassAtOrAbove(level, entity);
            isEating(level, entity);
        }
        entity.setChanged();
        level.sendBlockUpdated(entity.getBlockPos(), entity.getBlockState(), entity.getBlockState(), 2);
    }

    private static void consumeFoodAtOrAbove(Level level, StomachEntity entity) {
        if (entity.eatingCooldown > 0) {
            entity.eatingCooldown -= 1;
            return;
        }
        List<ItemEntity> items = level.getEntitiesOfClass(ItemEntity.class, new AABB(entity.getBlockPos(), entity.getBlockPos().offset(1, 2 ,1)), (item) -> {
            FoodProperties food = item.getItem().getFoodProperties(null);
            if (food != null && (food.getNutrition() > 0 || food.getSaturationModifier() > 0)) {
                return true;
            }
            return false;
        });

        if (!items.isEmpty() && level.getBlockEntity(entity.corePos) instanceof MonsterCoreEntity core) {
            ItemStack stack = items.get(0).getItem();
            FoodProperties food = stack.getItem().getFoodProperties();

            core.calories += food.getNutrition();
            core.calories += (long) (food.getNutrition() * food.getSaturationModifier() * 2);
            entity.eatingCooldown = (int) ((food.getNutrition() + (food.getNutrition() * food.getSaturationModifier() * 2) * 0.75));
            stack.shrink(1);
        }

    }

    private static void consumeBiomassAtOrAbove(Level level, StomachEntity entity) {
        if (entity.eatingCooldown > 0) {
            return;
        }
        List<ItemEntity> items = level.getEntitiesOfClass(ItemEntity.class, new AABB(entity.getBlockPos(), entity.getBlockPos().offset(1, 2 ,1)), (itemEntity) -> {
            ItemStack item = itemEntity.getItem();
            if (item.is(ModItemTagGenerator.BIOMASS)) {
                return true;
            }
            return false;
        });

        if (!items.isEmpty() && level.getBlockEntity(entity.corePos) instanceof MonsterCoreEntity core) {
            ItemStack stack = items.get(0).getItem();

            entity.eatingCooldown = 10;
            core.carbohydrates += 10;
            stack.shrink(1);
        }
    }


    private static void isEating(Level level, StomachEntity entity) {
        List<ItemEntity> items = level.getEntitiesOfClass(ItemEntity.class, new AABB(entity.getBlockPos(), entity.getBlockPos().offset(1, 2 ,1)), (item) -> {
            FoodProperties food = item.getItem().getFoodProperties(null);
            if ((food != null && (food.getNutrition() > 0 || food.getSaturationModifier() > 0)) || item.getItem().is(ModItemTagGenerator.BIOMASS)) {
                return true;
            }
            return false;
        });

        if (!items.isEmpty() && level.getBlockEntity(entity.corePos) instanceof MonsterCoreEntity core) {
            entity.isEating = true;
        }
        else {
            entity.isEating = false;
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putInt("eatingCooldown", eatingCooldown);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        eatingCooldown = tag.getInt("eatingCooldown");

        isEating = tag.getBoolean("isEating");
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, this::eating));
    }

    private PlayState eating(AnimationState<GeoBlockEntity> state) {
        if ((state.isCurrentAnimation(OPEN) || state.isCurrentAnimation(CLOSE)) && !state.getController().hasAnimationFinished()) {
            return PlayState.CONTINUE;
        }

        if (!isEating && !wasEating) {
            state.setAndContinue(OPEN_IDLE);
        }
        else if (!isEating && wasEating) {
            state.setAndContinue(OPEN);
            wasEating = false;
        }
        else if (isEating && wasEating) {
            state.setAndContinue(DIGESTING);
        }
        else if (isEating && !wasEating) {
            state.setAndContinue(CLOSE);
            wasEating = true;
        }
        return PlayState.CONTINUE;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = new CompoundTag();
        tag.putBoolean("isEating", isEating);
        return tag;
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public AABB getRenderBoundingBox() {
        return new AABB(getBlockPos().offset(-1, 0, -1), getBlockPos().offset(2, 2, 2));
    }


}
