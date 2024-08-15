package com.swiftydevs.projectz.Common.init;

import com.swiftydevs.projectz.Client.Packets.PlayerStatsPacket;
import com.swiftydevs.projectz.ProjectZ;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class ModNetworking {
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(ProjectZ.MOD_ID, "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    public static void register() {
        int id = 0;
        CHANNEL.registerMessage(id++, PlayerStatsPacket.class, PlayerStatsPacket::toBytes, PlayerStatsPacket::new, PlayerStatsPacket::handle);
    }
}
