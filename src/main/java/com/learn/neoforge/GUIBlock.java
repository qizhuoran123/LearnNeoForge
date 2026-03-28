package com.learn.neoforge;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public abstract class GUIBlock extends Block {


    public GUIBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult){
        if(!level.isClientSide) {
            MenuProvider menu = this.getMenuProvider(state,level,pos);
            if(menu != null) {
                player.openMenu(menu);

            }
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }
    @Override
    protected abstract MenuProvider getMenuProvider(BlockState state, Level level, BlockPos pos);
}

