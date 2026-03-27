package com.learn.neoforge;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;


public class CatalystItem extends Item {

    public CatalystItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean hasCraftingRemainingItem(){return true;}

    @Override
    public @NotNull ItemStack getCraftingRemainingItem(ItemStack itemStack){
        ItemStack remain = itemStack.copy();
        remain.setCount(1);
        return remain;
    }
}
