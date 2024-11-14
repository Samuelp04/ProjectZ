package com.swiftydevs.projectz.Client.Packets;

import com.swiftydevs.projectz.Client.ClientEventHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraftforge.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

public class PlayerStatsPacket {
    private final UUID playerId;
    private final int playerKills;
    private final int playerZombieKills;
    private final int playerDeaths;


    public PlayerStatsPacket(UUID playerId, int playerKills, int playerZombieKills, int playerDeaths) {
        this.playerId = playerId;
        this.playerKills = playerKills;
        this.playerZombieKills = playerZombieKills;
        this.playerDeaths = playerDeaths;
    }

    public PlayerStatsPacket(FriendlyByteBuf buffer) {
        this.playerId = buffer.readUUID();
        this.playerKills = buffer.readInt();
        this.playerZombieKills = buffer.readInt();
        this.playerDeaths = buffer.readInt();
    }

    public void toBytes(FriendlyByteBuf buffer) {
        buffer.writeUUID(playerId);
        buffer.writeInt(playerKills);
        buffer.writeInt(playerZombieKills);
        buffer.writeInt(playerDeaths);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            // Handle packet on client side
            ClientEventHandler.playerKills.put(playerId, playerKills);
            ClientEventHandler.playerZombieKills.put(playerId, playerZombieKills);
            ClientEventHandler.playerDeaths.put(playerId, playerDeaths);
        });
        ctx.get().setPacketHandled(true);
    }
}