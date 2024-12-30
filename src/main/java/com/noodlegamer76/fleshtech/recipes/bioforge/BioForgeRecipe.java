package com.noodlegamer76.fleshtech.recipes.bioforge;

import com.noodlegamer76.fleshtech.entity.block.MonsterCoreEntity;
import com.noodlegamer76.fleshtech.gui.bioforge.BioForgeMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

public class BioForgeRecipe implements Recipe<Container> {
    private final ResourceLocation id;
    public final ItemStack result;
    public final long caloriesCost;
    public final long carbohydratesCost;

    public BioForgeRecipe(ResourceLocation id, ItemStack result, long caloriesCost, long carbohydratesCost) {
        this.id = id;
        this.result = result;
        this.caloriesCost = caloriesCost;
        this.carbohydratesCost = carbohydratesCost;
    }

    @Override
    public boolean matches(Container container, Level pLevel) {

        if (container instanceof BioForgeMenu menu && menu.corePos != null && pLevel.getBlockEntity(menu.corePos) instanceof MonsterCoreEntity entity) {
            long calories = entity.calories;
            long carbohydrates = entity.carbohydrates;

            return calories >= caloriesCost &&
                    carbohydrates >= carbohydratesCost;
        }
        return false;
    }

    public boolean matches(Level level, BlockPos corePos) {
        if (level.getBlockEntity(corePos) instanceof MonsterCoreEntity entity) {
            long calories = entity.calories;
            long carbohydrates = entity.carbohydrates;

            return calories >= caloriesCost &&
                    carbohydrates >= carbohydratesCost;
        }
        return false;
    }

    @Override
    public ItemStack assemble(Container pContainer, RegistryAccess pRegistryAccess) {
        return result;
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess pRegistryAccess) {
        return result;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return BioForgeRecipeSerializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return BioForgeRecipeType.INSTANCE;
    }
}
