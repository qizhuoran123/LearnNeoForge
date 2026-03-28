package com.learn.neoforge;

import com.mojang.serialization.Codec;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.awt.*;

public class LearnComponent {
    public static final DeferredRegister.DataComponents DATA =
            DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, learnneoforge.MODID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> BALANCE =
            DATA.registerComponentType("balance", builder -> builder.persistent(Codec.INT).networkSynchronized(ByteBufCodecs.VAR_INT));

    public static void register(IEventBus bus){
        DATA.register(bus);
    }
}
