package com.learn.neoforge.event;

import com.learn.neoforge.init.EntityRegister;
import com.learn.neoforge.learnneoforge;
import net.minecraft.client.renderer.entity.NoopRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

//@EventBusSubscriber(modid = learnneoforge.MODID,bus = EventBusSubscriber.Bus.MOD,value = Dist.CLIENT)
public class RenderEvents {

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        // 注册座椅实体的渲染器（使用空渲染器）
        event.registerEntityRenderer(EntityRegister.SEAT.get(), NoopRenderer::new);
    }


}
