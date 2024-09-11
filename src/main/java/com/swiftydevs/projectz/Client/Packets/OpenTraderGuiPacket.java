package com.swiftydevs.projectz.Client.Packets;
import com.swiftydevs.projectz.Client.Trading.TradingScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class OpenTraderGuiPacket {
    private final Component title;

    public OpenTraderGuiPacket(Component title) {
        this.title = title;
    }

    public static void encode(OpenTraderGuiPacket packet, FriendlyByteBuf buffer) {
        buffer.writeComponent(packet.title);
    }

    public static OpenTraderGuiPacket decode(FriendlyByteBuf buffer) {
        Component title = buffer.readComponent();
        return new OpenTraderGuiPacket(title);
    }

    public static void handle(OpenTraderGuiPacket packet, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            // This will run on the client
            Player player = Minecraft.getInstance().player;
            Minecraft.getInstance().setScreen(new TradingScreen());
        });
        context.setPacketHandled(true);
    }
}

