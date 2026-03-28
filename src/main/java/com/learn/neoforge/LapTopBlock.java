package com.learn.neoforge;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;

public class LapTopBlock extends GUIBlock{
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public LapTopBlock(Properties properties){
        super(properties);
    }
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block,BlockState> builder){
        builder.add(FACING);
    }
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context){
        return this.defaultBlockState().setValue(FACING,context.getHorizontalDirection());
    }
    @Override
    protected MenuProvider getMenuProvider(BlockState state, Level level, BlockPos pos){
        return new SimpleMenuProvider((containerID, playerInventory, player)-> new LapTopMenu(containerID,playerInventory), Component.translatable("gui.learnneoforge.laptop"));
    }
    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult){
        if(!level.isClientSide) {
            if(player.isCrouching()) {
                level.removeBlock(pos, false);
                ItemStack laptop = new ItemStack(LearnItem.LAPTOP.get());
                if(!player.addItem(laptop)){
                    player.drop(laptop,false);
                }
            }
            else{
                MenuProvider menu = this.getMenuProvider(state, level, pos);
                if (menu != null) {
                    player.openMenu(menu);

                }
            }
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }
}
