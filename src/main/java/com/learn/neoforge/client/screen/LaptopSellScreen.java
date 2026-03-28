package com.learn.neoforge.client.screen;

import com.learn.neoforge.learnneoforge;
import com.learn.neoforge.menu.LapTopSellMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class LaptopSellScreen extends AbstractContainerScreen<LapTopSellMenu> {
    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(learnneoforge.MODID, "textures/gui/laptop_sell_menu.png");

    public LaptopSellScreen(LapTopSellMenu menu, Inventory inventory, Component title){
        super(menu,inventory,title);
        this.imageWidth = 176;
        this.imageHeight = 166;
    }
    @Override
    protected void init(){
        super.init();
        int buttonY = this.topPos+30;
        int buttonX = this.leftPos+55;
        this.addRenderableWidget(Button.builder(Component.literal("Sell"),
                button->{
            if(this.minecraft != null && this.minecraft.gameMode != null){
                this.minecraft.gameMode.handleInventoryButtonClick(this.menu.containerId,0);
            }
                }).bounds(buttonX,buttonY,45,30).build());
        buttonX = this.leftPos + 140;
        buttonY = this.topPos + 20;
        this.addRenderableWidget(Button.builder(Component.literal("to buying"),
                button ->{
            if(this.minecraft != null&& this.minecraft.gameMode != null){
                this.minecraft.gameMode.handleInventoryButtonClick(this.menu.containerId,1);
            }
                }).bounds(buttonX,buttonY,30,20).build());


    }
    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int mouseX, int mouseY){
        guiGraphics.blit(TEXTURE,this.leftPos,this.topPos,0,0,this.imageWidth,this.imageHeight);
    }
    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks){
        this.renderBackground(guiGraphics,mouseX,mouseY,partialTicks);
        super.render(guiGraphics,mouseX,mouseY,partialTicks);
        this.renderTooltip(guiGraphics,mouseX,mouseY);
    }
}
