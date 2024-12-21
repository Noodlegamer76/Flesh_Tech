package com.noodlegamer76.fleshtech.block;

import com.noodlegamer76.fleshtech.FleshTechMod;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class InitBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, FleshTechMod.MODID);

    public static final RegistryObject<Block> RENDER_TESTER_BLOCK = BLOCKS.register("render_tester",
            () -> new RenderTesterBlock(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(DyeColor.BLACK).noCollission().noOcclusion()));

    public static final RegistryObject<Block> MONSTER_CORE = BLOCKS.register("monster_core",
            () -> new MonsterCore(BlockBehaviour.Properties.copy(Blocks.BEDROCK).mapColor(DyeColor.BLACK)));
    public static final RegistryObject<Block> STOMACH = BLOCKS.register("stomach",
            () -> new StomachBlock(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(DyeColor.BLACK).noOcclusion()));
}
