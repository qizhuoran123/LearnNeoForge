package com.learn.neoforge.client.renderer;

import com.learn.neoforge.entity.UnitreeEntity;
import com.learn.neoforge.learnneoforge;
import com.learn.neoforge.client.model.unitreeModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

public class unitreeRenderer extends LivingEntityRenderer {
    private final unitreeModel<UnitreeEntity> model;
    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(learnneoforge.MODID, "entity/unitree.png");
    public unitreeRenderer(EntityRendererProvider.Context context){
        super(context, new unitreeModel<>(context.bakeLayer(unitreeModel.LAYER_LOCATION)),0.5f);
        this.model = new unitreeModel<>(context.bakeLayer(unitreeModel.LAYER_LOCATION));
    }
    @Override
    public @NotNull ResourceLocation getTextureLocation(Entity entity) {
        return TEXTURE;
    }



}
