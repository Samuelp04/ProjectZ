package com.swiftydevs.projectz.Client;

import com.swiftydevs.projectz.Client.GUI.PlayerStatsOverlay;
import com.swiftydevs.projectz.Client.Packets.PlayerStatsPacket;
import com.swiftydevs.projectz.Client.PlayerMoney.MoneyHudOverlay;
import com.swiftydevs.projectz.Client.PlayerMoney.PlayerMoneyManager;
import com.swiftydevs.projectz.Client.Trading.TradingScreen;
import com.swiftydevs.projectz.Common.init.ModItems;
import com.swiftydevs.projectz.Common.init.ModNetworking;
import com.swiftydevs.projectz.ProjectZ;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.network.PacketDistributor;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = ProjectZ.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ClientEventHandler {

    public static Map<UUID, Integer> playerKills = new HashMap<>();
    public static Map<UUID, Integer> playerDeaths = new HashMap<>();
    public static Map<UUID, Integer> playerZombieKills = new HashMap<>();

    @SubscribeEvent
    public static void onPlayerKill(LivingDeathEvent event) {
        if (!event.getEntity().getLevel().isClientSide()
                && event.getSource().getEntity() instanceof Player
                && event.getEntity() instanceof Player) {
            Player player = (Player) event.getSource().getEntity();
            UUID playerUUID = player.getUUID();
            playerKills.put(playerUUID, playerKills.getOrDefault(playerUUID, 0) + 1);
            DataStorage.saveData(playerKills, "playerKills");

            ModNetworking.CHANNEL.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) player),
                    new PlayerStatsPacket(playerUUID, playerKills.get(playerUUID), playerZombieKills.get(playerUUID), playerDeaths.get(playerUUID)));

        }
    }

    @SubscribeEvent
    public static void onPlayerDeath(LivingDeathEvent event) {
        if (!event.getEntity().getLevel().isClientSide() && event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            UUID playerUUID = player.getUUID();

            // Track player deaths
            playerDeaths.put(playerUUID, playerDeaths.getOrDefault(playerUUID, 0) + 1);
            DataStorage.saveData(playerDeaths, "playerDeaths");

            // Handle dropping half of the player's money
            Level world = player.level;
            BlockPos pos = player.blockPosition();

            // Get player's current balance
            int currentBalance = PlayerMoneyManager.getPlayerBalance(player);
            int droppedMoney = currentBalance / 2;

            if (droppedMoney > 0) {
                // Reduce player's balance by half
                PlayerMoneyManager.addMoney(player, -droppedMoney);

                // Create money item stack
                ItemStack moneyStack = new ItemStack(ModItems.MONEY.get(), droppedMoney);

                // Spawn the money item entity in the world
                ItemEntity itemEntity = new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), moneyStack);
                world.addFreshEntity(itemEntity);
            }

            ModNetworking.CHANNEL.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) player),
                    new PlayerStatsPacket(playerUUID, playerKills.get(playerUUID), playerZombieKills.get(playerUUID), playerDeaths.get(playerUUID)));


        }
    }

    @SubscribeEvent
    public static void onPlayerKillZombie(LivingDeathEvent event) {
        if (!event.getEntity().getLevel().isClientSide() && event.getEntity() instanceof Zombie) {
            if (event.getSource().getEntity() instanceof Player) {
                Player player = (Player) event.getSource().getEntity();
                UUID playerUUID = player.getUUID();
                playerZombieKills.put(playerUUID, playerZombieKills.getOrDefault(playerUUID, 0) + 1);
                DataStorage.saveData(playerZombieKills, "playerZombieKills");

                ModNetworking.CHANNEL.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) player),
                        new PlayerStatsPacket(playerUUID, playerKills.get(playerUUID), playerZombieKills.get(playerUUID), playerDeaths.get(playerUUID)));

            }
        }
    }

    @SubscribeEvent
    public static void onItemPickup(EntityItemPickupEvent event) {
        Player player = event.getPlayer();
        ItemStack itemStack = event.getItem().getItem();

        // Check if the picked up item is your money item
        if (itemStack.getItem() == ModItems.MONEY.get()) {
            int amount = itemStack.getCount();
            PlayerMoneyManager.addMoney(player, amount);
            itemStack.setCount(0); // Remove the item from the ground
            event.setCanceled(true); // Prevent the item from being added to the player's inventory
        }
    }


    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        MinecraftForge.EVENT_BUS.register(new PlayerStatsOverlay());
        //MinecraftForge.EVENT_BUS.register(new CustomOverlayRenderer());
        MinecraftForge.EVENT_BUS.register(new MoneyHudOverlay(Minecraft.getInstance()));


    }

    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent event){
        PlayerMoneyManager.loadBalanceData();
        ClientEventHandler.playerKills = DataStorage.loadData("playerKills");
        ClientEventHandler.playerDeaths = DataStorage.loadData("playerDeaths");
        ClientEventHandler.playerZombieKills = DataStorage.loadData("playerZombieKills");
    }

    @SubscribeEvent
    public static void onServerStarted(ServerStartedEvent event) {
        ClientEventHandler.playerKills = DataStorage.loadData("playerKills");
        ClientEventHandler.playerDeaths = DataStorage.loadData("playerDeaths");
        ClientEventHandler.playerZombieKills = DataStorage.loadData("playerZombieKills");


    }





}

