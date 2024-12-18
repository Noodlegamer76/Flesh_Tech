package com.noodlegamer76.fleshtech.gui.monstercore;

import com.noodlegamer76.fleshtech.block.InitBlocks;
import com.noodlegamer76.fleshtech.entity.block.MonsterCoreEntity;
import com.noodlegamer76.fleshtech.gui.InitMenus;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class MonsterCoreMenu extends AbstractContainerMenu {
    BlockPos pos;
    Level level;
    MonsterCoreEntity entity;

    public MonsterCoreMenu(int pContainerId, Player player, BlockPos pos) {
        super(InitMenus.MONSTER_CORE_CONTAINER.get(), pContainerId);
        level = player.level();
        this.pos = pos;
        if (level.getBlockEntity(pos) instanceof MonsterCoreEntity monsterCoreEntity) {
            entity = monsterCoreEntity;
        }
    }

    @Override
    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        return null;
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return stillValid(ContainerLevelAccess.create(level, entity.getBlockPos()),
                pPlayer, InitBlocks.MONSTER_CORE.get());
    }
}
