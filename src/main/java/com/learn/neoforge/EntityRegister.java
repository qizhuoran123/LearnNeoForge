package com.learn.neoforge;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class EntityRegister {
    public static final DeferredRegister<EntityType<?>> ENTITIES =
            DeferredRegister.create(Registries.ENTITY_TYPE, learnneoforge.MODID);

    public static final DeferredHolder<EntityType<?>, EntityType<SeatEntity>> SEAT =
            ENTITIES.register("seat",()->EntityType.Builder
                    .<SeatEntity>of(SeatEntity::new, MobCategory.MISC)
                    .sized(0.0f,0.0f)
                    .noSummon()
                    .setShouldReceiveVelocityUpdates(false)  // 不接收速度更新
                    .setUpdateInterval(Integer.MAX_VALUE)   // 几乎不更新
                    .fireImmune()                 // 防火
                    .build("seat"));

    public static final DeferredHolder<EntityType<?>, EntityType<UnitreeEntity>> UNITREE =
            ENTITIES.register("unitree",()->EntityType.Builder.<UnitreeEntity>of(UnitreeEntity::new, MobCategory.CREATURE).build("unitree"));

    public static void register(IEventBus eventBus){
        ENTITIES.register(eventBus);
    }
}
