package com.learn.neoforge.init;

import com.learn.neoforge.menu.LapTopMenu;
import com.learn.neoforge.learnneoforge;
import com.learn.neoforge.menu.LapTopSellMenu;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class LearnMenu {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(Registries.MENU, learnneoforge.MODID);
    public static final DeferredHolder<MenuType<?>,MenuType<LapTopMenu>> LAPTOP_MENU =
            MENUS.register("laptop"
                    , ()-> new MenuType<LapTopMenu>(LapTopMenu::new, FeatureFlags.DEFAULT_FLAGS));
    public static final DeferredHolder<MenuType<?>, MenuType<LapTopSellMenu>> LAPTOP_SELL_MENU =
            MENUS.register("laptop_sell",
                    ()-> new MenuType<LapTopSellMenu>(LapTopSellMenu::new, FeatureFlags.DEFAULT_FLAGS));
    public static void register(IEventBus eventBus) {
        MENUS.register(eventBus);
    }
}
