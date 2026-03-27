package com.learn.neoforge;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class LearnBlocks {
    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(learnneoforge.MODID);

    public static final DeferredBlock<Block> LEARN_CHAIR =
            BLOCKS.register("learn_chair", () -> new ChairBlocks(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_PURPLE)
                    .strength(2.0f)
                    .noOcclusion()));
    public static final DeferredBlock<Block> BROKEN_UNITREE =
            BLOCKS.register("broken_unitree",()-> new BrokenUnitreeBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_GRAY)
                    .strength(6.0f)
                    .noOcclusion()
                    .noCollission()));

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }

    //========================== functions ================================
    public void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        Player player = event.getEntity();
        Level level = event.getLevel();
        BlockPos pos = event.getPos();
        BlockState state = level.getBlockState(pos);

        if (state.getBlock() == LearnBlocks.LEARN_CHAIR.get()) {
            event.setCanceled(true); //停下手头的活

            if (!level.isClientSide) {
                // 直接让玩家骑乘自己（需要创建座椅实体）
                // 简化：创建一个临时的座椅实体
                SeatEntity seat = new SeatEntity(level, pos);
                level.addFreshEntity(seat);
                player.startRiding(seat);
            }
        }
    }
}
