package com.learn.neoforge.init;

import com.learn.neoforge.item.CatalystItem;
import com.learn.neoforge.item.DenoteItem;
import com.learn.neoforge.learnneoforge;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class LearnItem {
    public static final DeferredRegister.Items ITEMS =
            DeferredRegister.createItems(learnneoforge.MODID);

    private static final ResourceLocation CHAIR_ATTACK_DAMAGE =
            ResourceLocation.fromNamespaceAndPath(learnneoforge.MODID, "chair_attack_damage");
    private static final ResourceLocation CHAIR_ATTACK_SPEED =
            ResourceLocation.fromNamespaceAndPath(learnneoforge.MODID, "chair_attack_speed");

    public static final DeferredItem<BlockItem> LEARN_CHAIR_ITEM =
            ITEMS.register("learn_chair", () -> new BlockItem(LearnBlocks.LEARN_CHAIR.get(),
                    new Item.Properties()
                            .attributes(ItemAttributeModifiers.builder()
                                    .add(Attributes.ATTACK_DAMAGE,
                                            new AttributeModifier(CHAIR_ATTACK_DAMAGE, 7.0,
                                                    AttributeModifier.Operation.ADD_VALUE),
                                            EquipmentSlotGroup.MAINHAND)
                                    .add(Attributes.ATTACK_SPEED,
                                            new AttributeModifier(CHAIR_ATTACK_SPEED, -2.4,
                                                    AttributeModifier.Operation.ADD_VALUE),
                                            EquipmentSlotGroup.MAINHAND)
                                    .add(Attributes.ENTITY_INTERACTION_RANGE,
                                            new AttributeModifier(
                                                    ResourceLocation.fromNamespaceAndPath(learnneoforge.MODID, "chair_entity_reach"),
                                                    22.0,
                                                    AttributeModifier.Operation.ADD_VALUE),
                                            EquipmentSlotGroup.MAINHAND)
                                    .build())
            ));
    public static final DeferredItem<BlockItem> BROKEN_UNITREE_ITEM =
            ITEMS.register("broken_unitree", ()-> new BlockItem(LearnBlocks.BROKEN_UNITREE.get(),
                    new Item.Properties().attributes(ItemAttributeModifiers.builder().build())));
    public static final DeferredItem<Item> UNITREE_GUARANTEE =
            ITEMS.register("unitree_guarantee",() -> new CatalystItem(new Item.Properties().stacksTo(1).attributes(ItemAttributeModifiers.builder().build())));
    public static final DeferredItem<Item> CREDIT_CARD =
            ITEMS.register("credit_card", () -> new DenoteItem(new Item
                    .Properties()
                    .stacksTo(1)
                    .component(LearnComponent.BALANCE.get(),1000)
                    .attributes(ItemAttributeModifiers.builder().build())));
    public static final DeferredItem<Item> LAPTOP =
            ITEMS.register("laptop",()-> new BlockItem(LearnBlocks.LAPTOP.get(),
                    new Item.Properties().attributes(ItemAttributeModifiers.builder().build())));
    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
