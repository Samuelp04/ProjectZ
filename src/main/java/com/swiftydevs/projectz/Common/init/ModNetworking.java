package com.swiftydevs.projectz.Common.init;

import com.swiftydevs.projectz.Client.Packets.OpenBackpackPacket;
import com.swiftydevs.projectz.Client.Packets.OpenTraderGuiPacket;
import com.swiftydevs.projectz.Client.Packets.PlayerStatsPacket;
import com.swiftydevs.projectz.ProjectZ;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.Optional;

@Mod.EventBusSubscriber(modid = ProjectZ.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
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
        CHANNEL.registerMessage(id++, OpenTraderGuiPacket.class, OpenTraderGuiPacket::encode, OpenTraderGuiPacket::decode, OpenTraderGuiPacket::handle, Optional.of(NetworkDirection.PLAY_TO_CLIENT));
        CHANNEL.registerMessage(id++, OpenBackpackPacket.class, OpenBackpackPacket::encode, OpenBackpackPacket::decode, OpenBackpackPacket::handle, Optional.of(NetworkDirection.PLAY_TO_CLIENT));

    }
}
