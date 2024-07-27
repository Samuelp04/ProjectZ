package com.swiftydevs.projectz.Common.init;

import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = "projectz", bus = EventBusSubscriber.Bus.FORGE)
public class ModWorldEvents {

    @SubscribeEvent
    public static void onBiomeLoading(BiomeLoadingEvent event) {
        ModEntityGeneration.onEntitySpawn(event);
    }
}

