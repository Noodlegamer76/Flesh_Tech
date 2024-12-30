package com.noodlegamer76.fleshtech.recipes;

import com.noodlegamer76.fleshtech.FleshTechMod;
import com.noodlegamer76.fleshtech.recipes.bioforge.BioForgeRecipe;
import com.noodlegamer76.fleshtech.recipes.bioforge.BioForgeRecipeSerializer;
import com.noodlegamer76.fleshtech.recipes.bioforge.BioForgeRecipeType;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class InitRecipeTypes {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, FleshTechMod.MODID);
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, FleshTechMod.MODID);

    public static final RegistryObject<RecipeSerializer<BioForgeRecipe>> BIOFORGE_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register("bioforge_recipe", () -> BioForgeRecipeSerializer.INSTANCE);
    public static final RegistryObject<RecipeType<BioForgeRecipe>> BIOFORGE_RECIPE_TYPE = RECIPE_TYPES.register("bioforge_recipe", () -> BioForgeRecipeType.INSTANCE);
}