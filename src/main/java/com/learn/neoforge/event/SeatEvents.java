package com.learn.neoforge.event;

import com.learn.neoforge.entity.SeatEntity;
import com.learn.neoforge.init.LearnBlocks;
import com.learn.neoforge.init.LearnItem;
import com.learn.neoforge.learnneoforge;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

public class SeatEvents {
    @SubscribeEvent
    public void onRightClickOnBlock(PlayerInteractEvent.RightClickBlock event){
        Player player = event.getEntity();
        BlockPos pos = event.getPos();
        Level level = event.getLevel();
        BlockState state = level.getBlockState(pos);
        Block block = state.getBlock();

        if (block == LearnBlocks.LEARN_CHAIR.get()){
            event.setCanceled(true);    //停下在做的事
            if (player.isCrouching()) {
                if (!level.isClientSide) {
                    if (!player.getMainHandItem().isEmpty()) {
                        return;
                    }

                    // 清除可能残留的座椅实体
                    AABB aabb = new AABB(pos.getX(), pos.getY(), pos.getZ(),
                            pos.getX() + 1, pos.getY() + 1, pos.getZ() + 1);
                    for (Entity e : level.getEntitiesOfClass(SeatEntity.class, aabb)) {
                        e.discard();
                    }
                    level.setBlock(pos, Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL);
                    ItemStack chairStack = new ItemStack(LearnItem.LEARN_CHAIR_ITEM.get());
                    if (!player.addItem(chairStack)) {
                        // 背包满则掉落在地上
                        player.drop(chairStack, false);
                    }
                }
            }
            if (player.isPassenger())
                player.stopRiding();

            if(!level.isClientSide){
                Entity seat = getOrCreateSeat(level, pos);
                if (seat != null) {
                    boolean success = player.startRiding(seat);
                    learnneoforge.LOGGER.info("startRiding result: {}", success);
                }
            }
        }
    }
    private Entity getOrCreateSeat(Level level, BlockPos pos){
        AABB aabb = new AABB(pos.getX(), pos.getY(), pos.getZ(),
                            pos.getX()+1, pos.getY()+1, pos.getZ()+1);
        for (Entity entity: level.getEntitiesOfClass(Entity.class,aabb)){
            if(entity instanceof SeatEntity) {
                return entity;
            }
        }
        SeatEntity seat = new SeatEntity(level, pos);
        level.addFreshEntity(seat);
        return seat;
    }

}
