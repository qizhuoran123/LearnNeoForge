package com.learn.neoforge.entity;

import com.learn.neoforge.block.BrokenUnitreeBlock;
import com.learn.neoforge.init.LearnBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class UnitreeEntity extends PathfinderMob {

    public UnitreeEntity(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
        this.setPersistenceRequired();
    }
    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 80.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.4D) // 注意3.0D速度非常快，Minecraft默认通常是0.2D~0.3D
                .add(Attributes.ATTACK_DAMAGE, 8.0D)
                .add(Attributes.ATTACK_SPEED, 5.0D);
    }


    @Override
    protected void registerGoals() {
        // ==========================================
        // 1. 行动目标 (goalSelector) - 数字越小，优先级越高
        // ==========================================

        // 优先级 0：游泳。非常重要！如果不加这个，你的生物掉进水里会直接沉底淹死
        this.goalSelector.addGoal(0, new FloatGoal(this));

        // 优先级 1：近战攻击。速度倍率设为 1.0，true 表示“即使视线被短暂遮挡也会继续追踪”
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0D, true));

        // 优先级 2：避水随机散步。比普通的 RandomStrollGoal 更好，它会尽量避免走进水里
        this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 1.0D));

        // 优先级 3：看向附近的玩家（8.0F 是方块距离）
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 8.0F));

        // 优先级 4：没事干的时候，随机东张西望
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));


        // ==========================================
        // 2. 索敌目标 (targetSelector)
        // ==========================================

        // 优先级 1：寻找最近的可攻击目标。
        // Monster.class 代表所有敌对生物（僵尸、小白、苦力怕等）。
        // true 表示“必须有视线才能锁定目标”（隔着墙不会透视锁敌）
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Monster.class, true));
    }

    @Override
    public void die(@NotNull DamageSource cause){
        super.die(cause);
        Direction deathDirection = this.getDirection().getAxis().isHorizontal()
                ? this.getDirection()
                : Direction.NORTH;
        if(!this.level().isClientSide){
            BlockPos pos = this.blockPosition();
            this.level().setBlockAndUpdate(pos, LearnBlocks.BROKEN_UNITREE.get()
                    .defaultBlockState()
                    .setValue(BrokenUnitreeBlock.FACING, deathDirection));
        }
    }
}




