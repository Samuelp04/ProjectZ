package com.swiftydevs.projectz.Client;

import com.swiftydevs.projectz.Client.GUI.PlayerStatsOverlay;
import com.swiftydevs.projectz.ProjectZ;
import com.swiftydevs.projectz.Common.entity.InfectedZombie;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = ProjectZ.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ClientEventHandler {

    public static final Map<UUID, Integer> playerKills = new HashMap<>();
    public static final Map<UUID, Integer> playerDeaths = new HashMap<>();
    public static final Map<UUID, Integer> playerZombieKills = new HashMap<>();

    @SubscribeEvent
    public static void onPlayerKill(LivingDeathEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getSource().getEntity();
            if (player != null) {
                UUID playerUUID = player.getUUID();
                playerKills.put(playerUUID, playerKills.getOrDefault(playerUUID, 0) + 1);
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerDeath(LivingDeathEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            UUID playerUUID = player.getUUID();
            playerDeaths.put(playerUUID, playerDeaths.getOrDefault(playerUUID, 0) + 1);
        }
    }

    @SubscribeEvent
    public static void onPlayerKillZombie(LivingDeathEvent event) {
        if (event.getEntity() instanceof InfectedZombie) {
            Entity entity = event.getSource().getEntity();
            if (entity instanceof Player) {
                Player player = (Player) entity;
                UUID playerUUID = player.getUUID();
                playerZombieKills.put(playerUUID, playerZombieKills.getOrDefault(playerUUID, 0) + 1);
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        MinecraftForge.EVENT_BUS.register(new PlayerStatsOverlay());
    }
}
