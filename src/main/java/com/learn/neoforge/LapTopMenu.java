package com.learn.neoforge;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class LapTopMenu extends AbstractContainerMenu {
    private Container container = new SimpleContainer(1);

    protected LapTopMenu(int containerId, Inventory playerInventory) {
        super(LearnMenu.LAPTOP_MENU.get(), containerId);
        this.addSlot(new Slot(this.container, 0,20,35){
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
        if (buttonID == 0) {
            ItemStack card = this.container.getItem(0);
            if(card.is(LearnItem.CREDIT_CARD.get())){
                int price = 200;
                int balance = card.getOrDefault(LearnComponent.BALANCE.get(),0);
                if (balance>=price) {
                    card.set(LearnComponent.BALANCE.get(),balance-price);
                    ItemStack product = new ItemStack(ModSpawnItem.UNITREE.get());
                    if (!player.getInventory().add(product)) {
                        player.drop(product, false);
                    }
                    return true;
                }
                else
                    player.displayClientMessage(
                            Component.translatable("message.learnneoforge.insufficient_balance")
                                    .withStyle(ChatFormatting.RED),
                            true
                    );
            }
        }
        return false;
    }

    @Override
    public void removed(Player player){
        super.removed(player);
        this.clearContainer(player, this.container);
    }
    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()){
            ItemStack shiftclicked = slot.getItem();
            itemStack = shiftclicked.copy();
            if(index==0){
                if(!this.moveItemStackTo(shiftclicked,1,37,true))
                    return ItemStack.EMPTY;
            }else if (this.slots.get(0).mayPlace(shiftclicked)) {
                if(!this.moveItemStackTo(shiftclicked,0,1,false))
                    return ItemStack.EMPTY;
            }
            if (shiftclicked.isEmpty()) slot.setByPlayer(ItemStack.EMPTY);// 如果全移走了，把格子清空
            else slot.setChanged();                                         // 如果只移走了一部分，更新格子数据

            if (shiftclicked.getCount() == itemStack.getCount()) return ItemStack.EMPTY;// 数量没变，说明根本没移过去，直接退出
            slot.onTake(player, itemStack);                                             // 触发“从格子中拿走物品”的特殊事件
        }
        return itemStack;
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }
}
