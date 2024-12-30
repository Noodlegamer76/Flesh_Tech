package com.noodlegamer76.fleshtech.entity.block;

import com.noodlegamer76.fleshtech.recipes.bioforge.BioForgeRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;

public class BioForgeEntity extends MonsterMachine {
    public BioForgeRecipe currentRecipe;
    public ArrayList<ItemStack> craftResults = new ArrayList<>();

    public BioForgeEntity(BlockPos pPos, BlockState pBlockState) {
        super(InitBlockEntities.BIO_FORGE.get(), pPos, pBlockState);
    }

    public static <T extends BlockEntity> void tick(Level level, BlockPos blockPos, BlockState blockState, T t) {
        if (level.getBlockEntity(blockPos) instanceof BioForgeEntity entity) {
            if (!entity.isCreated) {
                create(level, entity);
                entity.isCreated = true;
            }

            while (!entity.craftResults.isEmpty()) {
                ItemStack result = entity.craftResults.get(0);
                ItemEntity itemEntity = new ItemEntity(level, blockPos.getX(), blockPos.getY() + 1, blockPos.getZ(), result);
                level.addFreshEntity(itemEntity);
                entity.craftResults.remove(0);
            }

            if (entity.currentRecipe == null) {
                return;
            }
            if (entity.canCraft() && level.getBlockEntity(entity.corePos) instanceof MonsterCoreEntity core) {
                entity.craftResults.add(entity.currentRecipe.getResultItem(level.registryAccess()));
                core.calories -= entity.currentRecipe.caloriesCost;
                core.carbohydrates -= entity.currentRecipe.carbohydratesCost;
            }
            else {
            }
            entity.currentRecipe = null;
        }
    }

    public boolean canCraft() {
        if (currentRecipe == null || corePos == null || level == null) {
            return false;
        }
        else {
            return currentRecipe.matches(level, corePos);
        }
    }
}
