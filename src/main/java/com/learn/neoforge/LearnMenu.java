package com.learn.neoforge;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.flag.FeatureFlag;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.awt.*;

import static net.minecraft.world.flag.FeatureFlags.DEFAULT_FLAGS;

public class LearnMenu {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(Registries.MENU,learnneoforge.MODID);
    public static final DeferredHolder<MenuType<?>,MenuType<LapTopMenu>> LAPTOP_MENU =
            MENUS.register("laptop"
                    , ()-> new MenuType<LapTopMenu>(LapTopMenu::new, FeatureFlags.DEFAULT_FLAGS));

    public static void register(IEventBus eventBus) {
        MENUS.register(eventBus);
    }
}
