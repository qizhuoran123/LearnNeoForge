package com.learn.neoforge;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.awt.*;
import java.util.List;

public class DenoteItem extends Item {

    public DenoteItem(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<net.minecraft.network.chat.Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack,context,tooltipComponents,tooltipFlag);
        int balance = stack.getOrDefault(LearnComponent.BALANCE.get(),0);
        tooltipComponents.add(Component.translatable("tooltip.learnneoforge.balance",balance)
                .withStyle(ChatFormatting.GOLD));
        tooltipComponents.add(Component.translatable("tooltip.learnneoforge.desc").withStyle(ChatFormatting.AQUA,ChatFormatting.ITALIC));
    }
}
