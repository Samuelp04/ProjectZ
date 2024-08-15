package com.swiftydevs.projectz.Client.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.swiftydevs.projectz.Common.entity.NpcEntity;
import com.swiftydevs.projectz.Common.init.ModEntityTypes;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;

public class SpawnNpcCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("spawnnpc")
                .requires(source -> source.hasPermission(2)) // Command permission level
                .executes(context -> spawnNpc(context.getSource()))
        );
    }

    private static int spawnNpc(CommandSourceStack source) throws CommandSyntaxException {
        ServerPlayer player = source.getPlayerOrException();
        Level world = player.getLevel();
        NpcEntity npc = new NpcEntity(ModEntityTypes.MEDICAL_TRADER.get(), world); // Replace EntityType.ZOMBIE with your NPC EntityType
        npc.setPos(player.getX(), player.getY(), player.getZ());
        npc.setCustomName(Component.nullToEmpty("Medical Trader")); // Set custom name if needed
        world.addFreshEntity(npc);

        source.sendSuccess(Component.nullToEmpty("Spawned NPC at your location."), true);
        return 1;
    }
}