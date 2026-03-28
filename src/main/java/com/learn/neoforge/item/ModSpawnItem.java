package com.learn.neoforge.item;

import com.learn.neoforge.init.EntityRegister;
import com.learn.neoforge.learnneoforge;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModSpawnItem {
    public static final DeferredRegister.Items SPAWNITEM =
            DeferredRegister.createItems(learnneoforge.MODID);

    public static final DeferredItem<SummonItem> UNITREE =
            SPAWNITEM.register("unitree", ()-> new SummonItem(new Item.Properties().stacksTo(1), EntityRegister.UNITREE.get()));

    public static void register(IEventBus eventBus){
        SPAWNITEM.register(eventBus);
    }
}
