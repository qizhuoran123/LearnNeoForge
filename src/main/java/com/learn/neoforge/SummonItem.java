package com.learn.neoforge;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

public class SummonItem extends Item {
    private final EntityType<?> entityType;

    public SummonItem(Properties properties, EntityType<?> entityType){
        super(properties);
        this.entityType = entityType;
    }
    @Override
    public InteractionResult useOn(UseOnContext context){
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos().relative(context.getClickedFace());
        Player player = context.getPlayer();
        if (!level.isClientSide()){
            Entity entity = this.entityType.create(level);
            entity.setPos(pos.getX(),pos.getY(),pos.getZ());
            level.addFreshEntity(entity);
            if (player != null && !player.isCreative()){
                context.getItemInHand().shrink(1);
            }
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }
}
