package com.noodlegamer76.fleshtech.datagen;

import com.noodlegamer76.fleshtech.FleshTechMod;
import com.noodlegamer76.fleshtech.item.InitItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

public class ModItemTagGenerator extends ItemTagsProvider {
    public static final TagKey<Item> BIOMASS = ItemTags.create(new ResourceLocation(FleshTechMod.MODID, "biomass"));


    public ModItemTagGenerator(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> providerCompletableFuture,
                               CompletableFuture<TagLookup<Block>> tagLookupCompletableFuture, @Nullable ExistingFileHelper existingFileHelper) {
        super(packOutput, providerCompletableFuture, tagLookupCompletableFuture, FleshTechMod.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {


        //    this.tag(ModTags.Items.RAINBOW_WOOD)
        //            .add(ItemInit.RAINBOW_LOG.get())

        this.tag(BIOMASS)
                .addTags(
                        ItemTags.BAMBOO_BLOCKS,
                        ItemTags.BANNERS,
                        ItemTags.LOGS,
                        ItemTags.FLOWERS,
                        ItemTags.LEAVES,
                        ItemTags.PLANKS,
                        ItemTags.SAPLINGS,
                        ItemTags.WOODEN_BUTTONS,
                        ItemTags.WOODEN_DOORS,
                        ItemTags.WOODEN_FENCES,
                        ItemTags.WOODEN_SLABS,
                        ItemTags.WOODEN_STAIRS,
                        ItemTags.WOODEN_PRESSURE_PLATES,
                        ItemTags.WOODEN_TRAPDOORS,
                        ItemTags.WOOL,
                        ItemTags.WOOL_CARPETS,
                        Tags.Items.BARRELS_WOODEN,
                        Tags.Items.BONES,
                        Tags.Items.CHESTS_WOODEN,
                        Tags.Items.CROPS,
                        Tags.Items.DYES,
                        Tags.Items.EGGS,
                        Tags.Items.FEATHERS,
                        Tags.Items.FENCE_GATES_WOODEN,
                        Tags.Items.FENCES_WOODEN,
                        Tags.Items.HEADS,
                        Tags.Items.LEATHER,
                        Tags.Items.MUSHROOMS,
                        Tags.Items.RODS_WOODEN,
                        Tags.Items.SEEDS,
                        Tags.Items.STRING)
                .add(
                        Items.GRASS,
                        Items.FERN);
    }
}
