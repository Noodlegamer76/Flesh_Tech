package com.noodlegamer76.fleshtech.gui.bioforge;

import com.noodlegamer76.fleshtech.block.BioForgeBlock;
import com.noodlegamer76.fleshtech.block.InitBlocks;
import com.noodlegamer76.fleshtech.entity.block.BioForgeEntity;
import com.noodlegamer76.fleshtech.entity.block.MonsterCoreEntity;
import com.noodlegamer76.fleshtech.gui.InitMenus;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class BioForgeMenu extends AbstractContainerMenu {
    BlockPos pos;
    Level level;
    BioForgeEntity entity;

    public BioForgeMenu(int pContainerId, Player player, BlockPos pos) {
        super(InitMenus.BIO_FORGE.get(), pContainerId);
        level = player.level();
        this.pos = pos;
        if (level.getBlockEntity(pos) instanceof BioForgeEntity bioforge) {
            entity = bioforge;
        }
    }

    @Override
    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        return null;
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return stillValid(ContainerLevelAccess.create(level, entity.getBlockPos()),
                pPlayer, InitBlocks.BIO_FORGE.get());
    }
}