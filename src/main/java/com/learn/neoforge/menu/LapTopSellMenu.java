package com.learn.neoforge.menu;

import com.learn.neoforge.init.LearnComponent;
import com.learn.neoforge.init.LearnItem;
import com.learn.neoforge.init.LearnMenu;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.MinecartItem;

public class LapTopSellMenu extends AbstractContainerMenu {
    private Container container = new SimpleContainer(2);

    public LapTopSellMenu(int containerID, Inventory playerInventory){
        super(LearnMenu.LAPTOP_SELL_MENU.get(),containerID);
        this.addSlot(new Slot(this.container,1,120,40){
            @Override
            public boolean mayPlace(ItemStack stack){
                return stack.is(Items.GOLD_INGOT)
                        ||stack.is(Items.IRON_INGOT)
                        ||stack.is(Items.DIAMOND)
                        ||stack.is(Items.LAPIS_LAZULI)
                        ||stack.is(Items.COPPER_INGOT)
                        ||stack.is(Items.COAL);
            }
        });
        this.addSlot(new Slot(this.container,0,25,40){
            @Override
            public boolean mayPlace(ItemStack stack){
                return stack.is(LearnItem.CREDIT_CARD);
            }
        });
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }
        for (int k = 0; k < 9; ++k) {
            this.addSlot(new Slot(playerInventory, k, 8 + k * 18, 142));
        }
    }
    @Override
    public boolean clickMenuButton(Player player, int buttonID){
        if(buttonID == 0) { //which signs selling
            ItemStack card = this.container.getItem(0);
            ItemStack ingot = this.container.getItem(1);

            if (card.is(LearnItem.CREDIT_CARD.get())&&(ingot.is(Items.COPPER_INGOT)||ingot.is(Items.IRON_INGOT)||ingot.is(Items.DIAMOND)||ingot.is(Items.LAPIS_LAZULI)||ingot.is(Items.COAL)||ingot.is(Items.GOLD_INGOT))){
                int currentBalance = card.getOrDefault(LearnComponent.BALANCE.get(),0);
                if (ingot.is(Items.COAL))
                    {ingot.shrink(1);
                    card.set(LearnComponent.BALANCE.get(),currentBalance + 1);}
                else if (ingot.is(Items.IRON_INGOT)) {
                    ingot.shrink(1);
                    card.set(LearnComponent.BALANCE.get(),currentBalance + 20);
                }
                else if (ingot.is(Items.COPPER_INGOT)) {
                    ingot.shrink(1);
                    card.set(LearnComponent.BALANCE.get(),currentBalance + 5);
                }
                else if (ingot.is(Items.DIAMOND)) {
                    ingot.shrink(1);
                    card.set(LearnComponent.BALANCE.get(),currentBalance + 100);
                }
                else if (ingot.is(Items.GOLD_INGOT)) {
                    ingot.shrink(1);
                    card.set(LearnComponent.BALANCE.get(),currentBalance + 200);
                }
                else if (ingot.is(Items.LAPIS_LAZULI)) {
                    ingot.shrink(1);
                    card.set(LearnComponent.BALANCE.get(),currentBalance + 3);
                }

            }
        } else if (buttonID == 1) {
            player.getInventory().placeItemBackInInventory(this.container.removeItemNoUpdate(0));
            player.getInventory().placeItemBackInInventory(this.container.removeItemNoUpdate(1));
            player.openMenu(new SimpleMenuProvider(
                    (id, inv, p) -> new LapTopMenu(id,inv),
                    Component.translatable("gui.learnforge.laptop")
            ));
        }
        return true;
    }
    @Override
    public void removed(Player player){
        super.removed(player);
        this.clearContainer(player,this.container);
    }

    @Override
    public boolean stillValid(Player player){
        return true;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index){
        ItemStack stack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if(slot != null && slot.hasItem()) {
            ItemStack clicked = slot.getItem();
            stack = clicked.copy();
            if(index == 0) {
                if(!this.moveItemStackTo(clicked,2,38,true)){
                    return ItemStack.EMPTY;
                }
            } else if (index == 1) {
                if (!this.moveItemStackTo(clicked, 2, 38, true)) {
                    return ItemStack.EMPTY;
                }
            }
                else if (clicked.is(LearnItem.CREDIT_CARD)){
                    if(!this.moveItemStackTo(clicked,0,1,false)){
                        return ItemStack.EMPTY;
                    }
                } else if (clicked.is(Items.LAPIS_LAZULI)
                ||clicked.is(Items.COAL)
                ||clicked.is(Items.IRON_INGOT)
                ||clicked.is(Items.GOLD_INGOT)
                ||clicked.is(Items.DIAMOND)
                ||clicked.is(Items.COPPER_INGOT)){
                    if(!this.moveItemStackTo(clicked,1,2,false)){
                        return  ItemStack.EMPTY;
                    }
                }
                else if (index >= 2 && index < 29) {
                    if (!this.moveItemStackTo(clicked, 29, 38, false)) return ItemStack.EMPTY;
                } else if (index >= 29 && index < 38) {
                    if (!this.moveItemStackTo(clicked, 2, 29, false)) return ItemStack.EMPTY;
                }
                if (clicked.isEmpty()) {
                    slot.setByPlayer(ItemStack.EMPTY);
                } else {
                    slot.setChanged();
                }

                if (clicked.getCount() == stack.getCount()) {
                    return ItemStack.EMPTY;
                }
                slot.onTake(player, clicked);

            }
        return stack;
    }

}


