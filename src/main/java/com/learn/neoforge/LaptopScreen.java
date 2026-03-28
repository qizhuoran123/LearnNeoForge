package com.learn.neoforge;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class LaptopScreen extends AbstractContainerScreen<LapTopMenu> {
    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(learnneoforge.MODID, "textures/gui/laptop_gui.png");

    public LaptopScreen(LapTopMenu menu, Inventory inventory, Component title){
        super(menu, inventory, title);
        this.imageWidth = 176;
        this.imageHeight = 166;
    }
    @Override
    protected void init(){
        super.init();
        int buttonX = this.leftPos+50;
        int buttonY = this.topPos+40;
        this.addRenderableWidget(Button.builder(Component.literal("Buy Unitree Now !!!"),
                button -> {
            if(this.minecraft != null && this.minecraft.gameMode != null){
                this.minecraft.gameMode.handleInventoryButtonClick(this.menu.containerId,0);
            }
                }).bounds(buttonX,buttonY,40,20).build());
        buttonX = this.leftPos+130;
        buttonY = this.topPos + 20;
        this.addRenderableWidget(Button.builder(Component.literal("Go to selling website"),
                button -> {
            if(this.minecraft != null && this.minecraft.gameMode != null){
                this.minecraft.gameMode.handleInventoryButtonClick(this.menu.containerId,1);
            }
                }).bounds(buttonX,buttonY,40,20).build());
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY){
        guiGraphics.blit(TEXTURE,this.leftPos,this.topPos,0,0,this.imageWidth,this.imageHeight);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick){
        this.renderBackground(guiGraphics,mouseX,mouseY,partialTick);
        super.render(guiGraphics,mouseX,mouseY,partialTick);
        this.renderTooltip(guiGraphics,mouseX,mouseY);
    }
}
