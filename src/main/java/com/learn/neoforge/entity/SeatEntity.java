package com.learn.neoforge.entity;

import com.learn.neoforge.init.EntityRegister;
import com.learn.neoforge.init.LearnBlocks;
import com.learn.neoforge.learnneoforge;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.Vec3;

public class SeatEntity extends Entity {
    private BlockPos seatPos;
    private final Level level;
    private int creationTick;
    public SeatEntity(EntityType<?> entityType, Level level) {
        super(entityType, level);
        this.noPhysics = true; //无碰撞箱
        this.level = level;
        this.creationTick = this.tickCount;
    }

    public SeatEntity(Level level, BlockPos pos){
        this(EntityRegister.SEAT.get(),level);
        this.seatPos = pos;
        if(pos != null)
            this.setPos(pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5);
        this.creationTick = this.tickCount;
    }

    @Override
    public void tick(){
        super.tick();
        if(seatPos != null) {
            if (this.level().getBlockState(seatPos).getBlock() != LearnBlocks.LEARN_CHAIR.get()) {
                Block current = this.level().getBlockState(seatPos).getBlock();
                Block expected = LearnBlocks.LEARN_CHAIR.get();
                learnneoforge.LOGGER.error("SeatEntity: block mismatch! current={}, expected={}, pos={}",
                        current.getDescriptionId(), expected.getDescriptionId(), seatPos);
                this.discard();
                return;
            }else {
                learnneoforge.LOGGER.warn("SeatEntity: seatPos is null!");
            }
        }
        if (this.tickCount - this.creationTick > 20 && this.getPassengers().isEmpty()){
            learnneoforge.LOGGER.info("SeatEntity: no passenger after delay, discarding. tickCount={}, creationTick={}",
                    this.tickCount, this.creationTick);
            this.discard();

        }
    }

    @Override
    public void move(MoverType type, Vec3 pos) {
        // 座椅不能移动
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {

    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compoundTag) {
        if(compoundTag.contains("seatPos")){
            int[] pos = compoundTag.getIntArray("seatPos");
            if (pos.length == 3){
                seatPos = new BlockPos(pos[0], pos[1], pos[2]);
            }
        }
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compoundTag) {
        // 保存数据
        if (seatPos != null) {
            compoundTag.putIntArray("seatPos", new int[]{seatPos.getX(), seatPos.getY(), seatPos.getZ()});
        }
    }
    @Override
    public boolean isPickable() {
        return false; // 不可被拾取
    }

    @Override
    public boolean isPushable() {
        return false; // 不可被推动
    }
}

