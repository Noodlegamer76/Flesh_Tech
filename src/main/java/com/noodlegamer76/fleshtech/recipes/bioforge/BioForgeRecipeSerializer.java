package com.noodlegamer76.fleshtech.recipes.bioforge;

import com.google.gson.JsonObject;
import com.noodlegamer76.fleshtech.FleshTechMod;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

public class BioForgeRecipeSerializer implements RecipeSerializer<BioForgeRecipe> {
    public static final BioForgeRecipeSerializer INSTANCE = new BioForgeRecipeSerializer();
    public static final ResourceLocation ID = new ResourceLocation(FleshTechMod.MODID, "bioforge_recipe");

    @Override
    public BioForgeRecipe fromJson(ResourceLocation resourceLocation, JsonObject json) {
        int calories = json.get("calories").getAsInt();
        int carbohydrates = json.get("carbohydrates").getAsInt();

        Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(json.get("result").getAsString()));
        ItemStack result = item != null ? item.getDefaultInstance() : ItemStack.EMPTY;

        return new BioForgeRecipe(ID, result, calories, carbohydrates);
    }

    @Override
    public @Nullable BioForgeRecipe fromNetwork(ResourceLocation resourceLocation, FriendlyByteBuf friendlyByteBuf) {
        long caloriesCost = friendlyByteBuf.readLong();
        long carbohydratesCost = friendlyByteBuf.readLong();
        ItemStack result = friendlyByteBuf.readItem();
        return new BioForgeRecipe(resourceLocation, result, caloriesCost, carbohydratesCost);
    }

    @Override
    public void toNetwork(FriendlyByteBuf friendlyByteBuf, BioForgeRecipe bioForgeRecipe) {
        friendlyByteBuf.writeLong(bioForgeRecipe.caloriesCost);
        friendlyByteBuf.writeLong(bioForgeRecipe.carbohydratesCost);
        friendlyByteBuf.writeItem(bioForgeRecipe.result);
    }
}
