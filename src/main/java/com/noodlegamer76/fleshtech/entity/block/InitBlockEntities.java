package com.noodlegamer76.fleshtech.entity.block;

import com.noodlegamer76.fleshtech.FleshTechMod;
import com.noodlegamer76.fleshtech.block.InitBlocks;
import com.noodlegamer76.fleshtech.block.MonsterCore;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class InitBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, FleshTechMod.MODID);

        public static final RegistryObject<BlockEntityType<RenderTester>> RENDER_TESTER = BLOCK_ENTITIES.register("render_tester",
            () -> BlockEntityType.Builder.of(RenderTester::new, InitBlocks.RENDER_TESTER_BLOCK.get()).build(null));

    public static final RegistryObject<BlockEntityType<MonsterCoreEntity>> MONSTER_CORE = BLOCK_ENTITIES.register("monster_core",
            () -> BlockEntityType.Builder.of(MonsterCoreEntity::new, InitBlocks.MONSTER_CORE.get()).build(null));

    public static final RegistryObject<BlockEntityType<StomachEntity>> STOMACH = BLOCK_ENTITIES.register("stomach",
            () -> BlockEntityType.Builder.of(StomachEntity::new, InitBlocks.STOMACH.get()).build(null));

    public static final RegistryObject<BlockEntityType<BioForgeEntity>> BIO_FORGE = BLOCK_ENTITIES.register("bio_forge",
            () -> BlockEntityType.Builder.of(BioForgeEntity::new, InitBlocks.BIO_FORGE.get()).build(null));
}
