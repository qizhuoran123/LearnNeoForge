package com.learn.neoforge.client.renderer;

import com.learn.neoforge.entity.SeatEntity;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class SeatRenderer extends EntityRenderer<SeatEntity> {

    public SeatRenderer(EntityRendererProvider.Context context) {
        super(context);
    }



    @Override
    public boolean shouldRender(SeatEntity entity, net.minecraft.client.renderer.culling.Frustum frustum, double x, double y, double z) {
        // 永远不渲染
        return false;
    }

    @Override
    public ResourceLocation getTextureLocation(SeatEntity seatEntity) {
        return ResourceLocation.withDefaultNamespace("textures/misc/blank.png");
    }
}
