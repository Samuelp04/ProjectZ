package com.swiftydevs.projectz.Client.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.swiftydevs.projectz.Client.PlayerMoney.PlayerMoneyManager;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class AddMoneyCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("addmoney")
                .requires(source -> source.hasPermission(2)) // Requires admin permissions
                .then(Commands.argument("player", StringArgumentType.word())
                        .then(Commands.argument("amount", IntegerArgumentType.integer())
                                .executes(context -> addMoney(context, StringArgumentType.getString(context, "player"), IntegerArgumentType.getInteger(context, "amount"))))));
    }

    private static int addMoney(CommandContext<CommandSourceStack> context, String playerName, int amount) {
        ServerPlayer player = context.getSource().getServer().getPlayerList().getPlayerByName(playerName);
        if (player != null) {
            PlayerMoneyManager.addMoney(player, amount);
            context.getSource().sendSuccess(Component.nullToEmpty("Added " + amount + " money to " + playerName + "'s balance."), true);
            return 1;
        } else {
            context.getSource().sendFailure(Component.nullToEmpty("Player not found."));
            return 0;
        }
    }
}