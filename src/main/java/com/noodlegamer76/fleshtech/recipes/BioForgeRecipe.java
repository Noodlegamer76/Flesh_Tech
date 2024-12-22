package com.noodlegamer76.fleshtech.recipes;

import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

import java.util.Map;

public class BioForgeRecipe implements Recipe<Inventory> {
    private final ResourceLocation id;
    private final ItemStack result;
    private final Map<String, Integer> costVariables;

    public BioForgeRecipe(ResourceLocation id, ItemStack result, Map<String, Integer> costVariables) {
        this.id = id;
        this.result = result;
        this.costVariables = costVariables;
    }

    @Override
    public boolean matches(Inventory pContainer, Level pLevel) {
        return false;
    }

    @Override
    public ItemStack assemble(Inventory pContainer, RegistryAccess pRegistryAccess) {
        return null;
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return false;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess pRegistryAccess) {
        return null;
    }

    @Override
    public ResourceLocation getId() {
        return null;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return null;
    }

    @Override
    public RecipeType<?> getType() {
        return null;
    }
}
