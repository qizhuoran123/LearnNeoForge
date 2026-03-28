package com.learn.neoforge;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class learnforge_tabs {
    public static final DeferredRegister<CreativeModeTab> TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, learnneoforge.MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> LEARNNEOFORGE =
            TABS.register("learn_neoforge",
                    ()->CreativeModeTab.builder()
                            .title(Component.translatable("itemGroup"+learnneoforge.MODID))
                            .icon(()-> LearnItem.LEARN_CHAIR_ITEM.get().getDefaultInstance())
                            .displayItems(((itemDisplayParameters, output) ->
                            {output.accept(LearnItem.LEARN_CHAIR_ITEM.get());
                            output.accept(ModSpawnItem.UNITREE.get());
                            output.accept(LearnItem.BROKEN_UNITREE_ITEM.get());
                            output.accept(LearnItem.UNITREE_GUARANTEE.get());
                            output.accept(LearnItem.LAPTOP.get());
                            output.accept(LearnItem.CREDIT_CARD.get());}))
                            .build());

    public static void register(IEventBus eventBus){
        TABS.register(eventBus);
    }
}
