package com.noodlegamer76.fleshtech.packets.bioforge;

import com.noodlegamer76.fleshtech.entity.block.BioForgeEntity;
import com.noodlegamer76.fleshtech.recipes.InitRecipeTypes;
import com.noodlegamer76.fleshtech.recipes.bioforge.BioForgeRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

import java.util.List;
import java.util.function.Supplier;

public class BioForgeCraftingIDPacket {
    public int bioForgeItemCraftIndex;
    BlockPos pos;

    public BioForgeCraftingIDPacket(int bioForgeItemCraftIndex, BlockPos pos) {
        this.bioForgeItemCraftIndex = bioForgeItemCraftIndex;
        this.pos = pos;
    }

    public BioForgeCraftingIDPacket(FriendlyByteBuf buf) {
        this(buf.readInt(), buf.readBlockPos());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(bioForgeItemCraftIndex);
        buf.writeBlockPos(pos);
    }

    public void handle(Supplier<NetworkEvent.Context> contextSupplier) {
        ServerPlayer player = contextSupplier.get().getSender();
        Level level = player.level();
        if (level.getBlockEntity(pos) instanceof BioForgeEntity entity) {
            List<BioForgeRecipe> recipes = level.getRecipeManager().getAllRecipesFor(InitRecipeTypes.BIOFORGE_RECIPE_TYPE.get());

            if (bioForgeItemCraftIndex == -1) {
                return;
            }
            BioForgeRecipe recipe = recipes.get(bioForgeItemCraftIndex);

            entity.currentRecipe = recipe;
        }

    }
}