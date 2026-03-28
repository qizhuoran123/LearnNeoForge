package com.learn.neoforge.client;

import com.learn.neoforge.client.renderer.unitreeRenderer;
import com.learn.neoforge.client.screen.LaptopScreen;
import com.learn.neoforge.client.screen.LaptopSellScreen;
import com.learn.neoforge.event.RenderEvents;
import com.learn.neoforge.init.EntityRegister;
import com.learn.neoforge.init.LearnMenu;
import com.learn.neoforge.learnneoforge;
import com.learn.neoforge.client.model.unitreeModel;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

// This class will not load on dedicated servers. Accessing client side code from here is safe.
@Mod(value = learnneoforge.MODID, dist = Dist.CLIENT)
// You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
@EventBusSubscriber(modid = learnneoforge.MODID, value = Dist.CLIENT)
public class learnneoforgeClient {
    public learnneoforgeClient(IEventBus modEventBus, ModContainer container) {
        // Allows NeoForge to create a config screen for this mod's configs.
        // The config screen is accessed by going to the Mods screen > clicking on your mod > clicking on config.
        // Do not forget to add translations for your config options to the en_us.json file.
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
        modEventBus.addListener(RenderEvents::registerRenderers);
    }

    @SubscribeEvent
    static void onClientSetup(FMLClientSetupEvent event) {
        // Some client setup code
        learnneoforge.LOGGER.info("HELLO FROM CLIENT SETUP");
        learnneoforge.LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
    }

    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event){
        event.registerEntityRenderer(EntityRegister.UNITREE.get(), unitreeRenderer::new);
    }
    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event){
        event.registerLayerDefinition(unitreeModel.LAYER_LOCATION, unitreeModel::createBodyLayer);
    }
    @SubscribeEvent
    public static void registerScreens(RegisterMenuScreensEvent event){
        event.register(LearnMenu.LAPTOP_MENU.get(), LaptopScreen::new);
        event.register(LearnMenu.LAPTOP_SELL_MENU.get(), LaptopSellScreen::new);
    }
}
