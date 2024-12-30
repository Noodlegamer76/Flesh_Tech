package com.noodlegamer76.fleshtech.recipes.bioforge;

import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import com.noodlegamer76.fleshtech.FleshTechMod;

public class BioForgeRecipeType implements RecipeType<BioForgeRecipe> {

    public static final BioForgeRecipeType INSTANCE = new BioForgeRecipeType();
    public static final ResourceLocation ID = new ResourceLocation(FleshTechMod.MODID, "bioforge_recipe");

    @Override
    public String toString() {
        return ID.toString();
    }
}