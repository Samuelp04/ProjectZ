package com.swiftydevs.projectz.Client.PlayerMoney;

import com.swiftydevs.projectz.Client.DataStorage;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerMoneyManager {

    public static final String BALANCE_KEY = "player_balances";
    private static Map<UUID, Integer> playerBalances = new HashMap<>();

    public static void addMoney(Player player, int amount) {
        UUID playerUUID = player.getUUID();
        int currentBalance = playerBalances.getOrDefault(playerUUID, 0);
        int newBalance = currentBalance + amount;
        playerBalances.put(playerUUID, newBalance);
        saveBalanceData();
        updateBalanceDisplay(player, newBalance);
    }

    public static boolean hasEnoughMoney(Player player, int amount) {
        int currentBalance = getPlayerBalance(player);
        return currentBalance >= amount;
    }

    public static void subtractMoney(Player player, int amount) {
        UUID playerUUID = player.getUUID();
        int currentBalance = playerBalances.getOrDefault(playerUUID, 0);
        if (currentBalance >= amount) {
            int newBalance = currentBalance - amount;
            playerBalances.put(playerUUID, newBalance);
            saveBalanceData();
            updateBalanceDisplay(player, newBalance);
        } else {
            player.displayClientMessage(Component.nullToEmpty("Not enough money!"), true);
        }
    }

    public static void loadBalanceData() {
        playerBalances = DataStorage.loadData(BALANCE_KEY);
    }

    public static void saveBalanceData() {
        DataStorage.saveData(playerBalances, BALANCE_KEY);
    }

    public static void updateBalanceDisplay(Player player, int balance) {
        player.displayClientMessage(Component.nullToEmpty("Balance: " + balance), true);
    }

    public static int getPlayerBalance(Player player) {
        return playerBalances.getOrDefault(player.getUUID(), 0);
    }
}
