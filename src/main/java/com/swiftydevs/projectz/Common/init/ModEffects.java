package com.swiftydevs.projectz.Common.init;

import com.swiftydevs.projectz.Common.Effects.ZombieInfectionEffect;
import com.swiftydevs.projectz.ProjectZ;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

@Mod.EventBusSubscriber(modid = ProjectZ.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEffects {
    @ObjectHolder(ProjectZ.MOD_ID + ":zombie_infection")
    public static final MobEffect ZOMBIE_INFECTION = null;

    @SubscribeEvent
    public static void onRegisterEffects(RegistryEvent.Register<MobEffect> event) {
        event.getRegistry().register(new ZombieInfectionEffect().setRegistryName(ProjectZ.MOD_ID, "zombie_infection"));
    }
}
