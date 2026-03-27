package com.learn.neoforge;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

public class AttackRangeHandler {
    private static final double CUSTOM_REACH = 24.5;  // 默认是3.0，三叉戟是4.0
    private static final double DEFAULT_REACH = 3.0;
    @SubscribeEvent
    public void onLeftClickEmpty(PlayerInteractEvent.LeftClickEmpty event) {
        Player player = event.getEntity();
        if (!isHoldingChair(player)) return;


        tryAttackNearbyEntity(player);
    }

    private boolean isHoldingChair(Player player) {
        ItemStack mainHand = player.getMainHandItem();
        return mainHand.getItem() == LearnItem.LEARN_CHAIR_ITEM.get();
    }

    private void tryAttackNearbyEntity(Player player) {
        // 获取玩家攻击目标
        Entity target = getTargetEntity(player);

        if (target != null) {
            // 执行攻击
            performAttack(player, target);

            // 重置攻击冷却
            player.resetAttackStrengthTicker();
        }
    }

    private Entity getTargetEntity(Player player) {
        // 获取玩家眼睛位置
        Vec3 eyePos = player.getEyePosition();
        // 获取玩家视线方向
        Vec3 lookVec = player.getLookAngle();

        // 获取攻击范围内的所有实体
        AABB searchBox = player.getBoundingBox().inflate(CUSTOM_REACH);
        java.util.List<Entity> entities = player.level().getEntities(player, searchBox,
                e -> e.isAlive() && e != player);

        Entity closest = null;
        double closestDistance = CUSTOM_REACH + 1;

        // 射线检测找到最近的实体
        for (Entity entity : entities) {
            // 计算实体边界框与射线的交点
            AABB entityBox = entity.getBoundingBox();
            double distance = entityBox.clip(eyePos, eyePos.add(lookVec.scale(CUSTOM_REACH)))
                    .map(vec -> eyePos.distanceTo(vec))
                    .orElse(Double.MAX_VALUE);

            if (distance < closestDistance) {
                closestDistance = distance;
                closest = entity;
            }
        }

        return closest;
    }

    private void performAttack(Player player, Entity target) {
        // 检查攻击冷却
        if (player.getAttackStrengthScale(0.5f) < 0.9f) {
            return;
        }

        // 计算伤害
        float damage = calculateDamage(player);

        // 造成伤害
        target.hurt(player.damageSources().playerAttack(player), damage);

        // 触发攻击动画和效果
        player.attack(target);

        // 添加暴击粒子效果（可选）
        player.level().addParticle(net.minecraft.core.particles.ParticleTypes.CRIT,
                target.getX(), target.getY() + target.getBbHeight() / 2, target.getZ(),
                0, 0, 0);
    }

    private float calculateDamage(Player player) {
        float damage = 1.0f; // 空手伤害

        ItemStack mainHand = player.getMainHandItem();
        if (mainHand.getItem() == LearnItem.LEARN_CHAIR_ITEM.get()) {
            damage += 7.0f; // 椅子加成
        }

        return damage;
    }
}
